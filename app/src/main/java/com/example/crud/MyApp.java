package com.example.crud;

import android.app.Application;

import com.example.crud.di.ApplicationComponent;
import com.example.crud.di.ApplicationModule;
import com.example.crud.di.DaggerApplicationComponent;
import com.example.crud.di.RoomModule;

public class MyApp extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .roomModule(new RoomModule(this))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
