package com.dashboard.analytics.repository;

import com.dashboard.analytics.model.DashboardState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardStateRepository extends JpaRepository<DashboardState, Long> {
}
