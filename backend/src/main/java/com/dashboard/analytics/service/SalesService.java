package com.dashboard.analytics.service;

import com.dashboard.analytics.dto.*;
import com.dashboard.analytics.repository.SalesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SalesService {

    private final SalesRepository salesRepository;

    public SalesService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    public List<SalesByRegionDTO> getSalesByRegion() {
        return salesRepository.findSalesByRegion().stream()
                .map(row -> new SalesByRegionDTO(
                        (String) row[0],
                        toBigDecimal(row[1]),
                        ((Number) row[2]).longValue()
                ))
                .collect(Collectors.toList());
    }

    public List<MonthlyTrendDTO> getMonthlyTrends() {
        return salesRepository.findMonthlyTrends().stream()
                .map(row -> new MonthlyTrendDTO(
                        ((Number) row[0]).intValue(),
                        ((Number) row[1]).intValue(),
                        toBigDecimal(row[2]),
                        ((Number) row[3]).longValue()
                ))
                .collect(Collectors.toList());
    }

    public List<TopProductDTO> getTopProducts() {
        return salesRepository.findTopProducts().stream()
                .map(row -> new TopProductDTO(
                        (String) row[0],
                        toBigDecimal(row[1]),
                        ((Number) row[2]).longValue()
                ))
                .collect(Collectors.toList());
    }

    public List<SalesByCategoryDTO> getSalesByCategory() {
        return salesRepository.findSalesByCategory().stream()
                .map(row -> new SalesByCategoryDTO(
                        (String) row[0],
                        toBigDecimal(row[1]),
                        ((Number) row[2]).longValue(),
                        ((Number) row[3]).longValue()
                ))
                .collect(Collectors.toList());
    }

    public KpiSummaryDTO getKpiSummary() {
        KpiSummaryDTO kpi = new KpiSummaryDTO();
        BigDecimal totalRevenue = salesRepository.findTotalRevenue();
        Long totalOrders = salesRepository.findTotalOrders();

        kpi.setTotalRevenue(totalRevenue);
        kpi.setTotalOrders(totalOrders);
        kpi.setAvgOrderValue(
            totalOrders > 0
                ? totalRevenue.divide(BigDecimal.valueOf(totalOrders), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO
        );

        List<SalesByRegionDTO> regions = getSalesByRegion();
        kpi.setTopRegion(regions.isEmpty() ? "N/A" : regions.get(0).getRegion());
        kpi.setTotalProducts(salesRepository.findDistinctProductCount());
        kpi.setTotalCategories(salesRepository.findDistinctCategoryCount());

        return kpi;
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value instanceof BigDecimal) return (BigDecimal) value;
        return new BigDecimal(value.toString());
    }
}
