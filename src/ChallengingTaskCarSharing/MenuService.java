package ChallengingTaskCarSharing;

import ChallengingTaskCarSharing.dao.*;
import ChallengingTaskCarSharing.entity.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MenuService {
    private final Scanner sc;
    private final CustomerDAO customerDAO;
    private final CompanyDAO companyDAO;
    private final CarDAO carDAO;
    private final MenuView view;

    public MenuService(CarDAO carDAO, CompanyDAO companyDAO, CustomerDAO customerDAO, MenuView view) {
        this.sc = new Scanner(System.in).useDelimiter("\n");
        this.carDAO = carDAO;
        this.companyDAO = companyDAO;
        this.customerDAO = customerDAO;
        this.view = view;
    }

    void addCompany() {
        System.out.println("\nEnter the company name:");
        var companyName = sc.next();
        companyDAO.addCompany(new Company(null, companyName));
        System.out.println("The company was created!");
    }

    void addCar(Company company) {
        System.out.println("\nEnter the car name:");
        var carName = sc.next();
        carDAO.addCar(new Car(null, carName, company.getId()));
        System.out.println("The car was added!");
    }

    void addCustomer() {
        System.out.println("\nEnter the customer name:");
        var customerName = sc.next();
        customerDAO.addCustomer(new Customer(customerName));
        System.out.println("The customer was added!");
    }

    void rentACar(Customer customer) {
        if (customer.getRentedCar() == null) {
            var companies = companyDAO.getAllCompanies();
            if (!companies.isEmpty()) {
                System.out.printf("\nChoose a company:\n%s\n0. Back\n", getAsIndexedList(companies));
                var company = chooseElementFromList(companies);
                if (company != null) {
                    var cars = carDAO.getAllAvailableCompanyCars();
                    if (!cars.isEmpty()) {
                        System.out.printf("\nChoose a car:\n%s\n0. Back\n", getAsIndexedList(cars));
                        var car = chooseElementFromList(cars);
                        if (car != null) {
                            customerDAO.updateRentedCar((Car) car, customer);
                            System.out.printf("\nYou rented '%s'\n", ((Car) car).getName());
                            customer.setRentedCar((Car) car);
                        }
                    } else System.out.printf("\nNo available cars in the '%s' company\n\n",
                            ((Company) company).getName());
                }
            } else System.out.println("\nThe company list is empty!");
        } else System.out.println("\nYou've already rented a car!");
    }

    void returnRentedCar(Customer customer) {
        if (customer.getRentedCar() != null) {
            customerDAO.updateRentedCar(null, customer);
            customer.setRentedCar(null);
            System.out.println("\nYou've returned a rented car!");
        } else System.out.println("\nYou didn't rent a car!\n");
    }

    void showRentedCar(Customer customer) {
        if (customer.getRentedCar() != null) {
            companyDAO.getAllCompanies().stream()
                    .filter(company -> company.getId().equals(customer.getRentedCar().getCompanyId()))
                    .findAny()
                    .ifPresent(company -> carDAO.getAllCompanyCars(company).stream()
                            .filter(car -> car.getCompanyId().equals(customer.getRentedCar().getCompanyId()))
                            .findAny()
                            .ifPresent(car -> System.out.printf("\nYour rented car:\n%s\nCompany:\n%s\n",
                                    car.getName(), company.getName())));
        } else System.out.println("\nYou didn't rent a car!\n");
    }

    void showCompanies() {
        var companies = companyDAO.getAllCompanies();
        if (!companies.isEmpty()) {
            System.out.printf("\nChoose a company:\n%s\n0. Back\n", getAsIndexedList(companies));
            var company = chooseElementFromList(companies);
            if (company != null) view.companyMenu((Company) company);
        } else System.out.println("\nThe company list is empty!\n");
    }

    void showCustomers() {
        var customers = customerDAO.getAllCustomers();
        if (!customers.isEmpty()) {
            System.out.printf("\nCustomer list:\n%s\n0. Back\n", getAsIndexedList(customers));
            var customer = chooseElementFromList(customers);
            if (customer != null) view.customerMenu((Customer) customer);
        } else System.out.println("\nThe customer list is empty!");
    }

    void showCompanyCars(Company company) {
        var cars = carDAO.getAllCompanyCars(company);
        if (cars.isEmpty()) System.out.println("\nThe car list is empty!\n");
        else System.out.printf("\nCar list:\n%s\n\n", getAsIndexedList(cars));
    }

    <T> String getAsIndexedList(List<T> list) {
        var tempId = new AtomicInteger(1);
        return list.stream()
                .map(c -> {
                    try {
                        return c.getClass().getMethod("getName").invoke(c);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        return null;
                    }
                })
                .map(c -> String.format("%d. %s", tempId.getAndIncrement(), c))
                .collect(Collectors.joining("\n"));
    }

    <T> Object chooseElementFromList(List<T> list) {
        var input = Integer.parseInt(sc.next());
        if (input == 0 || input > list.size()) return null;
        else return list.get(input - 1);
    }
}