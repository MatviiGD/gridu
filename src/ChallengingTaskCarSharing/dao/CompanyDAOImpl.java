package ChallengingTaskCarSharing.dao;


import ChallengingTaskCarSharing.Database;
import ChallengingTaskCarSharing.entity.Company;

import java.sql.SQLException;
import java.util.*;

public class CompanyDAOImpl implements CompanyDAO {
    private final Database database;

    public CompanyDAOImpl(Database database) {
        this.database = database;
    }

    @Override
    public void addCompany(Company company) {
        try (var statement = database.getConnection().prepareStatement("INSERT INTO company(name) VALUES ?;")) {
            statement.setString(1, company.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while adding new company: " + e.getMessage());
        }
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = new LinkedList<Company>();
        try (var statement = database.getConnection().prepareStatement("SELECT * FROM company;")) {
            var rs = statement.executeQuery();
            while (rs.next()) {
                companies.add(new Company(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving companies data: " + e.getMessage());
        }
        return companies;
    }
}