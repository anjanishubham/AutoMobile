package com.lovelycoding.automobile.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lovelycoding.automobile.R;
import com.lovelycoding.automobile.databinding.FragmentDashboardBinding;
import com.lovelycoding.automobile.datamodel.Product;
import com.lovelycoding.automobile.ui.dashboard.adapter.DashboardItemAdapter;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private RecyclerView mRecyclerView;
    private FragmentDashboardBinding mDashboardBinding;
    private DashboardItemAdapter mAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        mDashboardBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_dashboard, container, false);
       // mAdapter=new DashboardItemAdapter();
        mRecyclerView=mDashboardBinding.dashboardRv;
        return mDashboardBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dashboardViewModel.setView(mRecyclerView);
        dashboardViewModel.getProductList();


    }

}