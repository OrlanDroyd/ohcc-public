package com.gmail.orlandroyd.ohcc.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.activity.AuthenticationActivity;
import com.gmail.orlandroyd.ohcc.glide.adapter.GalleryAdapter;
import com.gmail.orlandroyd.ohcc.glide.model.Image;
import com.gmail.orlandroyd.ohcc.glide.ui.SlideshowDialogFragment;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by OrlanDroyd on 2/3/2019.
 */
public class GalleryFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private ArrayList<Image> images;
    private GalleryAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
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
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        init();
        return rootView;
    }

    private void init() {
        recyclerView = rootView.findViewById(R.id.recycler_view_glide);

        images = new ArrayList<>();
        getData();
        mAdapter = new GalleryAdapter(getActivity().getApplicationContext(), images);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private ArrayList<Image> getData() {
        String[] title = getResources().getStringArray(R.array.image_title_grados_proteccion);
        String[] grado = getResources().getStringArray(R.array.image_grados_proteccion);
        images.clear();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids_grados_proteccion);
        for (int i = 0; i < imgs.length(); i++) {
            images.add(new Image(title[i], grado[i], imgs.getResourceId(i, -1)));
        }
        return images;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_login_main) {
            startActivity(new Intent(getContext(), AuthenticationActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
