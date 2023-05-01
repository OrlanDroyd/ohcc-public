package com.gmail.orlandroyd.ohcc.db.db_helper;

import android.content.Context;
import android.os.AsyncTask;

import com.gmail.orlandroyd.ohcc.db.dao.UsuarioDao;
import com.gmail.orlandroyd.ohcc.db.entity.UsuarioEntity;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {UsuarioEntity.class}, version = 1)
public abstract class UsuarioDB extends RoomDatabase {

    private static UsuarioDB instance;

    public abstract UsuarioDao usuarioDao();

    public static synchronized UsuarioDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    UsuarioDB.class,
                    "usuario_db")
                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
//            para insertar datos al principio
//            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private UsuarioDao usuarioDao;

        public PopulateDbAsyncTask(UsuarioDB usuarioDB) {
            this.usuarioDao = usuarioDB.usuarioDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            usuarioDao.insert(new UsuarioEntity("Orlando", "Peña", "Fernández", "Droyd", "12345678", 3));
            return null;
        }
    }
}
