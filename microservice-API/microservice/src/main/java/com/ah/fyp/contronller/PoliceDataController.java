package com.ah.fyp.contronller;

import com.ah.fyp.model.CrimeAmount;
import com.ah.fyp.service.CrimeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class PoliceDataController {

   @Autowired
   private CrimeDataService crimeDataService;



    @GetMapping("/get-crime-data-by-location")
    @ResponseBody
    public List<CrimeAmount> getCrimeData(@RequestParam String lat, @RequestParam String lng, @RequestParam String date)
            throws Exception {
        return  crimeDataService.getCrimeDataByLocation(lat,lng,date);
    }




    @GetMapping("/get-crime-data-fromDb")
    @ResponseBody
    public List<CrimeAmount> getCrimeDataFromDb(@RequestParam String lat, @RequestParam String lng) throws IOException {
        return crimeDataService.getCrimeDataFromDb(lat, lng);
    }
}








