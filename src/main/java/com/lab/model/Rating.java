package com.lab.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Entity(name = "RATINGS")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "RATING")
    @NotNull
    public int rating;

    @Column(name = "COMMENT")
    public String comment;

    @Column(name = "TIMESTAMP")
    @NotNull
    public Date timeStamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAR_ID", nullable = false)
    @NotNull
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    @NotNull
    private User user;
}

