package com.dashboard.analytics.dto;

import java.math.BigDecimal;

public class SalesByRegionDTO {

    private String region;
    private BigDecimal totalRevenue;
    private Long orderCount;

    public SalesByRegionDTO() {}

    public SalesByRegionDTO(String region, BigDecimal totalRevenue, Long orderCount) {
        this.region = region;
        this.totalRevenue = totalRevenue;
        this.orderCount = orderCount;
    }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public Long getOrderCount() { return orderCount; }
    public void setOrderCount(Long orderCount) { this.orderCount = orderCount; }
}
