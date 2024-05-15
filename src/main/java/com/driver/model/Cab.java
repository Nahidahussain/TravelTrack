package com.driver.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Data
public class Cab{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int cabId;

    int perKmRate;
    boolean availble;

    @OneToOne
    @JoinColumn
    Driver driver;
}