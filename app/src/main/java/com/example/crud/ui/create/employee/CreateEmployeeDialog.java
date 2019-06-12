package com.example.crud.ui.create.employee;


import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.crud.MyApp;
import com.example.crud.R;
import com.example.crud.data.db.entity.Company;
import com.example.crud.viewmodel.company.ListCompanyCollectionViewModel;
import com.example.crud.viewmodel.employee.NewEmployeeViewModel;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.angmarch.views.SpinnerTextFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEmployeeDialog extends DialogFragment {

    private EditText first_name;
    private EditText last_name;
    private EditText email;
    private EditText phone;
    private Spinner companySpinner;

//    private SpinAdapter adapter;

    private List<Company> companies;
    ArrayList<Company> coms = new ArrayList<>();

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private NewEmployeeViewModel newEmployeeViewModel;

    private ListCompanyCollectionViewModel listCompanyCollectionViewModel;

    public DialogInterface.OnDismissListener onDismissListener;

    public CreateEmployeeDialog() {
        // Required empty public constructor
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((MyApp) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar();
        getActivity().setTitle("Create Employee");

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_create_employee, null);

        first_name = view.findViewById(R.id.firstname);
        last_name = view.findViewById(R.id.lastname);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        final NiceSpinner niceSpinner = (NiceSpinner)view.findViewById(R.id.nice_spinner);

        newEmployeeViewModel = ViewModelProviders.of(this, viewModelFactory).get(NewEmployeeViewModel.class);

        listCompanyCollectionViewModel = ViewModelProviders.of(this,viewModelFactory).get(ListCompanyCollectionViewModel.class);

        listCompanyCollectionViewModel.getCompanies().observe(this, new Observer<List<Company>>() {
            @Override
            public void onChanged(@Nullable List<Company> data) {
                if (companies == null) {
                    setListData(data);
                }
                for (Company elem_ : companies) {
                    coms.add(new Company(elem_.getComId(), elem_.getName()));
                }


            }
        });

        builder.setView(view)
                .setTitle("Add New Employee")
                .setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        String username = editTextUsername.getText().toString();
//                        String password = editTextPassword.getText().toString();
//                        listener.applyTexts(username, password);
                    }
                });


//        final ArrayAdapter<Company> adapter =
//                new ArrayAdapter<Company>(getContext(), android.R.layout.simple_spinner_dropdown_item, coms);

        List<Company> dataset = new ArrayList<>();
        dataset=coms;

        SpinnerTextFormatter textFormatter = new SpinnerTextFormatter<Company>() {
            @Override
            public Spannable format(Company company) {
                return new SpannableString(company.getComId() + " " + company.getName());
            }
        };
        niceSpinner.setSpinnerTextFormatter(textFormatter);
        niceSpinner.setSelectedTextFormatter(textFormatter);

        niceSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                Company company = (Company) niceSpinner.getSelectedItem();
                System.out.println("Spinner1"+company.getComId());
            }
        });
        niceSpinner.attachDataSource(dataset);

        return builder.create();
    }
    private void setListData(List<Company> data) {
        this.companies=data;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }


}
