package com.lab.service;

import com.lab.model.Booking;
import com.lab.repository.BookingRepository;
import com.lab.repository.CarRepository;
import com.lab.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
public class BookingService {

    @Inject
    UserRepository userRepository;

    @Inject
    CarRepository carRepository;

    @Inject
    BookingRepository bookingRepository;

    public List<Booking> findBookingsByCarId(Long carId) {
        var car = carRepository.findById(carId);
        if (car == null) {
            throw new NotFoundException();
        }
        return car.getBookings();
    }

    public List<Booking> findBookingsByUserId(Long userId) {
        var user = userRepository.findById(userId);
        if (user == null) {
            throw new NotFoundException();
        }
        user.setPassword("");
        return user.getBookings();
    }

    @Transactional
    public void insertBooking(Long userId, Long carId, Booking booking) {
        var user = userRepository.findById(userId);
        if (user == null) {
            throw new NotFoundException();
        }

        var car = carRepository.findById(carId);
        if (car == null) {
            throw new NotFoundException();
        }
        booking.setCar(car);
        user.getBookings().add(booking);
        userRepository.getEntityManager().merge(user);
    }

    @Transactional
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Transactional
    public void updateBooking(Booking booking) {
        bookingRepository.getEntityManager().merge(booking);
    }

    public List<Booking> findAllBookings() {
        return bookingRepository.listAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id);
    }
}
