package com.driver.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Data
public class Driver{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int driverId;

    String name;
    String mobile;
    String password;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    List<TripBooking> tripBookingList = new ArrayList<>();

    @OneToOne (mappedBy = "driver" ,cascade = CascadeType.ALL)
    Cab cab;
}