package com.ah.fyp.repository;

import com.ah.fyp.entity.LocationData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrimeDataRepository extends JpaRepository<LocationData, Integer> {

    LocationData findById(int name);

}

