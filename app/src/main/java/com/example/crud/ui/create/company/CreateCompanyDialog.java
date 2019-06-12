package com.example.crud.ui.create.company;


import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.crud.MyApp;
import com.example.crud.R;
import com.example.crud.data.db.entity.Company;
import com.example.crud.viewmodel.company.NewCompanyViewModel;
import com.mvc.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCompanyDialog extends DialogFragment {

    public static final int GALLERY_ONLY_REQ = 1212;

    private ImageView clogo;
    private EditText cname;
    private EditText cemail;
    private EditText cweb;
    String pathFromGallery;

    public DialogInterface.OnDismissListener onDismissListener;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    NewCompanyViewModel newCompanyViewModel;

    public CreateCompanyDialog() {
        // Required empty public constructor
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
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

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_create_company, null);

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
                        newCompanyViewModel.addNewCompanyToDatabase(company);

                    }
                });

        clogo = view.findViewById(R.id.image_view_1);
        cname = view.findViewById(R.id.company_name);
        cemail = view.findViewById(R.id.email);
        cweb = view.findViewById(R.id.web);

        clogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.pickImageGalleryOnly(CreateCompanyDialog.this, GALLERY_ONLY_REQ);
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
//            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case GALLERY_ONLY_REQ:
                pathFromGallery = ImagePicker.getImagePathFromResult(getActivity(),requestCode,resultCode,data);
                Picasso.with(getContext()).load(pathFromGallery).into(clogo);
                break;
            default:
                Bitmap bitmap = ImagePicker.getImageFromResult(getActivity(), requestCode, resultCode, data);
                if (bitmap != null) {
                    clogo.setImageBitmap(bitmap);
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }
}
