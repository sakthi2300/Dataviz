package com.dashboard.analytics.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dashboard_state")
public class DashboardState {

    @Id
    private Long id = 1L;

    @Lob
    @Column(name = "dataset", columnDefinition = "LONGTEXT")
    private String dataset;

    @Lob
    @Column(name = "widgets", columnDefinition = "LONGTEXT")
    private String widgets;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDataset() { return dataset; }
    public void setDataset(String dataset) { this.dataset = dataset; }

    public String getWidgets() { return widgets; }
    public void setWidgets(String widgets) { this.widgets = widgets; }
}
