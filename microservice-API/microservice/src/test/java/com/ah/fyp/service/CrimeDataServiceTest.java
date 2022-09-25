package com.ah.fyp.service;

import com.ah.fyp.entity.LocationData;
import com.ah.fyp.model.CrimeAmount;
import com.ah.fyp.repository.CrimeDataRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CrimeDataServiceTest {
    @Autowired
    private CrimeDataService crimeDataService;
    @MockBean
    private CrimeDataRepository crimeDataRepository;
    @Test
    public void testGetCrimeDataFroDbMustNotBeNull() throws IOException {

        LocationData locationData = LocationData.builder()
                .id(1)
                .crimeType("robbery")
                .latitude("1.2")
                .longitude("-0.5")
                .name("86 main street")
                .build();
        List<LocationData> locationList = new ArrayList<>();
        locationList.add(locationData);
        Mockito.when(crimeDataRepository.findAll()).thenReturn(locationList);
        List<CrimeAmount> crimeAmountList = crimeDataService.getCrimeDataByLocation("52.243840", "0.23456", "09-2021");
        assertNotNull(crimeAmountList);
    }





    @Test
    public void testGetCrimeDataShouldNotReturnDuplicateCrimeListWithSameArea() throws IOException {

        LocationData locationData = LocationData.builder()
                .id(1)
                .crimeType("theft")
                .latitude("1.2")
                .longitude("-0.5")
                .name("86 long street")
                .build();
        LocationData locationDat1 = LocationData.builder()
                .id(1)
                .crimeType("anti-social-behaviour")
                .latitude("1.24")
                .longitude("-0.5")
                .name("On or near Wharf Street North")
                .build();
        List<LocationData> locationList = new ArrayList<>();
        locationList.add(locationData);
        locationList.add(locationDat1);

        Mockito.when(crimeDataRepository.findAll()).thenReturn(locationList);
        List<CrimeAmount> crimeAmountList = crimeDataService.getCrimeDataByLocation("52.243840", "0.23456", "09-2021");
        assertEquals(crimeAmountList.size(), 1);
    }
}