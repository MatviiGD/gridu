package ChallengingTaskCarSharing.entity;

public class Customer {
    private final String name;
    private Car rentedCar;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Car getRentedCar() {
        return rentedCar;
    }

    public void setRentedCar(Car rentedCar) {
        this.rentedCar = rentedCar;
    }
}