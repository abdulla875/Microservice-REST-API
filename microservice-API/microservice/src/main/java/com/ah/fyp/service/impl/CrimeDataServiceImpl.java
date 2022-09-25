package com.ah.fyp.service.impl;

import com.ah.fyp.entity.LocationData;
import com.ah.fyp.model.CrimeAmount;
import com.ah.fyp.model.CrimeType;
import com.ah.fyp.model.Root;
import com.ah.fyp.repository.CrimeDataRepository;
import com.ah.fyp.service.CrimeDataService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CrimeDataServiceImpl implements CrimeDataService {

    @Autowired
    private CrimeDataRepository crimeDataRepository;
    private CloseableHttpClient client = HttpClientBuilder.create().build();

    @Override
    public List<CrimeAmount> getCrimeDataByLocation(String lat, String lng, String date) throws IOException {
        HttpGet request = new HttpGet("https://data.police.uk/api/crimes-street/all-crime?lat="+lat+"&lng="+lng+"&date="+date);
        CloseableHttpResponse response = null;
        response = client.execute(request);




        Type collectionType = new TypeToken<List<Root>>(){}.getType();
        List<Root> listOfCrimeData = (List<Root>) new Gson()
                .fromJson( EntityUtils.toString(response.getEntity()) , collectionType);


        Set<String> locationSet = extractStreetName(listOfCrimeData);


        Object[] streetNameArray = locationSet.toArray();



        List<CrimeAmount> crimeAmountList = new ArrayList<>();
        for(int j=0;j<locationSet.size();j++){
            String streetNames = streetNameArray[j].toString();
            List<Root>  filteredArticleList = listOfCrimeData.stream()
                    .filter(article -> article.getLocation().getStreet().getName().contains(streetNames))
                    .collect(Collectors.toList());

            List<CrimeType> crimeTypeList = new ArrayList<>();
            for (Root root : filteredArticleList) {
                crimeTypeList.add(CrimeType.builder().cType(root.getCategory()).build());
            }
            Set<String> crimeTypeSet = new HashSet<>();
            for (CrimeType location : crimeTypeList) {
                crimeTypeSet.add(location.getCType());
            }

            Object[] crimeTypeArray = crimeTypeSet.toArray();

            List<CrimeType> crimeTypeListResult = getCrimeTypes(crimeTypeList, crimeTypeArray);
            crimeAmountList.add(CrimeAmount.builder()
                    .area(streetNames)
                    .amountOfCrime(filteredArticleList.size())
                    .latitude(filteredArticleList.get(0).getLocation().getLatitude())
                    .longitude(filteredArticleList.get(0).getLocation().getLongitude())
                    .crimeType(crimeTypeListResult)
                    .build());
        }
        return crimeAmountList ;
    }



    private List<CrimeType> getCrimeTypes(List<CrimeType> crimeTypeList, Object[] crimeTypeArray) {
        List<CrimeType> crimeTypeListResult = new ArrayList<>();
        for (Object o : crimeTypeArray) {
            String ct = o.toString();
            List<CrimeType> ctList = crimeTypeList.stream()
                    .filter(article -> article.getCType().contains(ct))
                    .collect(Collectors.toList());

            if (o.equals(ct)) {
                crimeTypeListResult.add(CrimeType.builder().cType(ct).amount(ctList.size()).build());
            }

        }
        return crimeTypeListResult;
    }





    private Set<String> extractStreetName(List<Root> lcs) {
        Set<String> locationSet = new HashSet<>();
        for (Root location : lcs) {
            locationSet.add(location.getLocation().getStreet().getName());
        }
        return locationSet;
    }




    @Override
    public List<CrimeAmount> getCrimeDataFromDb(String lat, String lng) throws IOException {
        HttpGet request = new HttpGet("https://data.police.uk/api/crimes-street/all-crime?lat="+lat+"&lng="+lng);
            CloseableHttpResponse response = null;

            response = client.execute(request);

            Type collectionType = new TypeToken<List<com.ah.fyp.model.Root>>(){}.getType();
            List<com.ah.fyp.model.Root> lcs = (List<Root>) new Gson()
                    .fromJson( EntityUtils.toString(response.getEntity()) , collectionType);

            for (com.ah.fyp.model.Root value : lcs) {
                crimeDataRepository.save(LocationData.builder()
                        .latitude(value.getLocation().getLatitude())
                        .longitude(value.getLocation().getLongitude())
                        .sId(value.getLocation().street.getId())
                        .name(value.getLocation().getStreet().getName())
                        .crimeType(value.getCategory())
                        .month(value.getMonth())
                        .build());
            }


        List<LocationData> locationList =  crimeDataRepository.findAll();
        Set<String> locationSet = new HashSet<>();
        for (LocationData location : locationList) {
            locationSet.add(location.getName());

        }
        Object[] myArr = locationSet.toArray();


        List<CrimeAmount> lst = new ArrayList<>();
        for(int j=0;j<locationSet.size();j++){
            String s = myArr[j].toString();
            List<LocationData>  filteredArticleList = locationList.stream()
                    .filter(article -> article.getName().contains(s))
                    .collect(Collectors.toList());

            List<CrimeType> crimeTypeList = new ArrayList<>();


            for (int i=0;i<filteredArticleList.size();i++) {
                crimeTypeList.add(CrimeType.builder().cType(filteredArticleList.get(i).getCrimeType()).build());
            }
            Set<String> crimeTypeSet = new HashSet<>();
            for (CrimeType location : crimeTypeList) {
                crimeTypeSet.add(location.getCType());
            }

            Object[] crimeTypeArray = crimeTypeSet.toArray();

            List<CrimeType> crimeTypeListResult = new ArrayList<>();
            for(int k=0;k<crimeTypeArray.length;k++) {
                String ct = crimeTypeArray[k].toString();
                List<CrimeType> ctList = crimeTypeList.stream()
                        .filter(article -> article.getCType().contains(ct))
                        .collect(Collectors.toList());

                if(crimeTypeArray[k].equals(ct)){
                    crimeTypeListResult.add(CrimeType.builder().cType(ct).amount(ctList.size()).build());
                }

            }
            lst.add(CrimeAmount.builder()
                    .area(s)
                    .amountOfCrime(filteredArticleList.size())
                    .latitude(filteredArticleList.get(0).getLatitude())
                    .longitude(filteredArticleList.get(0).getLongitude())
                    .crimeType(crimeTypeListResult)
                    .build());
            System.out.println("In total "+ filteredArticleList.size() + " Crimes found in:  " + s);
        }

        return lst;
    }

}
