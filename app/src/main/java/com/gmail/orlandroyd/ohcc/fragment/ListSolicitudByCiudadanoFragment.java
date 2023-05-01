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

import com.gmail.orlandroyd.ohcc.R;
import com.gmail.orlandroyd.ohcc.activity.DetailsSolicitudByCiudadanoActivity;
import com.gmail.orlandroyd.ohcc.activity.GestionarSolicitudByCiudadanoActivity;
import com.gmail.orlandroyd.ohcc.adapters.SolicitudByCiudadanoRecyclerViewAdapter;
import com.gmail.orlandroyd.ohcc.networking.api.generator.RetrofitClient;
import com.gmail.orlandroyd.ohcc.networking.api.model.Solicitud;
import com.gmail.orlandroyd.ohcc.networking.api.response.SolicitudGetAllResponse;
import com.gmail.orlandroyd.ohcc.preferences.AppPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import androidx.annotation.Nullable;
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
public class ListSolicitudByCiudadanoFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private ProgressDialog pDialog;
    private List<Solicitud> items;
    private FloatingActionButton fabCrear;
    private SolicitudByCiudadanoRecyclerViewAdapter adapter;
    private AppCompatTextView tvInfo;

    public static final int ADD_SOLICITUD_REQUEST = 1;
    public static final int UPDATE_SOLICITUD_REQUEST = 2;
    public static final int DETALLES_SOLICITUD_REQUEST = 3;

    private OnFragmentInteractionListener mListener;

    public ListSolicitudByCiudadanoFragment() {
        // Required empty public constructor
    }

    public static ListSolicitudByCiudadanoFragment newInstance(String param1, String param2) {
        ListSolicitudByCiudadanoFragment fragment = new ListSolicitudByCiudadanoFragment();
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
        rootView = inflater.inflate(R.layout.fragment_list_solicitud_ciudadadano, container, false);

        tvInfo = rootView.findViewById(R.id.tvInfo);

        initDialog();
        fabCrear = rootView.findViewById(R.id.fabAdd_solicitud);
        setUpRecyclerView();

        fabCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GestionarSolicitudByCiudadanoActivity.class);
                startActivityForResult(intent, ADD_SOLICITUD_REQUEST);
            }
        });

        displayData();

        return rootView;
    }

    private void displayData() {
        showDialog();

        int tipo_solicitud = AppPreferences.getFragmetPos(getContext());
        int idUsuario = AppPreferences.getIdUsuario(getContext());

        Call<SolicitudGetAllResponse> call = RetrofitClient.getInstance().getApi().getSolicitudByIdAndTipo(idUsuario, tipo_solicitud);
        call.enqueue(new Callback<SolicitudGetAllResponse>() {
            @Override
            public void onResponse(Call<SolicitudGetAllResponse> call, Response<SolicitudGetAllResponse> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        recyclerView.setVisibility(View.VISIBLE);
                        tvInfo.setVisibility(View.GONE);
                        items = response.body().getData();
                        if (items != null) {
                            if (items.size() != 0) {
                                adapter = new SolicitudByCiudadanoRecyclerViewAdapter(items, getContext());
                                adapter.setOnItemClickListener(new SolicitudByCiudadanoRecyclerViewAdapter.OnItemClicklistener() {
                                    @Override
                                    public void OnItemClick(Solicitud solicitud) {

                                        switch (solicitud.getId_estado()) {
                                            case 1: // Pendiente -> EDITAR
                                                Intent intent = new Intent(getContext(), GestionarSolicitudByCiudadanoActivity.class);
                                                intent.putExtra(GestionarSolicitudByCiudadanoActivity.EXTRA_ID, solicitud.getId_solicitud());
                                                intent.putExtra(GestionarSolicitudByCiudadanoActivity.EXTRA_DESCRIPCION, solicitud.getDescripcion());
                                                startActivityForResult(intent, UPDATE_SOLICITUD_REQUEST);
                                                break;
                                            case 2: // En progreso
                                                FancyToast.makeText(getContext(), "No es posible editar...", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                                                break;
                                            case 3: // Finalizado -> DETALLES
                                                Intent intentDetalles = new Intent(getContext(), DetailsSolicitudByCiudadanoActivity.class);
                                                intentDetalles.putExtra(DetailsSolicitudByCiudadanoActivity.EXTRA_ID, solicitud.getId_solicitud());
                                                intentDetalles.putExtra(DetailsSolicitudByCiudadanoActivity.EXTRA_EVALUACION, solicitud.getEvaluacion());
                                                intentDetalles.putExtra(DetailsSolicitudByCiudadanoActivity.EXTRA_RESPUESTA, solicitud.getRespuesta());
                                                intentDetalles.putExtra(DetailsSolicitudByCiudadanoActivity.EXTRA_FECHA_CREADA, solicitud.getFecha_creada());
                                                intentDetalles.putExtra(DetailsSolicitudByCiudadanoActivity.EXTRA_FECHA_INICIADA, solicitud.getFecha_iniciada());
                                                intentDetalles.putExtra(DetailsSolicitudByCiudadanoActivity.EXTRA_FECHA_RESPUESTA, solicitud.getFecha_respuesta());
                                                intentDetalles.putExtra(DetailsSolicitudByCiudadanoActivity.EXTRA_DESCIPCION, solicitud.getDescripcion());
                                                intentDetalles.putExtra(DetailsSolicitudByCiudadanoActivity.EXTRA_ID_USUARIO, solicitud.getId_usuario());
                                                intentDetalles.putExtra(DetailsSolicitudByCiudadanoActivity.EXTRA_ID_ESPECIALISTA, solicitud.getId_especialista());
                                                intentDetalles.putExtra(DetailsSolicitudByCiudadanoActivity.EXTRA_ID_TIPO_SOLICITUD, solicitud.getId_tipo_solicitud());
                                                intentDetalles.putExtra(DetailsSolicitudByCiudadanoActivity.EXTRA_ID_ESTADO, solicitud.getId_estado());
                                                startActivityForResult(intentDetalles, DETALLES_SOLICITUD_REQUEST);
                                                break;
                                            case 4: // Cancelado
                                                FancyToast.makeText(getContext(), "No es posible editar...", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                                                break;
                                        }
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
                        recyclerView.setVisibility(View.GONE);
                        tvInfo.setVisibility(View.VISIBLE);
                    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tvInfo.setVisibility(View.VISIBLE);
                    FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                }
            }

            @Override
            public void onFailure(Call<SolicitudGetAllResponse> call, Throwable t) {
                Log.e("ListSolicitudCiu", "onFailure: ", t);
                FancyToast.makeText(getContext(), getString(R.string.code_4221), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                hideDialog();
                recyclerView.setVisibility(View.GONE);
                tvInfo.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_SOLICITUD_REQUEST && resultCode == RESULT_OK) {
            displayData();
        } else if (requestCode == UPDATE_SOLICITUD_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(GestionarSolicitudByCiudadanoActivity.EXTRA_ID, -1);
            if (id == -1) {
                FancyToast.makeText(getContext(), "La Solicitud no se pudo editar", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                return;
            }
            displayData();
        } else if (requestCode == DETALLES_SOLICITUD_REQUEST && resultCode == RESULT_OK) {
            displayData();
            FancyToast.makeText(getContext(), "Evaluado con éxito", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
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

        // to hide fab
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fabCrear.getVisibility() == View.VISIBLE) {
                    fabCrear.hide();
                } else if (dy < 0 && fabCrear.getVisibility() != View.VISIBLE) {
                    fabCrear.show();
                }
            }
        });
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
            displayData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
