package ChallengingTaskCarSharing.dao;

import ChallengingTaskCarSharing.entity.*;

import java.util.List;

public interface CustomerDAO {
    void addCustomer(Customer customer);

    List<Customer> getAllCustomers();

    void updateRentedCar(Car car, Customer customerName);
}