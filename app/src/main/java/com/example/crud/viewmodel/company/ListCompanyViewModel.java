package com.example.crud.viewmodel.company;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.crud.data.db.entity.Company;
import com.example.crud.data.db.repository.Repository;

public class ListCompanyViewModel extends ViewModel {

    private Repository repository;

    public ListCompanyViewModel(Repository repository) {
        this.repository = repository;
    }

    public Company getCompanyItemById(int itemId){
        return repository.getCompany(itemId);
    }

    @SuppressLint("StaticFieldLeak")
    private class AddItemTask extends AsyncTask<Company, Void, Void> {

        @Override
        protected Void doInBackground(Company... item) {
            repository.insertCompany(item[0]);
            return null;
        }
    }

}