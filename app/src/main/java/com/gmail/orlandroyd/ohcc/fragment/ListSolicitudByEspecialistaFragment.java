package com.gmail.orlandroyd.ohcc.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.activity.GestionarSolicitudByCiudadanoActivity;
import com.gmail.orlandroyd.ohcc.adapters.SolicitudByEspecialistaRecyclerViewAdapter;
import com.gmail.orlandroyd.ohcc.networking.api.generator.RetrofitClient;
import com.gmail.orlandroyd.ohcc.networking.api.model.Solicitud;
import com.gmail.orlandroyd.ohcc.networking.api.response.SolicitudGetAllResponse;
import com.gmail.orlandroyd.ohcc.preferences.AppPreferences;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by OrlanDroyd on 11/5/2019.
 */
public class ListSolicitudByEspecialistaFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private ProgressDialog pDialog;
    private List<Solicitud> items;
    private SolicitudByEspecialistaRecyclerViewAdapter adapter;
    private AppCompatSpinner spinner;
    private AppCompatTextView tvInfo;
    private int especialistaId;
    private int tipoSolicitud;

    public static final int ADD_SOLICITUD_REQUEST = 1;
    public static final int UPDATE_SOLICITUD_REQUEST = 2;

    private OnFragmentInteractionListener mListener;

    public ListSolicitudByEspecialistaFragment() {
        // Required empty public constructor
    }

    public static ListSolicitudByEspecialistaFragment newInstance(String param1, String param2) {
        ListSolicitudByEspecialistaFragment fragment = new ListSolicitudByEspecialistaFragment();
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
        rootView = inflater.inflate(R.layout.fragment_list_solicitud_especialista, container, false);

        especialistaId = AppPreferences.getIdUsuario(getContext());
        tipoSolicitud = AppPreferences.getFragmetPos(getContext());

        initDialog();
        spinner = rootView.findViewById(R.id.spinner_solicitud);
        tvInfo = rootView.findViewById(R.id.tvInfo_list_sol_esp);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.solicitud_spinner, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        setUpRecyclerView();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                displayData(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        return rootView;
    }

    private void displayData(int id) {
        showDialog();

        Call<SolicitudGetAllResponse> call = RetrofitClient.getInstance().getApi().getSolicitudByEspecialista(id, tipoSolicitud, especialistaId);
        call.enqueue(new Callback<SolicitudGetAllResponse>() {
            @Override
            public void onResponse(Call<SolicitudGetAllResponse> call, Response<SolicitudGetAllResponse> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        items = response.body().getData();
                        if (items != null) {
                            if (items.size() != 0) {
                                recyclerView.setVisibility(View.VISIBLE);
                                tvInfo.setVisibility(View.GONE);
                                adapter = new SolicitudByEspecialistaRecyclerViewAdapter(items, getContext());
                                adapter.setOnItemClickListener(new SolicitudByEspecialistaRecyclerViewAdapter.OnItemClicklistener() {
                                    @Override
                                    public void OnItemClick(Solicitud solicitud) {
                                        FancyToast.makeText(getContext(), "No implementado aún...", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                                    }
                                });
                                recyclerView.setAdapter(adapter);
                            } else {
                                recyclerView.setVisibility(View.GONE);
                                tvInfo.setVisibility(View.VISIBLE);
                            }
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            tvInfo.setVisibility(View.VISIBLE);
                        }
                    } else {
                        tvInfo.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    tvInfo.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                }
            }

            @Override
            public void onFailure(Call<SolicitudGetAllResponse> call, Throwable t) {
                Log.e("ListSolicitudEsp", "onFailure: ", t);
                FancyToast.makeText(getContext(), getString(R.string.code_422), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                hideDialog();
                recyclerView.setVisibility(View.GONE);
                tvInfo.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int itemId = spinner.getSelectedItemPosition() + 1;

        if (requestCode == ADD_SOLICITUD_REQUEST && resultCode == RESULT_OK) {
            displayData(itemId);
        } else if (requestCode == UPDATE_SOLICITUD_REQUEST && resultCode == RESULT_OK) {
            displayData(itemId);
            int id = data.getIntExtra(GestionarSolicitudByCiudadanoActivity.EXTRA_ID, -1);
            if (id == -1) {
                FancyToast.makeText(getContext(), "La Solicitud no se pudo editar", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                return;
            }
        } else {
            // not save
            return;
        }
    }

    private void setUpRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_view_tramites);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

    }

    private void initDialog() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage(getString(R.string.loading));
        pDialog.setCancelable(false);
    }

    private void showDialog() {
        if (!pDialog.isShowing()) pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing()) pDialog.dismiss();
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
        inflater.inflate(R.menu.menu_list_update, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_update_main) {
            int itemId = spinner.getSelectedItemPosition() + 1;
            displayData(itemId);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
