package com.andriusdgt.workmanager.controller;

import com.andriusdgt.workmanager.model.ValidationResult;
import com.andriusdgt.workmanager.repository.ValidationResultRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class WorkControllerIntegrationTest {

    @MockBean
    private ValidationResultRepository validationResultRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createsAnalysisWorkOrder() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJsonBody = objectMapper.createObjectNode()
                .put("department", "GOD analysis department")
                .put("start_date", "2020-08-13")
                .put("end_date", "2020-08-15")
                .put("currency", "USD")
                .put("cost", 123.12)
                .set("parts", objectMapper.createArrayNode()
                        .add(objectMapper.createObjectNode()
                                .put("inventory_number", "InventoryNumber1")
                                .put("name", "PartNumber1")
                                .put("count", 1)
                        )
                )
                .toString();

        this.mockMvc
                .perform(put("/work-order/analysis").contentType("application/json").content(requestJsonBody))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void returnsSeveralValidationErrors() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJsonBody = objectMapper.createObjectNode()
                .put("department", "GODanalysisdepartment")
                .put("start_date", "2020-08-13")
                .put("end_date", "2020-08-15")
                .put("currency", "USD")
                .put("cost", -123.12)
                .set("parts", objectMapper.createArrayNode())
                .toString();

        MvcResult result = this.mockMvc
                .perform(put("/work-order/analysis").contentType("application/json").content(requestJsonBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .contains("must be greater than 0")
                .contains("must be a valid department");
    }

    @Test
    public void showsSerializationErrorForUnrecognizedField() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJsonBody = objectMapper.createObjectNode()
                .put("department", "GOD analysis department")
                .put("startDate", "2020-08-13")
                .put("end_date", "2020-08-15")
                .put("currency", "USD")
                .put("cost", 123.12)
                .set("parts", objectMapper.createArrayNode())
                .toString();

        MvcResult result = this.mockMvc
                .perform(put("/work-order/analysis").contentType("application/json").content(requestJsonBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .contains("Unrecognized field")
                .contains("startDate");
    }

    @Test
    public void showsSerializationErrorForWrongDate() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJsonBody = objectMapper.createObjectNode()
                .put("department", "GOD analysis department")
                .put("start_date", "20120-08-13")
                .put("end_date", "2020-08-15")
                .put("currency", "USD")
                .put("cost", 123.12)
                .set("parts", objectMapper.createArrayNode())
                .toString();

        MvcResult result = this.mockMvc
                .perform(put("/work-order/analysis").contentType("application/json").content(requestJsonBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .contains("Cannot deserialize value of type `java.time.LocalDate`")
                .contains("20120-08-13");
    }

    @Test
    public void showsSerializationErrorForWrongCurrency() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJsonBody = objectMapper.createObjectNode()
                .put("department", "GOD analysis department")
                .put("start_date", "2020-08-13")
                .put("end_date", "2020-08-15")
                .put("currency", "USsD")
                .put("cost", 123.12)
                .set("parts", objectMapper.createArrayNode())
                .toString();

        MvcResult result = this.mockMvc
                .perform(put("/work-order/analysis").contentType("application/json").content(requestJsonBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .contains("Cannot deserialize value of type `java.util.Currency`")
                .contains("USsD");
    }

    @Test
    public void savesValidationResult() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ArgumentCaptor<ValidationResult> validationResultCaptor = ArgumentCaptor.forClass(ValidationResult.class);
        ValidationResult expectedValidationResult = ValidationResult.builder()
                .date(LocalDate.now())
                .workOrderType("analysisOrder")
                .department(null)
                .valid(false)
                .build();
        String requestJsonBody = objectMapper.createObjectNode()
                .put("department", "GOD analysis department")
                .put("start_date", "2020-08-13")
                .put("end_date", "2020-08-15")
                .put("currency", "USD")
                .put("cost", -123.12)
                .set("parts", objectMapper.createArrayNode())
                .toString();

        this.mockMvc
                .perform(put("/work-order/analysis").contentType("application/json").content(requestJsonBody))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(validationResultRepository, times(1)).save(validationResultCaptor.capture());
        assertThat(validationResultCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(expectedValidationResult);
    }

}
