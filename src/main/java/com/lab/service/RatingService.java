package com.lab.service;

import com.lab.model.Rating;
import com.lab.model.User;
import com.lab.repository.BookingRepository;
import com.lab.repository.CarRepository;
import com.lab.repository.RatingRepository;
import com.lab.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
public class RatingService {

    @Inject
    BookingRepository bookingRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    CarRepository carRepository;

    @Inject
    RatingRepository ratingRepository;

    public List<Rating> findRatingsByCarId(Long carId) {
        var car = carRepository.findById(carId);
        if (car == null) {
            throw new NotFoundException();
        }
        return car.getRatings();
    }

    public List<Rating> findRatingsByUserId(Long userId) {
        var user = userRepository.findById(userId);
        if (user == null) {
            throw new NotFoundException();
        }
        return user.getRatings();
    }

    @Transactional
    public void insertRating(Long bookingId, Long userId, Long carId, Rating rating) {
        User user;
        user = userRepository.findById(userId);
        if (user == null) {
            throw new NotFoundException();
        }

        var car = carRepository.findById(carId);
        if (car == null) {
            throw new NotFoundException();
        }

        var booking = bookingRepository.findById(bookingId);
        if (booking == null) {
            throw new NotFoundException();
        }
        car.getBookings().remove(booking);
        booking.setUser(user);
        booking.setCar(car);
        booking.setRating(rating);
        car.getBookings().add(booking);
        car.setAverageRating((car.getAverageRating() * car.getReviewsCount() + rating.getRating()) / (car.reviewsCount + 1));
        car.setReviewsCount(car.getReviewsCount() + 1);
        carRepository.getEntityManager().merge(car);
    }

    @Transactional
    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }

    @Transactional
    public void updateRating(Rating rating) {
        ratingRepository.getEntityManager().merge(rating);
    }

    public List<Rating> findAllRatings() {
        return ratingRepository.listAll();
    }

    public Rating findById(Long id) {
        return ratingRepository.findById(id);
    }
}
