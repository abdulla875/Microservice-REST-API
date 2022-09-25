package com.ah.fyp.model;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CrimeAmount {

    private String area;
    private int amountOfCrime;
    private String latitude;
    private String longitude;
    private List<CrimeType> crimeType;
    private String month;
}



