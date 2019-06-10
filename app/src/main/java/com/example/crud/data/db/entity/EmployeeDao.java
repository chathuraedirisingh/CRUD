package com.example.crud.data.db.entity;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Query("SELECT * FROM employee")
    LiveData<List<Employee>> getListEmployeeData();

    @Query("SELECT * FROM employee WHERE empId =:empId")
    LiveData<Employee> getEmployeeById(Integer empId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertEmployee(Employee employee);

    @Delete
    void deleteEmployee(Employee employee);

}
