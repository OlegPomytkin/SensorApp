package ru.SensorApp;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.SensorApp.controllers.MeasurementController;
import ru.SensorApp.models.Measurement;
import ru.SensorApp.repositories.MeasurementRepository;
import ru.SensorApp.services.MeasurementService;
import ru.SensorApp.util.MeasurementValidator;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(MeasurementController.class)
public class MeasurementsMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeasurementValidator measurementValidator;

    @MockBean
    private MeasurementRepository measurementRepository;
    @MockBean(name = "MeasurementService")
    private MeasurementService measurementService;

    private List<Measurement> measurementsList = new ArrayList<>();



    @Before
    public void setup(){

       measurementsList.add(new Measurement((float) 25.1, true));
       measurementsList.add(new Measurement((float)-15.1, true));
       measurementsList.add(new Measurement((float)-10.8, false));

    }

    @Test
    public void returnAllMeasurements() throws Exception{


        when(measurementService.findAll()).thenReturn(measurementsList);

        this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/measurements"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].value",is(-10.8)));

    }

    @Test
    public void rainyDaysCount() throws Exception{

        when(measurementService.countRaining()).thenReturn(measurementsList.stream().filter(m -> m.isRaining()).count());

        this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/measurements/rainyDaysCount"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".countRainingDays",is(List.of(2))));

    }


}
