package com.example.crud.di;

import android.app.Application;

import com.example.crud.ui.create.company.CreateCompanyDialog;
import com.example.crud.ui.create.employee.CreateEmployeeDialog;
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
    void inject(CreateEmployeeDialog createEmployeeDialog);
    void inject(UpdateEmployeeFragment detailEmpFragment);

    void inject(ListCompanyFragment listComFragment);
    void inject(CreateCompanyDialog createCompanyDialog);
    void inject(UpdateCompanyFragment detailComFragment);

    Application application();

}