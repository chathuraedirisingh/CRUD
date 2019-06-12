package com.example.crud.di;

import android.app.Application;

import com.example.crud.MyApp;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final MyApp application;
    public ApplicationModule(MyApp application) {
        this.application = application;
    }

    @Provides
    MyApp provideRoomDemoApplication(){
        return application;
    }

    @Provides
    Application provideApplication(){
        return application;
    }
}
