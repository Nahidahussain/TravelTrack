package com.driver.services;

public interface DriverService {

		public void register(String mobile, String password,String name);

		public void removeDriver(int driverId);
		public void updateStatus(int driverId);
}
