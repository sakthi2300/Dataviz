package com.dashboard.analytics.controller;

import com.dashboard.analytics.dto.*;
import com.dashboard.analytics.service.SalesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    /**
     * GET /api/sales-by-region
     * Returns total revenue and order count grouped by region.
     */
    @GetMapping("/sales-by-region")
    public ResponseEntity<List<SalesByRegionDTO>> getSalesByRegion() {
        return ResponseEntity.ok(salesService.getSalesByRegion());
    }

    /**
     * GET /api/monthly-trends
     * Returns monthly revenue and order count trends.
     */
    @GetMapping("/monthly-trends")
    public ResponseEntity<List<MonthlyTrendDTO>> getMonthlyTrends() {
        return ResponseEntity.ok(salesService.getMonthlyTrends());
    }

    /**
     * GET /api/top-products
     * Returns top 10 products by total revenue.
     */
    @GetMapping("/top-products")
    public ResponseEntity<List<TopProductDTO>> getTopProducts() {
        return ResponseEntity.ok(salesService.getTopProducts());
    }

    /**
     * GET /api/sales-by-category
     * Returns revenue, orders, and quantity grouped by category.
     */
    @GetMapping("/sales-by-category")
    public ResponseEntity<List<SalesByCategoryDTO>> getSalesByCategory() {
        return ResponseEntity.ok(salesService.getSalesByCategory());
    }

    /**
     * GET /api/kpi-summary
     * Returns aggregated KPI metrics for the dashboard header.
     */
    @GetMapping("/kpi-summary")
    public ResponseEntity<KpiSummaryDTO> getKpiSummary() {
        return ResponseEntity.ok(salesService.getKpiSummary());
    }
}
