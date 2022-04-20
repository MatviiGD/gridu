package ChallengingTaskCarSharing.dao;

import ChallengingTaskCarSharing.Database;
import ChallengingTaskCarSharing.entity.*;

import java.sql.SQLException;
import java.util.*;

public class CarDAOImpl implements CarDAO {
    private final Database database;

    public CarDAOImpl(Database database) {
        this.database = database;
    }

    @Override
    public void addCar(Car car) {
        try (var statement = database.getConnection().prepareStatement("INSERT INTO car(name,company_id) VALUES (?,?);")) {
            statement.setString(1, car.getName());
            statement.setInt(2, car.getCompanyId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while adding new company car: " + e.getMessage());
        }
    }

    @Override
    public List<Car> getAllCompanyCars(Company company) {
        LinkedList<Car> cars = new LinkedList<Car>();
        try (var statement = database.getConnection().prepareStatement("SELECT * FROM car WHERE company_id = ?;")) {
            statement.setInt(1, company.getId());
            var rs = statement.executeQuery();
            while (rs.next()) {
                cars.add(new Car(rs.getInt("id"), rs.getString("name"), rs.getInt("company_id")));
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving company cars data: " + e.getMessage());
        }
        return cars;
    }

    @Override
    public List<Car> getAllAvailableCompanyCars() {
        LinkedList<Car> availableCars = new LinkedList<Car>();
        try (var statement = database.getConnection().prepareStatement("SELECT * FROM car " + "LEFT JOIN customer ON car.id = customer.rented_car_id WHERE customer.name IS NULL;")) {
            var rs = statement.executeQuery();
            while (rs.next()) {
                availableCars.add(new Car(rs.getInt("id"), rs.getString("name"), rs.getInt("company_id")));
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving available company cars data: " + e.getMessage());
        }
        return availableCars;
    }
}