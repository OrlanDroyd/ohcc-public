package com.gmail.orlandroyd.ohcc.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.adapters.LibraryRecyclerViewAdapter;
import com.gmail.orlandroyd.ohcc.adapters.adapter_models.LibraryItem;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created by OrlanDroyd on 10/4/2019.
 */
public class LibraryFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private LibraryRecyclerViewAdapter adapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public LibraryFragment() {
        // Required empty public constructor
    }

    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_library, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_view_library);
        recyclerView.setHasFixedSize(true);
        adapter = new LibraryRecyclerViewAdapter(LibraryItem.getList(), rootView.getContext());
        recyclerView.setAdapter(adapter);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }

}
