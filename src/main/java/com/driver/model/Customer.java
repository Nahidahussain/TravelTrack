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
public class Customer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int custmId;

    String name;

    String password;

    String mobile;

    @OneToMany (mappedBy = "customer", cascade = CascadeType.ALL)
    List<TripBooking> bookingList = new ArrayList<>();
}