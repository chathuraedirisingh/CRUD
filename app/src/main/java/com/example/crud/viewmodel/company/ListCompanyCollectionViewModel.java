package com.example.crud.viewmodel.company;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.crud.data.db.entity.Company;
import com.example.crud.data.db.repository.Repository;

import java.util.List;


public class ListCompanyCollectionViewModel extends ViewModel {

    private Repository repository;

    public ListCompanyCollectionViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Company>> getCompanies() {
        return repository.getCompanies();
    }

    public void deleteCompanyItem(Company company) {
        DeleteItemTask deleteItemTask = new DeleteItemTask();
        deleteItemTask.execute(company);
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteItemTask extends AsyncTask<Company, Void, Void> {

        @Override
        protected Void doInBackground(Company... item) {
            repository.deleteCompany(item[0]);
            return null;
        }
    }

}
