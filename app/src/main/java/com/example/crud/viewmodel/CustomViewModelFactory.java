package com.example.crud.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.crud.data.db.repository.Repository;
import com.example.crud.viewmodel.company.ListCompanyCollectionViewModel;
import com.example.crud.viewmodel.company.ListCompanyViewModel;
import com.example.crud.viewmodel.company.NewCompanyViewModel;
import com.example.crud.viewmodel.employee.ListEmployeeCollectionViewModel;
import com.example.crud.viewmodel.employee.ListEmployeeViewModel;
import com.example.crud.viewmodel.employee.NewEmployeeViewModel;


import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CustomViewModelFactory implements ViewModelProvider.Factory {
    private final Repository repository;

    @Inject
    public CustomViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListEmployeeCollectionViewModel.class))
            return (T) new ListEmployeeCollectionViewModel(repository);

        else if (modelClass.isAssignableFrom(ListEmployeeViewModel.class))
            return (T) new ListEmployeeViewModel(repository);

        else if (modelClass.isAssignableFrom(NewEmployeeViewModel.class))
            return (T) new NewEmployeeViewModel(repository);

        if (modelClass.isAssignableFrom(ListCompanyCollectionViewModel.class))
            return (T) new ListCompanyCollectionViewModel(repository);

        else if (modelClass.isAssignableFrom(ListCompanyViewModel.class))
            return (T) new ListCompanyViewModel(repository);

        else if (modelClass.isAssignableFrom(NewCompanyViewModel.class))
            return (T) new NewCompanyViewModel(repository);

        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}