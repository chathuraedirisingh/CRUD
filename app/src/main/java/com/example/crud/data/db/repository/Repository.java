package com.example.crud.data.db.repository;



import android.arch.lifecycle.LiveData;

import com.example.crud.data.db.entity.Company;
import com.example.crud.data.db.entity.CompanyDao;
import com.example.crud.data.db.entity.Employee;
import com.example.crud.data.db.entity.EmployeeDao;

import java.util.List;

import javax.inject.Inject;

public class Repository {

    private final EmployeeDao employeeDao;
    private final CompanyDao companyDao;

    @Inject
    public Repository(EmployeeDao employeeDao,CompanyDao companyDao){
        this.employeeDao = employeeDao;
        this.companyDao = companyDao;
    }


    public LiveData<Employee> getEmployee(Integer empId){ return employeeDao.getEmployeeById(empId); }

    public LiveData<List<Employee>> getEmployees(){
        return employeeDao.getListEmployeeData();
    }

    public void deleteEmployee(Employee employee){
        employeeDao.deleteEmployee(employee);
    }

    public void insertEmployee(Employee employee){
        employeeDao.insertEmployee(employee);
    }

    public LiveData<Company> getCompany(String comId){ return companyDao.getCompanyById(comId); }

    public LiveData<List<Company>> getCompanies(){
        return companyDao.getListCompanyData();
    }

    public void deleteCompany(Company company){
        companyDao.deleteCompany(company);
    }

    public void insertCompany(Company company){
        companyDao.insertCompany(company);
    }
}
