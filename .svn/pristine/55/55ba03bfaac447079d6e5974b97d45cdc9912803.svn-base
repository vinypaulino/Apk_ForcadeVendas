package com.najasoftware.fdv.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.najasoftware.fdv.model.Cidade;
import com.najasoftware.fdv.model.Uf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 04/05/2016.
 * lemoel@gmail.com
 */
public class CidadeDAO extends BancoDAO {

    private static final String ID = "_id";
    private static final String TABLE = "CIDADES";
    private static final String NOME = "NOME";
    private static final String UF_ID = "UF_ID";
    private String sql;
    private Context context;

    public CidadeDAO(Context context) {
        super(context);
        this.context = context;
    }

    public Cidade getCidade(Long id) {

        Cidade city = new Cidade();

        sql = "SELECT * FROM " + TABLE + " WHERE " + ID + " = ?;";
        String args[] = new String[]{Long.toString(id)};

        try {
            Cursor c = getDb().rawQuery(sql, args);
            if (c.moveToNext()) {
                city.setId(c.getLong(c.getColumnIndex(ID)));
                city.setNome(c.getString(c.getColumnIndex(NOME)));
                city.setUf(new UfDAO(context).getUf(c.getLong(c.getColumnIndex(UF_ID))));
            }
        } finally {
            getDb().close();
        }

        return city;
    }

    public void insere(List<Cidade> cidades) {
        for (Cidade city : cidades) {
            ContentValues dados = pegaDadosDoCidade(city);
            getDb().insert(TABLE, null, dados);
        }
    }

    @NonNull
    private ContentValues pegaDadosDoCidade(Cidade city) {
        ContentValues dados = new ContentValues();
        dados.put(ID, city.getId());
        dados.put(NOME, city.getNome());
        dados.put(UF_ID, city.getUf().getId());
        return dados;
    }

    public void deleteAll() {
        sql = "DELETE FROM " + TABLE;
        getDb().execSQL(sql);
    }

    public List<Cidade> getCidadePorEstado(Long ufId) {
        List<Cidade> cidades = new ArrayList<>();
        sql = "SELECT * FROM " + TABLE + " WHERE " + UF_ID + " = ? ORDER BY NOME ;";
        String args[] = new String[]{Long.toString(ufId)};

        try {

            Cursor c = getDb().rawQuery(sql, args);

            if (c.moveToNext()) {
                do {
                    Cidade cidade = new Cidade();
                    cidade.setId(c.getLong(c.getColumnIndex(ID)));
                    cidade.setNome(c.getString(c.getColumnIndex(NOME)));
                    cidade.setUf(new UfDAO(context).getUf(c.getLong(c.getColumnIndex(UF_ID))));
                    cidades.add(cidade);
                } while (c.moveToNext());
            }

        } finally {
            getDb().close();
        }
        return cidades;
    }

    public List<Cidade> getCidades() {

        try {
            List<Cidade> cidades = new ArrayList<>();
            //Cursor SELECT * FROM CLIENTES;
            Cursor c = getDb().query(TABLE, null, null, null, null, null, "_id");

            if (c.moveToNext()) {
                do {
                    Cidade city = new Cidade();
                    city.setId(c.getLong(c.getColumnIndex(ID)));
                    city.setNome(c.getString(c.getColumnIndex(NOME)));
                    city.setUf(new Uf(c.getLong(c.getColumnIndex(UF_ID))));
                    cidades.add(city);
                } while (c.moveToNext());
            }

            c.close();
            return cidades;
        } finally {
            getDb().close();
        }
    }

}