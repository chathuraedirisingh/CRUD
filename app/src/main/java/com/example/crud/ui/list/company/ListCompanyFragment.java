package com.example.crud.ui.list.company;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crud.MainActivity;
import com.example.crud.MyApp;
import com.example.crud.R;
import com.example.crud.data.db.entity.Company;
import com.example.crud.data.db.entity.Employee;
import com.example.crud.ui.create.company.CreateCompanyDialog;
import com.example.crud.ui.update.company.UpdateCompanyFragment;
import com.example.crud.viewmodel.company.ListCompanyCollectionViewModel;
import com.example.crud.viewmodel.employee.ListEmployeeCollectionViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListCompanyFragment extends Fragment {

    private List<Company> companies;

    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ListCompanyCollectionViewModel listCompanyCollectionViewModel;

    public static ListCompanyFragment newInstance() {return new ListCompanyFragment(); }


    public ListCompanyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyApp) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("List Companies");

        //Set up and subscribe (observe) to the ViewModel
        listCompanyCollectionViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ListCompanyCollectionViewModel.class);

        listCompanyCollectionViewModel.getCompanies().observe(this, new Observer<List<Company>>() {
            @Override
            public void onChanged(@Nullable List<Company> listItems) {
                if (companies == null) {
                    setListData(listItems);
                }
                System.out.println("Companies "+companies.toString());
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_list_company, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.emp_list_frag_rec);
        layoutInflater = getActivity().getLayoutInflater();

        FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.fab_create_company);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateCompanyDialog myDialog = new CreateCompanyDialog();
                myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        System.out.println("NOTIFY");
                        adapter.notifyDataSetChanged();

                        ListCompanyFragment fg = new ListCompanyFragment();
                        getFragmentManager()  // or getSupportFragmentManager() if your fragment is part of support library
                                .beginTransaction()
                                .replace(R.id.content_frame, fg)
                                .commit();
                    }
                });
                myDialog.show(getActivity().getSupportFragmentManager(),"MyDP");
            }
        });


        return v;
    }

    public void setListData(List<Company> listOfData) {
        this.companies = listOfData;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomAdapter(companies);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

        private List<Company> companies;

        private Context mContext;
        public CustomAdapter(List<Company> companies) {
            this.mContext = getContext();
            this.companies = companies;
        }

        @Override
        public CustomAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = layoutInflater.inflate(R.layout.item_company, parent, false);
            return new CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CustomAdapter.CustomViewHolder holder, int position) {
            //11. and now the ViewHolder data
            Company company = companies.get(position);

            holder.name.setText(company.getName());
            holder.email.setText(company.getEmail());

            holder.logo.setImageURI(Uri.parse(company.getLogo()));
//            holder.logo.set
            holder.web.setText(company.getUrl());

        }

        @Override
        public int getItemCount() {
            // 12. Returning 0 here will tell our Adapter not to make any Items. Let's fix that.
            return companies.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView name;
            private TextView email;
            private TextView web;
            private ImageView logo;
            private ViewGroup container;
            private Button update;


            public CustomViewHolder(View itemView) {
                super(itemView);
                this.name = (TextView) itemView.findViewById(R.id.companyName);
                this.email = (TextView) itemView.findViewById(R.id.email);
                this.web = (TextView) itemView.findViewById(R.id.url);
                this.logo = (ImageView) itemView.findViewById(R.id.company_logo);

                this.container = (ViewGroup) itemView.findViewById(R.id.root_list_item);
                this.update = (Button)itemView.findViewById(R.id.update);
                this.container.setOnClickListener(this);
                this.update.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                //getAdapterPosition() get's an Integer based on which the position of the current
                //ViewHolder (this) in the Adapter. This is how we get the correct Data.
                Company listItem = companies.get(
                        this.getAdapterPosition()
                );

                UpdateCompanyFragment dialogFragment = new UpdateCompanyFragment();
                Bundle bundle = new Bundle();
                bundle.putString("ID", String.valueOf(listItem.getComId()));
                dialogFragment.setArguments(bundle);
                dialogFragment.show((getActivity().getSupportFragmentManager()),"Edit Dialog");

            }
        }

    }

    private ItemTouchHelper.Callback createHelperCallback() {
        return new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //not used, as the first parameter above is 0
            @Override
            public boolean onMove(RecyclerView recyclerView1, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                listCompanyCollectionViewModel.deleteCompanyItem(
                        companies.get(position)
                );

                companies.remove(position);
                adapter.notifyItemRemoved(position);
            }
        };
    }
}
