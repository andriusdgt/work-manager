package com.andriusdgt.workmanager.controller;

import com.andriusdgt.workmanager.model.*;
import com.andriusdgt.workmanager.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkControllerTest {

    @Mock
    private AnalysisOrderRepository analysisOrderRepository;

    @Mock
    private RepairOrderRepository repairOrderRepository;

    @Mock
    private ReplacementOrderRepository replacementOrderRepository;

    @Mock
    private PartRepository partRepository;

    @Mock
    private ValidationResultRepository validationResultRepository;

    WorkController controller;

    @BeforeEach
    public void setUp() {
        controller = new WorkController(analysisOrderRepository,
                repairOrderRepository,
                replacementOrderRepository,
                partRepository,
                validationResultRepository
        );
    }

    @Test
    public void createsAnalysisOrder() {
        AnalysisOrder analysisOrderMock = mock(AnalysisOrder.class);
        List<Part> partsMock = List.of(mock(Part.class));
        ValidationResult expectedValidationResult = ValidationResult.builder()
                .date(LocalDate.now())
                .workOrderType("analysisOrder")
                .department(Department.ANALYSIS.toString())
                .valid(true)
                .build();
        ArgumentCaptor<ValidationResult> validationResultCaptor = ArgumentCaptor.forClass(ValidationResult.class);

        when(analysisOrderMock.getParts()).thenReturn(partsMock);
        when(analysisOrderMock.getDepartment()).thenReturn(Department.ANALYSIS);

        controller.createAnalysisOrder(analysisOrderMock);

        verify(partRepository).saveAll(partsMock);
        verify(analysisOrderRepository).save(analysisOrderMock);
        verify(validationResultRepository).save(validationResultCaptor.capture());
        assertThat(expectedValidationResult)
                .usingRecursiveComparison()
                .isEqualTo(validationResultCaptor.getValue());
    }

    @Test
    public void createsRepairOrder() {
        RepairOrder repairOrderMock = mock(RepairOrder.class);
        List<Part> partsMock = List.of(mock(Part.class));
        ValidationResult expectedValidationResult = ValidationResult.builder()
                .date(LocalDate.now())
                .workOrderType("repairOrder")
                .department(Department.REPAIR.toString())
                .valid(true)
                .build();
        ArgumentCaptor<ValidationResult> validationResultCaptor = ArgumentCaptor.forClass(ValidationResult.class);

        when(repairOrderMock.getParts()).thenReturn(partsMock);
        when(repairOrderMock.getDepartment()).thenReturn(Department.REPAIR);

        controller.createRepairOrder(repairOrderMock);

        verify(partRepository).saveAll(partsMock);
        verify(repairOrderRepository).save(repairOrderMock);
        verify(validationResultRepository).save(validationResultCaptor.capture());
        assertThat(expectedValidationResult)
                .usingRecursiveComparison()
                .isEqualTo(validationResultCaptor.getValue());
    }

    @Test
    public void createsReplacementOrder() {
        ReplacementOrder replacementOrderMock = mock(ReplacementOrder.class);
        List<Part> partsMock = List.of(mock(Part.class));
        ValidationResult expectedValidationResult = ValidationResult.builder()
                .date(LocalDate.now())
                .workOrderType("replacementOrder")
                .department(Department.REPLACEMENT.toString())
                .valid(true)
                .build();
        ArgumentCaptor<ValidationResult> validationResultCaptor = ArgumentCaptor.forClass(ValidationResult.class);

        when(replacementOrderMock.getParts()).thenReturn(partsMock);
        when(replacementOrderMock.getDepartment()).thenReturn(Department.REPLACEMENT);

        controller.createReplacementOrder(replacementOrderMock);

        verify(partRepository).saveAll(partsMock);
        verify(replacementOrderRepository).save(replacementOrderMock);
        verify(validationResultRepository).save(validationResultCaptor.capture());
        assertThat(expectedValidationResult)
                .usingRecursiveComparison()
                .isEqualTo(validationResultCaptor.getValue());
    }

    @Test
    public void getsAllValidationRequestHistory() {
        List<ValidationResult> expectedValidationResults = List.of(mock(ValidationResult.class));
        when(validationResultRepository.findAll()).thenReturn(expectedValidationResults);

        Iterable<ValidationResult> actualValidationResults = controller.getAllValidationRequestHistory();

        assertEquals(expectedValidationResults, actualValidationResults);
    }

}
