package com.example.crud.dependancyinjection;

import android.app.Application;

import com.example.crud.ui.create.company.CreateCompanyFragment;
import com.example.crud.ui.create.employee.CreateEmployeeFragment;
import com.example.crud.ui.list.company.ListCompanyFragment;
import com.example.crud.ui.list.employee.ListEmployeeFragment;
import com.example.crud.ui.update.company.UpdateCompanyFragment;
import com.example.crud.ui.update.employee.UpdateEmployeeFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class})
public interface ApplicationComponent {

    void inject(ListEmployeeFragment listEmpFragment);
    void inject(CreateEmployeeFragment createEmpFragment);
    void inject(UpdateEmployeeFragment detailEmpFragment);

    void inject(ListCompanyFragment listComFragment);
    void inject(CreateCompanyFragment createComFragment);
    void inject(UpdateCompanyFragment detailComFragment);

    Application application();

}