package com.example.crud.viewmodel.employee;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.crud.data.db.entity.Employee;
import com.example.crud.data.db.repository.Repository;

public class ListEmployeeViewModel extends ViewModel {

    private Repository repository;

    public ListEmployeeViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<Employee> getEmployeeById(Integer itemId){
        return repository.getEmployee(itemId);
    }

}