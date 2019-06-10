package com.example.crud.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.crud.data.db.entity.Company;
import com.example.crud.data.db.entity.CompanyDao;
import com.example.crud.data.db.entity.Employee;
import com.example.crud.data.db.entity.EmployeeDao;

@Database(entities = {Employee.class, Company.class}, version = 1)
public abstract class AppDB extends RoomDatabase {

    public abstract EmployeeDao employeeDao();

    public abstract CompanyDao companyDao();

}
