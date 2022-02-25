package com.andriusdgt.workmanager.controller;

import com.andriusdgt.workmanager.model.AnalysisOrder;
import com.andriusdgt.workmanager.model.RepairOrder;
import com.andriusdgt.workmanager.model.ReplacementOrder;
import com.andriusdgt.workmanager.repository.AnalysisOrderRepository;
import com.andriusdgt.workmanager.repository.PartRepository;
import com.andriusdgt.workmanager.repository.RepairOrderRepository;
import com.andriusdgt.workmanager.repository.ReplacementOrderRepository;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/work-order")
public class WorkController {

    private final AnalysisOrderRepository analysisOrderRepository;
    private final RepairOrderRepository repairOrderRepository;
    private final ReplacementOrderRepository replacementOrderRepository;
    private final PartRepository partRepository;

    public WorkController(AnalysisOrderRepository analysisOrderRepository,
                          RepairOrderRepository repairOrderRepository,
                          ReplacementOrderRepository replacementOrderRepository,
                          PartRepository partRepository) {

        this.analysisOrderRepository = analysisOrderRepository;
        this.repairOrderRepository = repairOrderRepository;
        this.replacementOrderRepository = replacementOrderRepository;
        this.partRepository = partRepository;
    }

    @PutMapping("/analysis")
    public void createAnalysisOrder(@Valid @RequestBody AnalysisOrder analysisOrder) {
        partRepository.saveAll(analysisOrder.getParts());
        analysisOrderRepository.save(analysisOrder);
    }

    @PutMapping("/repair")
    public void createRepairOrder(@Valid @RequestBody RepairOrder repairOrder) {
        partRepository.saveAll(repairOrder.getParts());
        repairOrderRepository.save(repairOrder);
    }

    @PutMapping("/replacement")
    public void createReplacementOrder(@Valid @RequestBody ReplacementOrder replacementOrder) {
        partRepository.saveAll(replacementOrder.getParts());
        replacementOrderRepository.save(replacementOrder);
    }

}
