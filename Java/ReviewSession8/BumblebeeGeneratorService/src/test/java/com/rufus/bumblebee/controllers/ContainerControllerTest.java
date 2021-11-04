package com.rufus.bumblebee.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rufus.bumblebee.controllers.requests.ContainerRequest;
import com.rufus.bumblebee.controllers.requests.ReportType;
import com.rufus.bumblebee.services.dto.ContainerDto;
import com.rufus.bumblebee.services.dto.ContainerStatus;
import com.rufus.bumblebee.services.dto.HistoryDto;
import com.rufus.bumblebee.services.interfaces.ContainerService;
import com.rufus.bumblebee.services.interfaces.ContainerHistoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = ContainerController.class)
public class ContainerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ContainerService service;

    @MockBean
    private ContainerHistoryService<HistoryDto, String> containerHistoryService;

    private static final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private static final String TEST_VALUE = "TEST";

    @Test
    public void testAddContainer() throws Exception {
        ContainerDto dto = getContainerDto();
        ResponseEntity<ContainerDto> baseResponse = new ResponseEntity<>(dto, HttpStatus.OK);
        given(service.createContainer(TEST_VALUE, false, ReportType.EXCEL_TYPE)).willReturn(dto);

        ContainerRequest request = new ContainerRequest();
        request.setHistoryOn(false);
        request.setReportType(ReportType.EXCEL_TYPE);
        request.setName(TEST_VALUE);

        MockHttpServletResponse response = mvc.perform(post("/containers")
                        .contentType(MediaType.APPLICATION_JSON).content(ow.writeValueAsString(request)))
                .andExpect(status().isOk()).
                andReturn().getResponse();
        assertNotNull(response.getContentAsString());
        assertEquals(response.getStatus(), baseResponse.getStatusCode().value());
    }


    @Test
    public void testRemoveContainer() throws Exception {
        MockHttpServletResponse response = mvc.perform(delete("/containers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).
                andReturn().getResponse();
        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void testGetHistory() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/containers/1/history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).
                andReturn().getResponse();
        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    private ContainerDto getContainerDto() {
        ContainerDto dto = new ContainerDto();
        dto.setCuid("1L");
        dto.setName(TEST_VALUE);
        dto.setStatus(ContainerStatus.NEW);
        return dto;
    }
}


