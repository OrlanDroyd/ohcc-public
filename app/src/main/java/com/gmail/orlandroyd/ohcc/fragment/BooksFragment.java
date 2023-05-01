package com.gmail.orlandroyd.ohcc.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.adapters.IndexRecyclerViewAdapter;
import com.gmail.orlandroyd.ohcc.adapters.adapter_models.IndexItem;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created by OrlanDroyd on 2/3/2019.
 */
public class BooksFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private IndexRecyclerViewAdapter adapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public BooksFragment() {
        // Required empty public constructor
    }

    public static BooksFragment newInstance() {
        BooksFragment fragment = new BooksFragment();
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
        rootView = inflater.inflate(R.layout.fragment_books, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_view_book);
        recyclerView.setHasFixedSize(true);
        adapter = new IndexRecyclerViewAdapter(IndexItem.getList(), rootView.getContext());
        recyclerView.setAdapter(adapter);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }

}
