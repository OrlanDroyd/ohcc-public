package com.gmail.orlandroyd.ohcc.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.adapters.GlossaryRecyclerViewAdapter;
import com.gmail.orlandroyd.ohcc.adapters.adapter_models.GlossaryItem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by OrlanDroyd on 2/3/2019.
 */
public class GlossaryFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private View rootView;
    private RecyclerView recyclerView;
    private GlossaryRecyclerViewAdapter adapter;


    public GlossaryFragment() {
        // Required empty public constructor
    }

    public static GlossaryFragment newInstance(String param1, String param2) {
        GlossaryFragment fragment = new GlossaryFragment();
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
        rootView = inflater.inflate(R.layout.app_bar_glossary, container, false);

        setUpRecyclerView();

        return rootView;
    }

    private void setUpRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_view_glossary);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        // get glossary.csv
        List<GlossaryItem> glossaryItems = getDataFromAsset();
        adapter = new GlossaryRecyclerViewAdapter(glossaryItems, getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private List<GlossaryItem> getDataFromAsset() {
        ArrayList<GlossaryItem> glossaryItems = new ArrayList<>();
        try {
            AssetManager manager = getContext().getAssets();
            InputStream in = manager.open("glossary.csv");

            ArrayList<GlossaryItem> items = parse(in);

            for (GlossaryItem item : items) {
                glossaryItems.add(new GlossaryItem(item.getTitle(), item.getContent()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return glossaryItems;
    }

    /* Simple CSV Parser */
    private static final int COL_TITLE = 0;
    private static final int COL_CONTENT = 1;
    private static String[] tokens;

    private ArrayList<GlossaryItem> parse(InputStream in) throws IOException {
        ArrayList<GlossaryItem> results = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String nextLine;
        int cont = 1;
        while ((nextLine = reader.readLine()) != null) {
            tokens = nextLine.split(";");
            if (tokens.length != 2) {
                Toast.makeText(getContext(), "CSV error " + cont, Toast.LENGTH_SHORT).show();
                cont++;
                continue;
            } else {
                GlossaryItem current = new GlossaryItem(tokens[COL_TITLE], tokens[COL_CONTENT]);
                results.add(current);
            }
        }

        in.close();
        return results;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_glossary, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

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

}
