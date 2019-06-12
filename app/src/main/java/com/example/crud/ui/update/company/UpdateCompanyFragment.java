package com.example.crud.ui.update.company;


import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.crud.MyApp;
import com.example.crud.R;
import com.example.crud.data.db.entity.Company;
import com.example.crud.ui.create.company.CreateCompanyDialog;
import com.example.crud.viewmodel.company.ListCompanyViewModel;
import com.example.crud.viewmodel.company.NewCompanyViewModel;
import com.mvc.imagepicker.ImagePicker;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateCompanyFragment extends DialogFragment {

    public static final int GALLERY_ONLY_REQ = 1212;

    private ImageView clogo;
    private EditText cname;
    private EditText cemail;
    private EditText cweb;
    String pathFromGallery;

    private Company company;

    public DialogInterface.OnDismissListener onDismissListener;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    NewCompanyViewModel newCompanyViewModel;
    ListCompanyViewModel listCompanyViewModel;
    public UpdateCompanyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyApp) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        ImagePicker.setMinQuality(100, 100);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        newCompanyViewModel = ViewModelProviders.of(this, viewModelFactory).get(NewCompanyViewModel.class);
        listCompanyViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListCompanyViewModel.class);

        Bundle bundle = getArguments();
        int id = Integer.parseInt(bundle.getString("ID",""));
        System.out.println("ID  "+id);

        Company comp = listCompanyViewModel.getCompanyItemById(id);
        setData(comp);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_update_company, null);


        builder.setView(view)
                .setTitle("Add New Company")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = cname.getText().toString();
                        String email = cemail.getText().toString();
                        String web = cweb.getText().toString();
                        String path = pathFromGallery;

//                        listener.applyTexts(username, password);

                        Company company = new Company(name,email,path,web);
                        newCompanyViewModel.updateCompany(company);

                    }
                });

        clogo = view.findViewById(R.id.image_view_1);
        cname = view.findViewById(R.id.company_name);
        cemail = view.findViewById(R.id.email);
        cweb = view.findViewById(R.id.web);

        clogo.setImageURI(Uri.parse(company.getLogo()));
        cname.setText(company.getName());
        cemail.setText(company.getEmail());
        cweb.setText(company.getUrl());

        clogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.pickImageGalleryOnly(UpdateCompanyFragment.this, GALLERY_ONLY_REQ);
            }
        });

        return builder.create();
    }

    private void setData(Company com) {
        this.company = com;
    }

}
