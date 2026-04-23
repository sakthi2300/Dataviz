package com.dashboard.analytics.dto;

import java.math.BigDecimal;

public class TopProductDTO {

    private String productName;
    private BigDecimal totalRevenue;
    private Long totalQuantity;

    public TopProductDTO() {}

    public TopProductDTO(String productName, BigDecimal totalRevenue, Long totalQuantity) {
        this.productName = productName;
        this.totalRevenue = totalRevenue;
        this.totalQuantity = totalQuantity;
    }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public Long getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(Long totalQuantity) { this.totalQuantity = totalQuantity; }
}
