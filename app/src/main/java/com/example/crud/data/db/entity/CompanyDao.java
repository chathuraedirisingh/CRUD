package com.example.crud.data.db.entity;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CompanyDao {

    @Query("SELECT * FROM company")
    LiveData<List<Company>> getListCompanyData();

    @Query("SELECT * FROM company WHERE comId =:id LIMIT 1")
    Company getCompanyById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertCompany(Company company);

    @Delete
    void deleteCompany(Company company);

    @Update
    void updateCompany(Company company);

}
