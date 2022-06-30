package ChallengingTaskCarSharing.dao;

import ChallengingTaskCarSharing.entity.Company;

import java.util.List;

public interface CompanyDAO {
    void addCompany(Company company);

    List<Company> getAllCompanies();
}