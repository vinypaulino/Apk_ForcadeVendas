package com.najasoftware.fdv.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.najasoftware.fdv.model.Uf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 04/05/2016.
 * lemoel@gmail.com
 */
public class UfDAO extends BancoDAO {

    private static final String ID = "_id";
    private static final String TABLE = "UFS";
    private static final String NOME = "NOME";
    private static final String SIGLA = "SIGLA";
    private String sql;
    private Context context;

    public UfDAO(Context context) {
        super(context);
        this.context = context;
    }

    public Uf getUf(Long id) {

        Uf uf = new Uf();

        sql = "SELECT * FROM " + TABLE + " WHERE " + ID + " = ?;";
        String args[] = new String[]{Long.toString(id)};
        Cursor c = getDb().rawQuery(sql, args);
        if (c.moveToNext()) {
            uf.setId(c.getLong(c.getColumnIndex(ID)));
            uf.setNome(c.getString(c.getColumnIndex(NOME)));
            uf.setSigla(c.getString(c.getColumnIndex(SIGLA)));
        }
        return uf;
    }

    public List<Uf> getUfs() {
        List<Uf> ufs = new ArrayList<>();
        try {
            //Cursor SELECT * FROM FORMA PAGAMENTO;
            Cursor c = getDb().query(TABLE, null, null, null, null, null, NOME);

            if (c.moveToNext()) {
                do {
                    Uf uf = new Uf();
                    uf.setId(c.getLong(c.getColumnIndex(ID)));
                    uf.setNome(c.getString(c.getColumnIndex(NOME)));
                    uf.setSigla(c.getString(c.getColumnIndex(SIGLA)));
                    ufs.add(uf);
                } while (c.moveToNext());
            }

        } finally {
            getDb().close();
        }
        return ufs;
    }


    @NonNull
    private ContentValues pegaDadosDoEstado(Uf uf) {
        ContentValues dados = new ContentValues();
        dados.put(ID, uf.getId());
        dados.put(NOME, uf.getNome());
        dados.put(SIGLA, uf.getSigla());
        return dados;
    }


    public void deleteAll() {
        sql = "DELETE FROM " + TABLE;
        getDb().execSQL(sql);
    }

    public void insere(List<Uf> ufs) {
        for (Uf uf : ufs) {
            ContentValues dados = pegaDadosDoEstado(uf);
            getDb().insert(TABLE, null, dados);
        }
    }
}