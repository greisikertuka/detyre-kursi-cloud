package com.lab.service;

import com.lab.model.Car;
import com.lab.repository.CarRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
public class CarService {

    @Inject
    CarRepository carRepository;


    @Transactional
    public void insertCar(Car car) {
        carRepository.persist(car);
    }

    @Transactional
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Transactional
    public void updateCar(Car car) {
        carRepository.getEntityManager().merge(car);
    }

    public List<Car> findAllCars() {
        return carRepository.listAll();
    }

    public Car findCarById(Long id) {
        return carRepository.findById(id);
    }
}
