package com.example.crud.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;

import com.example.crud.data.db.AppDB;
import com.example.crud.data.db.entity.CompanyDao;
import com.example.crud.data.db.entity.EmployeeDao;
import com.example.crud.data.db.repository.Repository;
import com.example.crud.viewmodel.CustomViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private final AppDB database;

    public RoomModule(Application application) {
        this.database = Room.databaseBuilder(
                application,
                AppDB.class,
                "myApp.db"
        ).build();
    }

    @Provides
    @Singleton
    Repository provideRepository(EmployeeDao employeeDao, CompanyDao companyDao){
        return new Repository(employeeDao, companyDao);
    }


    @Provides
    @Singleton
    EmployeeDao provideEmployeeDao(AppDB database){
        return database.employeeDao();
    }

    @Provides
    @Singleton
    CompanyDao provideCompanyDao(AppDB database){
        return database.companyDao();
    }

    @Provides
    @Singleton
    AppDB provideDtabase(Application application){
        return database;
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(Repository repository){
        return new CustomViewModelFactory(repository);
    }
}