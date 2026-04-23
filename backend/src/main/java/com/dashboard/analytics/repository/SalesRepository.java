package com.dashboard.analytics.repository;

import com.dashboard.analytics.model.SalesRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<SalesRecord, Long> {

    /**
     * Aggregate total revenue and order count grouped by region.
     */
    @Query(value = "SELECT s.region, SUM(s.total_price) AS totalRevenue, COUNT(s.id) AS orderCount " +
                   "FROM sales_records s GROUP BY s.region ORDER BY totalRevenue DESC",
           nativeQuery = true)
    List<Object[]> findSalesByRegion();

    /**
     * Aggregate monthly revenue trends ordered chronologically.
     */
    @Query(value = "SELECT YEAR(s.order_date) AS yr, MONTH(s.order_date) AS mn, " +
                   "SUM(s.total_price) AS totalRevenue, COUNT(s.id) AS orderCount " +
                   "FROM sales_records s GROUP BY yr, mn ORDER BY yr, mn",
           nativeQuery = true)
    List<Object[]> findMonthlyTrends();

    /**
     * Top 10 products by total revenue.
     */
    @Query(value = "SELECT s.product_name, SUM(s.total_price) AS totalRevenue, " +
                   "SUM(s.quantity) AS totalQuantity " +
                   "FROM sales_records s GROUP BY s.product_name " +
                   "ORDER BY totalRevenue DESC LIMIT 10",
           nativeQuery = true)
    List<Object[]> findTopProducts();

    /**
     * Aggregate revenue, orders, and quantity grouped by category.
     */
    @Query(value = "SELECT s.category, SUM(s.total_price) AS totalRevenue, " +
                   "COUNT(s.id) AS orderCount, SUM(s.quantity) AS totalQuantity " +
                   "FROM sales_records s GROUP BY s.category ORDER BY totalRevenue DESC",
           nativeQuery = true)
    List<Object[]> findSalesByCategory();

    /**
     * KPI: total revenue
     */
    @Query(value = "SELECT COALESCE(SUM(s.total_price), 0) FROM sales_records s", nativeQuery = true)
    java.math.BigDecimal findTotalRevenue();

    /**
     * KPI: total orders
     */
    @Query(value = "SELECT COUNT(s.id) FROM sales_records s", nativeQuery = true)
    Long findTotalOrders();

    /**
     * KPI: distinct product count
     */
    @Query(value = "SELECT COUNT(DISTINCT s.product_name) FROM sales_records s", nativeQuery = true)
    Long findDistinctProductCount();

    /**
     * KPI: distinct category count
     */
    @Query(value = "SELECT COUNT(DISTINCT s.category) FROM sales_records s", nativeQuery = true)
    Long findDistinctCategoryCount();
}
