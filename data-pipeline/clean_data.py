import pandas as pd
import mysql.connector
from mysql.connector import Error
import os
import sys

# ─── Configuration ───────────────────────────────────────────────
DB_CONFIG = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '9600',
    'database': 'sales_dashboard'
}

CSV_PATH = os.path.join(os.path.dirname(__file__), 'data', 'sales_data.csv')


def create_database_and_table(cursor):
    """Create the database and sales_records table if they don't exist."""
    cursor.execute("CREATE DATABASE IF NOT EXISTS sales_dashboard")
    cursor.execute("USE sales_dashboard")
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS sales_records (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            order_id VARCHAR(20) NOT NULL,
            product_name VARCHAR(100) NOT NULL,
            category VARCHAR(50) NOT NULL,
            region VARCHAR(50) NOT NULL,
            quantity INT NOT NULL,
            unit_price DECIMAL(10,2) NOT NULL,
            total_price DECIMAL(10,2) NOT NULL,
            order_date DATE NOT NULL
        )
    """)
    print("[✓] Database and table created successfully.")


def load_and_clean_data(csv_path: str) -> pd.DataFrame:
    """Load CSV and perform data cleaning operations."""
    print(f"[→] Loading data from: {csv_path}")
    df = pd.read_csv(csv_path)
    print(f"    Raw rows: {len(df)}")

    # 1. Drop duplicate rows
    before = len(df)
    df = df.drop_duplicates()
    print(f"    Dropped {before - len(df)} duplicate rows.")

    # 2. Drop rows with missing critical fields
    before = len(df)
    df = df.dropna(subset=['order_id', 'product_name', 'category', 'region', 'order_date'])
    print(f"    Dropped {before - len(df)} rows with missing critical fields.")

    # 3. Normalize string columns (strip whitespace, title case)
    df['product_name'] = df['product_name'].str.strip().str.title()
    df['category'] = df['category'].str.strip().str.title()
    df['region'] = df['region'].str.strip().str.title()
    df['order_id'] = df['order_id'].str.strip().str.upper()

    # 4. Convert numeric columns
    df['quantity'] = pd.to_numeric(df['quantity'], errors='coerce')
    df['unit_price'] = pd.to_numeric(df['unit_price'], errors='coerce')
    df['total_price'] = pd.to_numeric(df['total_price'], errors='coerce')

    # 5. Remove rows with negative or zero quantities
    before = len(df)
    df = df[df['quantity'] > 0]
    print(f"    Removed {before - len(df)} rows with invalid quantities.")

    # 6. Recalculate total_price where missing
    mask = df['total_price'].isna()
    df.loc[mask, 'total_price'] = df.loc[mask, 'quantity'] * df.loc[mask, 'unit_price']
    print(f"    Recalculated {mask.sum()} missing total prices.")

    # 7. Parse and validate dates
    df['order_date'] = pd.to_datetime(df['order_date'], errors='coerce')
    before = len(df)
    df = df.dropna(subset=['order_date'])
    print(f"    Dropped {before - len(df)} rows with invalid dates.")

    # 8. Round prices to 2 decimal places
    df['unit_price'] = df['unit_price'].round(2)
    df['total_price'] = df['total_price'].round(2)

    print(f"[✓] Cleaned rows: {len(df)}")
    return df


def insert_into_mysql(df: pd.DataFrame, db_config: dict):
    """Insert cleaned DataFrame into MySQL sales_records table."""
    try:
        # Connect without database first to create it
        conn = mysql.connector.connect(
            host=db_config['host'],
            port=db_config['port'],
            user=db_config['user'],
            password=db_config['password']
        )
        cursor = conn.cursor()
        create_database_and_table(cursor)
        conn.commit()
        cursor.close()
        conn.close()

        # Reconnect with the database selected
        conn = mysql.connector.connect(**db_config)
        cursor = conn.cursor()

        # Clear existing data
        cursor.execute("TRUNCATE TABLE sales_records")

        # Insert rows
        insert_query = """
            INSERT INTO sales_records 
            (order_id, product_name, category, region, quantity, unit_price, total_price, order_date)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
        """

        rows = []
        for _, row in df.iterrows():
            rows.append((
                row['order_id'],
                row['product_name'],
                row['category'],
                row['region'],
                int(row['quantity']),
                float(row['unit_price']),
                float(row['total_price']),
                row['order_date'].strftime('%Y-%m-%d')
            ))

        cursor.executemany(insert_query, rows)
        conn.commit()
        print(f"[✓] Inserted {cursor.rowcount} rows into MySQL.")

        cursor.close()
        conn.close()

    except Error as e:
        print(f"[✗] MySQL Error: {e}")
        sys.exit(1)


def main():
    print("=" * 60)
    print("  Sales Data Pipeline — Clean & Load")
    print("=" * 60)

    # Step 1: Load and clean
    df = load_and_clean_data(CSV_PATH)

    # Step 2: Show summary
    print("\n--- Data Summary ---")
    print(f"  Regions:    {df['region'].nunique()} → {sorted(df['region'].unique())}")
    print(f"  Categories: {df['category'].nunique()} → {sorted(df['category'].unique())}")
    print(f"  Products:   {df['product_name'].nunique()}")
    print(f"  Date Range: {df['order_date'].min().date()} to {df['order_date'].max().date()}")
    print(f"  Total Rev:  ${df['total_price'].sum():,.2f}")
    print()

    # Step 3: Insert into MySQL
    insert_into_mysql(df, DB_CONFIG)

    print("\n[✓] Pipeline complete!")


if __name__ == '__main__':
    main()
