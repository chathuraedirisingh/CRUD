package com.example.crud.ui.details.employee;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crud.R;
import com.example.crud.ui.list.employee.ListEmployeeFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailEmployeeFragment extends Fragment implements ListEmployeeFragment.setId {


    public Integer id;

    public DetailEmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_employee, container, false);
    }

    @Override
    public void upDatedData(Integer id) {
        this.id =  id;
    }
}
