package com.finance.ReconcileAuditSystem.controller;

import com.finance.ReconcileAuditSystem.service.ReconciliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reconcile")
public class ReconcileController {

    @Autowired
    private ReconciliationService service;

    @GetMapping("/run")
    public String run() {
        service.runReconciliation();
        service.markDuplicates();
        return "Reconciliation Completed";
    }
}