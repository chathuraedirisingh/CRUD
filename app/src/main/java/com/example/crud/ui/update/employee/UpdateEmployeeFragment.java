package com.example.crud.ui.update.employee;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crud.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateEmployeeFragment extends Fragment {


    public UpdateEmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_employee, container, false);
    }

}
