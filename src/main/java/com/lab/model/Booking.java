package com.lab.model;

import com.lab.model.enums.BookingStatus;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;


@Getter
@Setter
@Entity(name = "BOOKINGS")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "NAME")
    @NotNull
    public String name;

    @Column(name = "LAST_NAME")
    @NotNull
    public String lastName;

    @Column(name = "EMAIL")
    @NotNull
    public String email;

    @Column(name = "PHONE")
    @NotNull
    public String phone;

    @Column(name = "START_DATE")
    @NotNull
    public Date startDate;

    @Column(name = "END_DATE")
    @NotNull
    public Date endDate;

    @Column(name = "TIMESTAMP")
    @NotNull
    public Date timeStamp;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    @NotNull
    public BookingStatus bookingStatus;

    @Column(name = "TOTAL")
    @NotNull
    public float total;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RATING_ID", referencedColumnName = "ID")
    public Rating rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAR_ID", nullable = false)
    @NotNull
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    @NotNull
    private User user;
}

