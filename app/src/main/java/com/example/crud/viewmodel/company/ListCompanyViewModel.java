package com.example.crud.viewmodel.company;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.crud.data.db.entity.Company;
import com.example.crud.data.db.repository.Repository;

public class ListCompanyViewModel extends ViewModel {

    private Repository repository;

    public ListCompanyViewModel(Repository repository) {
        this.repository = repository;
    }

    public Company getCompanyItemById(long itemId){
        return repository.getCompany(itemId);
    }

}