package com.dashboard.analytics.dto;

import java.math.BigDecimal;

public class SalesByCategoryDTO {

    private String category;
    private BigDecimal totalRevenue;
    private Long orderCount;
    private Long totalQuantity;

    public SalesByCategoryDTO() {}

    public SalesByCategoryDTO(String category, BigDecimal totalRevenue, Long orderCount, Long totalQuantity) {
        this.category = category;
        this.totalRevenue = totalRevenue;
        this.orderCount = orderCount;
        this.totalQuantity = totalQuantity;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public Long getOrderCount() { return orderCount; }
    public void setOrderCount(Long orderCount) { this.orderCount = orderCount; }

    public Long getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(Long totalQuantity) { this.totalQuantity = totalQuantity; }
}
