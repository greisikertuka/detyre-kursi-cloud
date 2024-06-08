package com.lab.repository;

import com.lab.model.Car;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class CarRepository implements PanacheRepository<Car> {
}
