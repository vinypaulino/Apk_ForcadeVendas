package com.najasoftware.fdv.dao;

import android.content.Context;
import android.database.Cursor;

import com.najasoftware.fdv.model.Cliente;
import com.najasoftware.fdv.model.Telefone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 04/05/2016.
 * lemoel@gmail.com
 */
public class TelefoneDAO extends BancoDAO {
    private final String TABLE = "TELEFONES";
    private final String CLIENTE_CNPJ = "CLIENTE_CNPJ";
    private final String ID = "_id";
    private final String NUM_TEL = "NUM_TEL";
    private Context context;
    private String sql;

    public TelefoneDAO(Context context) {
        super(context);
        this.context = context;
    }

    public List<Telefone> getTelefone(Cliente cliente) {

        List<Telefone> telefones = new ArrayList<>();

        sql = "SELECT * FROM " + TABLE + " WHERE " + CLIENTE_CNPJ + " = ?;";
        String args[] = new String[]{cliente.getCnpj().toString()};
        Cursor c = getDb().rawQuery(sql, args);
        if (c.moveToNext()) {
            do {
                Telefone tel = new Telefone();
                tel.setId(c.getLong(c.getColumnIndex(ID)));
                tel.setCliente(cliente);
                tel.setNumero(c.getString(c.getColumnIndex(NUM_TEL)));
                telefones.add(tel);
            } while (c.moveToNext());
        }
        return telefones;
    }

    public void deleteAll() {
        sql = " DELETE FROM " + TABLE;
        getDb().execSQL(sql);
    }
}
