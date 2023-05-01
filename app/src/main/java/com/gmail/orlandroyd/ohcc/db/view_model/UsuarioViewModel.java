package com.gmail.orlandroyd.ohcc.db.view_model;

import android.app.Application;

import com.gmail.orlandroyd.ohcc.db.entity.UsuarioEntity;
import com.gmail.orlandroyd.ohcc.db.repository.UsuarioRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UsuarioViewModel extends AndroidViewModel {

    private UsuarioRepository repository;
    private LiveData<List<UsuarioEntity>> allUsuarios;

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        repository = new UsuarioRepository(application);
        allUsuarios = repository.getAll();
    }

    public void insert(UsuarioEntity usuarioEntity) {
        repository.insert(usuarioEntity);
    }

    public void update(UsuarioEntity usuarioEntity) {
        repository.update(usuarioEntity);
    }

    public void delete(UsuarioEntity usuarioEntity) {
        repository.delete(usuarioEntity);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public LiveData<List<UsuarioEntity>> getAllUsuarios() {
        return allUsuarios;
    }
}
