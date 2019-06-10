package com.example.crud.viewmodel.employee;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.crud.data.db.entity.Employee;
import com.example.crud.data.db.repository.Repository;


public class NewEmployeeViewModel extends ViewModel {

    private Repository repository;

    public NewEmployeeViewModel(Repository repository) {
        this.repository = repository;
    }

    /**
     * Attach our LiveData to the Database
     */
    public void addNewEmployeeToDatabase(Employee employee){
        new AddItemTask().execute(employee);
    }

    @SuppressLint("StaticFieldLeak")
    private class AddItemTask extends AsyncTask<Employee, Void, Void> {

        @Override
        protected Void doInBackground(Employee... item) {
            repository.insertEmployee(item[0]);
            return null;
        }
    }
}