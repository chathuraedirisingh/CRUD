package com.example.crud.data.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class Company {

    @PrimaryKey(autoGenerate = true)
    private long comId=0;

    private String name;

    private String email;

    private String logo;

    private String url;

    public Company(String name, String email, String logo, String url) {
        this.name = name;
        this.email = email;
        this.logo = logo;
        this.url = url;
    }

    public Company(long id,String name) {
        this.comId = id;
        this.name = name;
    }

    public long getComId() {
        return comId;
    }

    public void setComId(long comId) {
        this.comId = comId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return this.name.toString();
    }
}
