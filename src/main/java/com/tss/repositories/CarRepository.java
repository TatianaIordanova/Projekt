package com.tss.repositories;

import com.tss.entities.Car;
import org.springframework.data.repository.CrudRepository;


public interface CarRepository extends CrudRepository<Car, Long>{
    
}
