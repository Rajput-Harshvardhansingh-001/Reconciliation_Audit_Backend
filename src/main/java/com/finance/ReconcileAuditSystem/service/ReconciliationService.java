package com.finance.ReconcileAuditSystem.service;

import com.finance.ReconcileAuditSystem.model.Entities.FileMappingEntity;
import com.finance.ReconcileAuditSystem.model.Entities.SystemTransaction;
import com.finance.ReconcileAuditSystem.model.StatusEnum.ReconciliationStatus;
import com.finance.ReconcileAuditSystem.repository.FileMapRepo;
import com.finance.ReconcileAuditSystem.repository.SystemTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReconciliationService {
    @Autowired
    private FileMapRepo bankRepo;

    @Autowired
    private SystemTransactionRepo systemRepo;

    public List<Map<String, Object>> reconcile() {

//        List<FileMappingEntity> bankList = bankRepo.findAll()
//                .stream()
//                .filter(b -> b.getAccountId() != null && b.getDate() != null)
//                .toList();

        List<FileMappingEntity> bankList = bankRepo.findAll();
        List<SystemTransaction> systemList = systemRepo.findAll();

        List<Map<String, Object>> result = new ArrayList<>();


        for (FileMappingEntity bank : bankList) {

            boolean matched = false;

            for (SystemTransaction sys : systemList) {

                if (
                        bank.getAccountId() != null &&
                                bank.getDate() != null &&
                                bank.getAccountId().equals(sys.getAccountId()) &&
                                bank.getDate().equals(sys.getDate()) &&
                                Objects.equals(bank.getDeposits(), sys.getDeposits()) &&
                                Objects.equals(bank.getWithdrawal(), sys.getWithdrawal())
                ) {
                    matched = true;
                    break;
                }
            }

            Map<String, Object> row = new HashMap<>();
            row.put("accountId", bank.getAccountId());
            row.put("date", bank.getDate());
            row.put("status", matched ? "MATCHED" : "MISMATCH");

            result.add(row);
        }

        return result;
    }














//    @Autowired
//    private FileMapRepo statementRepo;
//
//    @Autowired
//    private SystemTransactionRepo systemRepo;
//
//    public void runReconciliation() {
//
//        List<FileMappingEntity> bankData = statementRepo.findAll();
//
//        for (FileMappingEntity bank : bankData) {
//
//            Double amount = (bank.getDeposits() != null)
//                    ? bank.getDeposits()
//                    : bank.getWithdrawal();
//
//            String type = (bank.getDeposits() != null) ? "CREDIT" : "DEBIT";
//
//            List<SystemTransaction> matches =
//                    systemRepo.findByAccountId(bank.getAccountId());
//
//            boolean found = false;
//
//            for (SystemTransaction sys : matches) {
//
//                if (sys.getAmount().equals(amount)
//                        && sys.getDate().equals(bank.getDate())
//                        && sys.getType().equalsIgnoreCase(type)) {
//
//                    found = true;
//                    break;
//                }
//            }
//
//            if (found) {
//                bank.setStatus(ReconciliationStatus.MATCHED);
//            } else {
//                bank.setStatus(ReconciliationStatus.MISMATCH);
//            }
//
//            statementRepo.save(bank);
//        }
//    }
//
//    public void markDuplicates() {
//
//        List<FileMappingEntity> all = statementRepo.findAll();
//
//        Map<String, Integer> map = new HashMap<>();
//
//        for (FileMappingEntity e : all) {
//
//            Double amount = (e.getDeposits() != null)
//                    ? e.getDeposits()
//                    : e.getWithdrawal();
//
//            String key = e.getAccountId() + "_" + amount + "_" + e.getDate();
//
//            map.put(key, map.getOrDefault(key, 0) + 1);
//        }
//
//        for (FileMappingEntity e : all) {
//
//            Double amount = (e.getDeposits() != null)
//                    ? e.getDeposits()
//                    : e.getWithdrawal();
//
//            String key = e.getAccountId() + "_" + amount + "_" + e.getDate();
//
//            if (map.get(key) > 1) {
//                e.setStatus(ReconciliationStatus.DUPLICATE);
//                statementRepo.save(e);
//            }
//        }
//    }
}
