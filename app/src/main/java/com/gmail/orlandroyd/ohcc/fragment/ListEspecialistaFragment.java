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
import com.gmail.orlandroyd.ohcc.activity.GestionarEspecialistaActivity;
import com.gmail.orlandroyd.ohcc.adapters.EspecialistaRecyclerViewAdapter;
import com.gmail.orlandroyd.ohcc.networking.api.generator.RetrofitClient;
import com.gmail.orlandroyd.ohcc.networking.api.model.Usuario;
import com.gmail.orlandroyd.ohcc.networking.api.response.UsuarioResponse;
import com.gmail.orlandroyd.ohcc.util.AESUtils;
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
public class ListEspecialistaFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private ProgressDialog pDialog;
    private List<Usuario> items;
    private FloatingActionButton fabCrear;
    private AppCompatTextView tvInfo;

    private OnFragmentInteractionListener mListener;

    public static final int ADD_ESPECIALISTA_REQUEST = 1;
    public static final int UPDATE_ESPECIALISTA_REQUEST = 2;


    public ListEspecialistaFragment() {
        // Required empty public constructor
    }

    public static ListEspecialistaFragment newInstance(String param1, String param2) {
        ListEspecialistaFragment fragment = new ListEspecialistaFragment();
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
        rootView = inflater.inflate(R.layout.fragment_list_especialista, container, false);

        tvInfo = rootView.findViewById(R.id.tvInfo);

        initDialog();
        fabCrear = rootView.findViewById(R.id.fabAdd_especialista);
        setUpRecyclerView();

        fabCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GestionarEspecialistaActivity.class);
                startActivityForResult(intent, ADD_ESPECIALISTA_REQUEST);
            }
        });

        displayData();

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ESPECIALISTA_REQUEST && resultCode == RESULT_OK) {
            displayData();
        } else if (requestCode == UPDATE_ESPECIALISTA_REQUEST && resultCode == RESULT_OK) {
            displayData();
            int id = data.getIntExtra(GestionarEspecialistaActivity.EXTRA_ID, -1);
            if (id == -1) {
                FancyToast.makeText(getContext(), "El Especialista no se pudo editar", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                return;
            }
        } else {
            // not save
            return;
        }
    }

    private void displayData() {
        showDialog();

        Call<UsuarioResponse> call = RetrofitClient.getInstance().getApi().getEspecialistasUsuario();
        call.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                hideDialog();
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        recyclerView.setVisibility(View.VISIBLE);
                        tvInfo.setVisibility(View.GONE);
                        items = response.body().getData();
                        if (items != null) {
                            EspecialistaRecyclerViewAdapter adapter = new EspecialistaRecyclerViewAdapter(items, getContext());
                            adapter.setOnItemClickListener(new EspecialistaRecyclerViewAdapter.OnItemClicklistener() {
                                @Override
                                public void OnItemClick(Usuario usuario) {
                                    /**
                                     * click to details and update
                                     */
                                    Intent intent = new Intent(getContext(), GestionarEspecialistaActivity.class);
                                    intent.putExtra(GestionarEspecialistaActivity.EXTRA_ID, usuario.getId_usuario());
                                    intent.putExtra(GestionarEspecialistaActivity.EXTRA_NOMBRE, usuario.getName());
                                    intent.putExtra(GestionarEspecialistaActivity.EXTRA_APELLIDO1, usuario.getLastName1());
                                    intent.putExtra(GestionarEspecialistaActivity.EXTRA_APELLIDO2, usuario.getLastName2());
                                    intent.putExtra(GestionarEspecialistaActivity.EXTRA_NOMBRE_USUARIO, usuario.getUser_name());
                                    String password = "";
                                    String encryptPassword = usuario.getPassword();
                                    try {
                                        password = AESUtils.decrypt(encryptPassword);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    intent.putExtra(GestionarEspecialistaActivity.EXTRA_PASSWORD, password);
                                    startActivityForResult(intent, UPDATE_ESPECIALISTA_REQUEST);
                                }
                            });
                            recyclerView.setAdapter(adapter);
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            tvInfo.setVisibility(View.VISIBLE);
                        }
                    } else if (response.code() == 206) {
                        recyclerView.setVisibility(View.GONE);
                        tvInfo.setVisibility(View.VISIBLE);
                    }
                } else {
                    FancyToast.makeText(getContext(), getString(R.string.code_400), FancyToast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                Log.e("ListEsp", "onFailure: ", t);
                hideDialog();
                FancyToast.makeText(getContext(), getString(R.string.code_4221), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });

    }

    private void setUpRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_view_especialistas);
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
