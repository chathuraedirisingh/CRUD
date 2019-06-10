package com.example.crud.viewmodel.employee;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.crud.data.db.entity.Employee;
import com.example.crud.data.db.repository.Repository;

import java.util.List;

public class ListEmployeeCollectionViewModel extends ViewModel {

    private Repository repository;

    public ListEmployeeCollectionViewModel(Repository repository) {
        this.repository = repository;
    }

//    public ListEmployeeCollectionViewModel(@NonNull Application application) {
//        super(application);
//    }




    public LiveData<List<Employee>> getEmployees() {
        return repository.getEmployees();
    }

    public void deleteEmployeeItem(Employee employee) {
        DeleteItemTask deleteItemTask = new DeleteItemTask();
        deleteItemTask.execute(employee);
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteItemTask extends AsyncTask<Employee, Void, Void> {

        @Override
        protected Void doInBackground(Employee... item) {
            repository.deleteEmployee(item[0]);
            return null;
        }
    }

}
