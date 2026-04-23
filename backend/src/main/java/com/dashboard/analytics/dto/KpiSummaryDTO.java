package com.dashboard.analytics.dto;

import java.math.BigDecimal;

public class KpiSummaryDTO {

    private BigDecimal totalRevenue;
    private Long totalOrders;
    private BigDecimal avgOrderValue;
    private String topRegion;
    private Long totalProducts;
    private Long totalCategories;

    public KpiSummaryDTO() {}

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public Long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(Long totalOrders) { this.totalOrders = totalOrders; }

    public BigDecimal getAvgOrderValue() { return avgOrderValue; }
    public void setAvgOrderValue(BigDecimal avgOrderValue) { this.avgOrderValue = avgOrderValue; }

    public String getTopRegion() { return topRegion; }
    public void setTopRegion(String topRegion) { this.topRegion = topRegion; }

    public Long getTotalProducts() { return totalProducts; }
    public void setTotalProducts(Long totalProducts) { this.totalProducts = totalProducts; }

    public Long getTotalCategories() { return totalCategories; }
    public void setTotalCategories(Long totalCategories) { this.totalCategories = totalCategories; }
}
