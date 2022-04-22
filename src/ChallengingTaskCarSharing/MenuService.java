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

    public void addCompany() {
        System.out.println("\nEnter the company name:");
        String companyName = sc.next();
        companyDAO.addCompany(new Company(null, companyName));
        System.out.println("The company was created!");
    }

    public void addCar(Company company) {
        System.out.println("\nEnter the car name:");
        String carName = sc.next();
        carDAO.addCar(new Car(null, carName, company.getId()));
        System.out.println("The car was added!");
    }

    public void addCustomer() {
        System.out.println("\nEnter the customer name:");
        String customerName = sc.next();
        customerDAO.addCustomer(new Customer(customerName));
        System.out.println("The customer was added!");
    }

    public void rentACar(Customer customer) {
        if (customer.getRentedCar() == null) {
            List<Company> companies = companyDAO.getAllCompanies();
            if (!companies.isEmpty()) {
                System.out.printf("\nChoose a company:\n%s\n0. Back\n", getAsIndexedList(companies));
                Company company = (Company) chooseElementFromList(companies);
                if (company != null) {
                    List<Car> cars = carDAO.getAllAvailableCompanyCars();
                    if (!cars.isEmpty()) {
                        System.out.printf("\nChoose a car:\n%s\n0. Back\n", getAsIndexedList(cars));
                        Car car = (Car) chooseElementFromList(cars);
                        if (car != null) {
                            customerDAO.updateRentedCar(car, customer);
                            System.out.printf("\nYou rented '%s'\n", car.getName());
                            customer.setRentedCar(car);
                        }
                    } else {
                        System.out.printf("\nNo available cars in the '%s' company\n\n", company.getName());
                    }
                }
            } else {
                System.out.println("\nThe company list is empty!");
            }
        } else {
            System.out.println("\nYou've already rented a car!");
        }
    }

    public void returnRentedCar(Customer customer) {
        if (customer.getRentedCar() != null) {
            customerDAO.updateRentedCar(null, customer);
            customer.setRentedCar(null);
            System.out.println("\nYou've returned a rented car!");
        } else {
            System.out.println("\nYou didn't rent a car!\n");
        }
    }

    public void showRentedCar(Customer customer) {
        if (customer.getRentedCar() != null) {
            companyDAO.getAllCompanies().stream()
                    .filter(company -> company.getId().equals(customer.getRentedCar().getCompanyId()))
                    .findAny()
                    .ifPresent(company -> carDAO.getAllCompanyCars(company).stream()
                            .filter(car -> car.getCompanyId().equals(customer.getRentedCar().getCompanyId()))
                            .findAny()
                            .ifPresent(car -> System.out.printf("\nYour rented car:\n%s\nCompany:\n%s\n",
                                    car.getName(), company.getName())));
        } else {
            System.out.println("\nYou didn't rent a car!\n");
        }
    }

    public void showCompanies() {
        List<Company> companies = companyDAO.getAllCompanies();
        if (!companies.isEmpty()) {
            System.out.printf("\nChoose a company:\n%s\n0. Back\n", getAsIndexedList(companies));
            Company company = (Company) chooseElementFromList(companies);
            if (company != null) {
                view.companyMenu(company);
            }
        } else {
            System.out.println("\nThe company list is empty!\n");
        }
    }

    public void showCustomers() {
        List<Customer> customers = customerDAO.getAllCustomers();
        if (!customers.isEmpty()) {
            System.out.printf("\nCustomer list:\n%s\n0. Back\n", getAsIndexedList(customers));
            Customer customer = (Customer) chooseElementFromList(customers);
            if (customer != null) {
                view.customerMenu(customer);
            }
        } else {
            System.out.println("\nThe customer list is empty!");
        }
    }

    public void showCompanyCars(Company company) {
        List<Car> cars = carDAO.getAllCompanyCars(company);
        if (cars.isEmpty()) {
            System.out.println("\nThe car list is empty!\n");
        } else {
            System.out.printf("\nCar list:\n%s\n\n", getAsIndexedList(cars));
        }
    }

    public <T> String getAsIndexedList(List<T> list) {
        AtomicInteger tempId = new AtomicInteger(1);
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

    public <T> Object chooseElementFromList(List<T> list) {
        int input = Integer.parseInt(sc.next());
        if (input == 0 || input > list.size()) {
            return null;
        } else {
            return list.get(input - 1);
        }
    }
}