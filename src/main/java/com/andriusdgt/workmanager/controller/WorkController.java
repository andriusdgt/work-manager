package com.andriusdgt.workmanager.controller;

import com.andriusdgt.workmanager.model.*;
import com.andriusdgt.workmanager.repository.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/work-order")
public class WorkController {

    private final AnalysisOrderRepository analysisOrderRepository;
    private final RepairOrderRepository repairOrderRepository;
    private final ReplacementOrderRepository replacementOrderRepository;
    private final PartRepository partRepository;
    private final ValidationResultRepository validationResultRepository;

    public WorkController(AnalysisOrderRepository analysisOrderRepository,
                          RepairOrderRepository repairOrderRepository,
                          ReplacementOrderRepository replacementOrderRepository,
                          PartRepository partRepository,
                          ValidationResultRepository validationResultRepository) {

        this.analysisOrderRepository = analysisOrderRepository;
        this.repairOrderRepository = repairOrderRepository;
        this.replacementOrderRepository = replacementOrderRepository;
        this.partRepository = partRepository;
        this.validationResultRepository = validationResultRepository;
    }

    @PutMapping("/analysis")
    public void createAnalysisOrder(@Valid @RequestBody AnalysisOrder analysisOrder) {
        partRepository.saveAll(analysisOrder.getParts());
        analysisOrderRepository.save(analysisOrder);
        saveValidationResult("analysisOrder");
    }

    @PutMapping("/repair")
    public void createRepairOrder(@Valid @RequestBody RepairOrder repairOrder) {
        partRepository.saveAll(repairOrder.getParts());
        repairOrderRepository.save(repairOrder);
        saveValidationResult("repairOrder");
    }

    @PutMapping("/replacement")
    public void createReplacementOrder(@Valid @RequestBody ReplacementOrder replacementOrder) {
        partRepository.saveAll(replacementOrder.getParts());
        replacementOrderRepository.save(replacementOrder);
        saveValidationResult("replacementOrder");
    }

    private void saveValidationResult(String workOrderType){
        ValidationResult validationResult = ValidationResult.builder()
                .date(LocalDate.now())
                .workOrderType(workOrderType)
                .valid(true)
                .build();
        validationResultRepository.save(validationResult);
    }

}
