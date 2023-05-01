package com.gmail.orlandroyd.ohcc.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.gmail.orlandroyd.ohcc.db.dao.UsuarioDao;
import com.gmail.orlandroyd.ohcc.db.db_helper.UsuarioDB;
import com.gmail.orlandroyd.ohcc.db.entity.UsuarioEntity;

import java.util.List;

import androidx.lifecycle.LiveData;

public class UsuarioRepository {
    private UsuarioDao usuarioDao;
    private LiveData<List<UsuarioEntity>> allUsuarios;

    public UsuarioRepository(Application application) {
        UsuarioDB usuarioDB = UsuarioDB.getInstance(application);
        usuarioDao = usuarioDB.usuarioDao();
        allUsuarios = usuarioDao.getAll();
    }

    public void insert(UsuarioEntity usuarioEntity) {
        new insertUsuarioAsyncTask(usuarioDao).execute(usuarioEntity);
    }

    public void update(UsuarioEntity usuarioEntity) {
        new updateUsuarioAsyncTask(usuarioDao).execute(usuarioEntity);
    }

    public void delete(UsuarioEntity usuarioEntity) {
        new deleteUsuarioAsyncTask(usuarioDao).execute(usuarioEntity);
    }

    public void deleteAll() {
        new deleteAllUsuariosAsyncTask(usuarioDao).execute();
    }

    public LiveData<List<UsuarioEntity>> getAll() {
        return allUsuarios;
    }

    private static class insertUsuarioAsyncTask extends AsyncTask<UsuarioEntity, Void, Void> {
        private UsuarioDao usuarioDao;

        public insertUsuarioAsyncTask(UsuarioDao usuarioDao) {
            this.usuarioDao = usuarioDao;
        }

        @Override
        protected Void doInBackground(UsuarioEntity... usuarioEntities) {
            usuarioDao.insert(usuarioEntities[0]);
            return null;
        }
    }

    private static class updateUsuarioAsyncTask extends AsyncTask<UsuarioEntity, Void, Void> {
        private UsuarioDao usuarioDao;

        public updateUsuarioAsyncTask(UsuarioDao usuarioDao) {
            this.usuarioDao = usuarioDao;
        }

        @Override
        protected Void doInBackground(UsuarioEntity... usuarioEntities) {
            usuarioDao.update(usuarioEntities[0]);
            return null;
        }
    }

    private static class deleteUsuarioAsyncTask extends AsyncTask<UsuarioEntity, Void, Void> {
        private UsuarioDao usuarioDao;

        public deleteUsuarioAsyncTask(UsuarioDao usuarioDao) {
            this.usuarioDao = usuarioDao;
        }

        @Override
        protected Void doInBackground(UsuarioEntity... usuarioEntities) {
            usuarioDao.delete(usuarioEntities[0]);
            return null;
        }
    }

    private static class deleteAllUsuariosAsyncTask extends AsyncTask<Void, Void, Void> {
        private UsuarioDao usuarioDao;

        public deleteAllUsuariosAsyncTask(UsuarioDao usuarioDao) {
            this.usuarioDao = usuarioDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            usuarioDao.deleteAll();
            return null;
        }
    }
}
