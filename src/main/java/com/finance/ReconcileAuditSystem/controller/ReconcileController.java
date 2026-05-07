package com.finance.ReconcileAuditSystem.controller;

import com.finance.ReconcileAuditSystem.service.ReconciliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reconcile")
public class ReconcileController {

    @Autowired
    private ReconciliationService service;

    @PostMapping("/run")
    public List<Map<String, Object>> reconcile() {
        return service.reconcile();
    }
}