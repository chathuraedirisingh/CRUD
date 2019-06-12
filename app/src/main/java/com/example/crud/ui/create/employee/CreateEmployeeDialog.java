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

import java.util.ArrayList;
import java.util.Arrays;
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
        companySpinner = view.findViewById(R.id.spinner);

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


        final ArrayAdapter<Company> adapter =
                new ArrayAdapter<Company>(getContext(), android.R.layout.simple_spinner_dropdown_item, coms);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        companySpinner.setAdapter(adapter);

        companySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        Company company = (Company) parent.getSelectedItem();
                        System.out.println("Spinner1"+company.getComId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });
        return builder.create();
    }

    public class SpinAdapter extends ArrayAdapter<Company> {
        public SpinAdapter(Activity context, ArrayList<Company> comp) {
           super(context, 0, comp);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View listItemView = convertView;
            if(listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.listitems_layout, parent, false);
            }
            Company company = getItem(position);
            TextView nameTextView = (TextView) listItemView.findViewById(R.id.name);
            nameTextView.setText(company.getName());
            return listItemView;
        }

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

    public class SpinnerAdapter extends BaseAdapter {

        Context context;
        ArrayList<Company> conv;
        private LayoutInflater inflater = null;

        public SpinnerAdapter(Context context, List<Company> conv) {
            // TODO Auto-generated constructor stub
            this.context = context;
//            this.conv = conv;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return conv.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return conv.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Company company=conv.get(position);
            View vi = convertView;
            if (vi == null)
                vi = inflater.inflate(R.layout.listitems_layout, null);
//            TextView id = (TextView) vi.findViewById(R.id.idx);
            TextView name = (TextView) vi.findViewById(R.id.name);
//            id.setText((int) company.getComId());
            name.setText(company.getName());
            return vi;
        }
    }

}
