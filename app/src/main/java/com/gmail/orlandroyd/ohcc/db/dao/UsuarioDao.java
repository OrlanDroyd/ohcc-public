package com.gmail.orlandroyd.ohcc.db.dao;

import com.gmail.orlandroyd.ohcc.db.entity.UsuarioEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UsuarioDao {

    @Insert
    void insert(UsuarioEntity usuarioEntity);

    @Update
    void update(UsuarioEntity usuarioEntity);

    @Delete
    void delete(UsuarioEntity usuarioEntity);

    @Query("DELETE FROM usuario")
    void deleteAll();

    @Query("SELECT * FROM usuario ORDER BY id_usuario DESC")
    LiveData<List<UsuarioEntity>> getAll();
}
