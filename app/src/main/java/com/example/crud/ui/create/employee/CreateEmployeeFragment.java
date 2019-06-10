package com.example.crud.ui.create.employee;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.crud.MyApp;
import com.example.crud.R;
import com.example.crud.data.db.entity.Employee;
import com.example.crud.ui.list.employee.ListEmployeeFragment;
import com.example.crud.viewmodel.employee.NewEmployeeViewModel;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEmployeeFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private NewEmployeeViewModel newEmployeeViewModel;

    private ImageView back;
    public CreateEmployeeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        ((MyApp) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Set up and subscribe (observe) to the ViewModel
        newEmployeeViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewEmployeeViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_employee, container, false);

        FloatingActionButton fabulous = (FloatingActionButton) v.findViewById(R.id.fab_create_new_item);
        FloatingActionButton goback = (FloatingActionButton) v.findViewById(R.id.fab_back);

        fabulous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Employee listItem = new Employee("Ashan","Chalith","ihbd","vdyiviad","1");
//                Employee listItem2 = new Employee("Chathura","Edirisinghe","ihbd","vdyiviad","1");
//                newEmployeeViewModel.addNewEmployeeToDatabase(listItem2);
//                newEmployeeViewModel.addNewEmployeeToDatabase(listItem);
                startListActivity();
            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListActivity();
            }
        });
        return v;
    }
    private void startListActivity() {
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.content_frame, new ListEmployeeFragment()).commit();
    }

}
