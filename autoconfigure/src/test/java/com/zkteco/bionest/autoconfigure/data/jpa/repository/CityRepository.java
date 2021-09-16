package com.zkteco.bionest.autoconfigure.data.jpa.repository;

import com.zkteco.bionest.autoconfigure.data.jpa.entity.City;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City,Integer>, JpaSpecificationExecutor<City> {
}
