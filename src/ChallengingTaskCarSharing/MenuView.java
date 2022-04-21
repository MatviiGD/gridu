package ChallengingTaskCarSharing;

import ChallengingTaskCarSharing.dao.*;
import ChallengingTaskCarSharing.entity.*;

import java.util.Scanner;

public class MenuView {
    private final Scanner sc = new Scanner(System.in).useDelimiter("\n");
    private final MenuService service;

    public MenuView(Database database) {
        CarDAOImpl carDAO = new CarDAOImpl(database);
        CompanyDAOImpl companyDAO = new CompanyDAOImpl(database);
        CustomerDAOImpl customerDAO = new CustomerDAOImpl(database);
        this.service = new MenuService(carDAO, companyDAO, customerDAO, this);
        mainMenu();
    }

    private void mainMenu() {
        while (true) {
            System.out.println("\n1. Log in as a manager\n2. Log in as a customer\n3. Create a customer\n0. Exit");
            int input = Integer.parseInt(sc.next());
            if (0 == input) {
                return;
            }
            if (1 == input) {
                managerMenu();
            }
            if (2 == input) {
                service.showCustomers();
            }
            if (3 == input) {
                service.addCustomer();
            }
        }
    }

    private void managerMenu() {
        while (true) {
            System.out.println("\n1. Company list\n2. Create a company\n0. Back");
            int input = Integer.parseInt(sc.next());
            if (0 == input) {
                return;
            }
            if (1 == input) {
                service.showCompanies();
            }
            if (2 == input) {
                service.addCompany();
            }
        }
    }

    public void customerMenu(Customer customer) {
        while (true) {
            System.out.println("\n1. Rent a car\n2. Return a rented car\n3. My rented car\n0. Back");
            int input= Integer.parseInt(sc.next());
            if (0 == input) {
                return;
            }
            if (1 == input) {
                service.rentACar(customer);
            }
            if (2 == input) {
                service.returnRentedCar(customer);
            }
            if (3 == input) {
                service.showRentedCar(customer);
            }
        }
    }

    public void companyMenu(Company company) {
        System.out.printf("'%s' company", company.getName());
        while (true) {
            System.out.println("\n1. Car list\n2. Create a car\n0. Back");
            int input = Integer.parseInt(sc.next());
            if (0 == input) {
                return;
            }
            if (1 == input) {
                service.showCompanyCars(company);
            }
            if (2 == input) {
                service.addCar(company);
            }
        }
    }
}