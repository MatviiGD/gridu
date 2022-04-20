package ChallengingTaskCarSharing.dao;

import ChallengingTaskCarSharing.Database;
import ChallengingTaskCarSharing.entity.*;

import java.sql.SQLException;
import java.util.*;

public class CustomerDAOImpl implements CustomerDAO {
    private final Database database;

    public CustomerDAOImpl(Database database) {
        this.database = database;
    }

    @Override
    public void addCustomer(Customer customer) {
        try (var statement = database.getConnection().prepareStatement("INSERT INTO customer(name) VALUES ?;")) {
            statement.setString(1, customer.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while adding new customer: " + e.getMessage());
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new LinkedList<Customer>();
        try (var statement = database.getConnection().prepareStatement("SELECT * FROM customer;")) {
            var rs = statement.executeQuery();
            while (rs.next()){
                customers.add(new Customer(rs.getString("name")));
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving customer data: " + e.getMessage());
        }
        return customers;
    }

    @Override
    public void updateRentedCar(Car car, Customer customer) {
        try (var statement = database.getConnection()
                .prepareStatement("UPDATE customer SET rented_car_id = ? WHERE name = ?;")) {
            if (car == null){
                statement.setNull(1, 1);
            } else{
                statement.setInt(1, car.getId());
            }
            statement.setString(2, customer.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating a customer table: " + e.getMessage());
        }
    }
}