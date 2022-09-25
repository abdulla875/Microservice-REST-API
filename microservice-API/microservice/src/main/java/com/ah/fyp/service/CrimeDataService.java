package com.ah.fyp.service;

import com.ah.fyp.model.CrimeAmount;

import java.io.IOException;
import java.util.List;

public interface CrimeDataService {
     List<CrimeAmount> getCrimeDataFromDb(String lat, String lng) throws IOException;

     List<CrimeAmount> getCrimeDataByLocation(String lat, String lng, String date) throws IOException;


}
