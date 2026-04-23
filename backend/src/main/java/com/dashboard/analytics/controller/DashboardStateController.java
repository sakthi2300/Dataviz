package com.dashboard.analytics.controller;

import com.dashboard.analytics.model.DashboardState;
import com.dashboard.analytics.repository.DashboardStateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/state")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardStateController {

    private final DashboardStateRepository repo;

    public DashboardStateController(DashboardStateRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<DashboardState> getState() {
        return repo.findById(1L)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(new DashboardState()));
    }

    @PostMapping
    public ResponseEntity<DashboardState> saveState(@RequestBody DashboardState state) {
        state.setId(1L);
        return ResponseEntity.ok(repo.save(state));
    }
}
