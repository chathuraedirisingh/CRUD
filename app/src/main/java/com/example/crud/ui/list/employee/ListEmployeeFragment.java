package com.example.crud.ui.list.employee;


import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Fade;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.crud.MyApp;
import com.example.crud.R;
import com.example.crud.data.db.entity.Employee;
import com.example.crud.ui.create.employee.CreateEmployeeDialog;
import com.example.crud.ui.details.employee.DetailEmployeeFragment;
import com.example.crud.viewmodel.employee.ListEmployeeCollectionViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListEmployeeFragment extends Fragment {

    private static final String EXTRA_EMP_ID = "EXTRA_EMP_ID";

    private List<Employee> employees;

    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private Toolbar toolbar;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ListEmployeeCollectionViewModel listEmployeeCollectionViewModel;


    public ListEmployeeFragment() {
        // Required empty public constructor
    }

    public static ListEmployeeFragment newInstance() {return new ListEmployeeFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyApp) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("List Employees");

        //Set up and subscribe (observe) to the ViewModel
        listEmployeeCollectionViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ListEmployeeCollectionViewModel.class);

        listEmployeeCollectionViewModel.getEmployees().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(@Nullable List<Employee> listItems) {
                if (employees == null) {
                    setListData(listItems);
                }

                System.out.println("Hell "+employees.toString());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list_employee, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.emp_list_frag_rec);
        layoutInflater = getActivity().getLayoutInflater();

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_create_employee);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateEmployeeDialog myDialog = new CreateEmployeeDialog();
                myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        System.out.println("NOTIFY");
                        adapter.notifyDataSetChanged();

                        ListEmployeeFragment fg = new ListEmployeeFragment();
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

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void startDetailActivity(long itemId, View viewRoot) {
        Activity container = getActivity();
        Intent i = new Intent(container, DetailEmployeeFragment.class);
        i.putExtra(EXTRA_EMP_ID, itemId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            container.getWindow().setEnterTransition(new Fade(Fade.IN));
            container.getWindow().setEnterTransition(new Fade(Fade.OUT));

//            ActivityOptions options = ActivityOptions
//                    .makeSceneTransitionAnimation(container,
//                            new Pair<>(viewRoot.findViewById(R.id.imv_list_item_circle),
//                                    getString(R.string.transition_drawable)),
//                            new Pair<>(viewRoot.findViewById(R.id.lbl_message),
//                                    getString(R.string.transition_message)),
//                            new Pair<>(viewRoot.findViewById(R.id.lbl_date_and_time),
//                                    getString(R.string.transition_time_and_date)));

            startActivity(i);

        }
    }


    public void setListData(List<Employee> listOfData) {
        this.employees = listOfData;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomAdapter(employees);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


//        DividerItemDecoration itemDecoration = new DividerItemDecoration(
//                recyclerView.getContext(),
//                layoutManager.getOrientation()
//        );
//
//        itemDecoration.setDrawable(
//                ContextCompat.getDrawable(
//                        getActivity(),
//                        R.drawable.divider_white
//                )
//        );
//
//        recyclerView.addItemDecoration(
//                itemDecoration
//        );


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

        private List<Employee> employees;

        private Context mContext;
        public CustomAdapter(List<Employee> employees) {
            this.mContext = getContext();
            this.employees = employees;
        }

        @Override
        public CustomAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = layoutInflater.inflate(R.layout.item_employee, parent, false);
            CustomViewHolder customViewHolder = new CustomViewHolder(v);
            return customViewHolder;
        }

        @Override
        public void onBindViewHolder(CustomAdapter.CustomViewHolder holder, int position) {
            //11. and now the ViewHolder data
            Employee employee = employees.get(position);

            holder.firstname.setText(
                    employee.getFirstname()
            );

            holder.lastname.setText(
                    employee.getLastname()
            );
            holder.email.setText(
                    employee.getEmail()
            );
            holder.phone.setText(
                    employee.getPhone()
            );
            holder.company.setText(
                    employee.getCompanyid()
            );

        }

        @Override
        public int getItemCount() {
            // 12. Returning 0 here will tell our Adapter not to make any Items. Let's fix that.
            return employees.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            //            private ImageView image;
            private ProgressBar loading;
            private TextView firstname;
            private TextView lastname;
            private TextView company;
            private TextView email;
            private TextView phone;
            private ViewGroup container;


            public CustomViewHolder(View itemView) {
                super(itemView);
                this.firstname = (TextView) itemView.findViewById(R.id.firstname);
                this.lastname = (TextView) itemView.findViewById(R.id.lastname);
                this.company = (TextView) itemView.findViewById(R.id.company);
                this.email = (TextView) itemView.findViewById(R.id.email);
                this.phone = (TextView) itemView.findViewById(R.id.phone);

                this.loading = (ProgressBar) itemView.findViewById(R.id.pro_item_data);
                this.container = (ViewGroup) itemView.findViewById(R.id.root_list_item);

                this.container.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                //getAdapterPosition() get's an Integer based on which the position of the current
                //ViewHolder (this) in the Adapter. This is how we get the correct Data.
                Employee listItem = employees.get(
                        this.getAdapterPosition()
                );

                startDetailActivity(listItem.getEmpId(), v);

            }
        }

    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //not used, as the first parameter above is 0
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                listEmployeeCollectionViewModel.deleteEmployeeItem(
                        employees.get(position)
                );

                employees.remove(position);
                adapter.notifyItemRemoved(position);
            }
        };
        return simpleItemTouchCallback;
    }

    public interface setId                                       {
        public void upDatedData(Integer data);

    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
