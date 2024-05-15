package com.driver.services.impl;

import com.driver.model.*;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer = customerRepository2.findById(customerId).get();
//		customerRepository2.delete(customer);
		List<TripBooking> bookedTrip = customer.getBookingList();
		for(TripBooking trip : bookedTrip){
			Driver driver = trip.getDriver();
			Cab cab = driver.getCab();
			driverRepository2.save(driver);
			trip.setStatus(TripStatus.CANCELED);
		}

		customerRepository2.delete(customer);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> drivers = driverRepository2.findAll();
		Driver driver = null;
		for(Driver currDriver : drivers){
			if(currDriver.getCab().isAvailble()){
				if(driver == null || currDriver.getDriverId() < driver.getDriverId()){
					driver = currDriver;
				}
			}
		}
		if(driver == null){
			throw new Exception("No Cab is Availble");
		}

		TripBooking newtripBooking = new TripBooking();
		newtripBooking.setFromLocation(fromLocation);
		newtripBooking.setToLocation(toLocation);
		newtripBooking.setDistanceInKm(distanceInKm);
		newtripBooking.setDriver(driver);
		newtripBooking.setCustomer(customerRepository2.findById(customerId).get());
		newtripBooking.setStatus(TripStatus.CONFIRMED);

		int rate = driver.getCab().getPerKmRate();
		newtripBooking.setBill(distanceInKm*rate);

		driver.getCab().setAvailble(false);
		driverRepository2.save(driver);

		Customer customer = customerRepository2.findById(customerId).get();
		customer.getBookingList().add(newtripBooking);

		customerRepository2.save(customer);

		return newtripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking bookedTrip = tripBookingRepository2.findById(tripId).get();
		bookedTrip.setStatus(TripStatus.CANCELED);
		bookedTrip.setBill(0);
		bookedTrip.getDriver().getCab().setAvailble(true);

		tripBookingRepository2.save(bookedTrip);
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking bookedTrip = tripBookingRepository2.findById(tripId).get();
		bookedTrip.setStatus(TripStatus.COMPLETED);
		bookedTrip.getDriver().getCab().setAvailble(true);

		tripBookingRepository2.save(bookedTrip);
	}
}
