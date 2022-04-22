package ChallengingTaskCarSharing.dao;

import ChallengingTaskCarSharing.entity.*;

import java.util.List;

public interface CarDAO {
    void addCar(Car car);

    List<Car> getAllCompanyCars(Company company);

    List<Car> getAllAvailableCompanyCars();
}