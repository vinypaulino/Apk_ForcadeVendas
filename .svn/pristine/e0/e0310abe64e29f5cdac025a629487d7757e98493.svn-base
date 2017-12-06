package com.najasoftware.fdv.service;

import android.content.Context;
import android.util.Log;

import com.najasoftware.fdv.R;
import com.najasoftware.fdv.model.Uf;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import livroandroid.lib.utils.FileUtils;

/**
 * Created by Lemoel Marques - NajaSoftware on 04/05/2016.
 * lemoel@gmail.com
 */
public class UfsService {

    private static final boolean LOG_ON = false;
    private static final String TAG = "UfsService";

    public static List<Uf> getUfs(Context context) throws IOException {
        try {
            String json = readFile(context);
            List<Uf> ufs = parserJson(context,json);
            return ufs;
        } catch (Exception e) {
            Log.e(TAG, "Erro ao ler os cidades: " + e.getMessage(),e);
            return null;
        }
    }

    private static List<Uf> parserJson(Context context, String json) throws IOException {

        List<Uf> ufs = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(json);
            JSONObject obj = root.getJSONObject("ufs");
            JSONArray jsonUfs = obj.getJSONArray("uf");

            for(int i = 0; i< jsonUfs.length(); i++) {
                JSONObject jsonUf = jsonUfs.getJSONObject(i);
                Uf uf = new Uf();

                //Lê informações de cada estado
                uf.setId(jsonUf.optLong("id"));
                uf.setNome(jsonUf.optString("nome"));
                uf.setSigla(jsonUf.optString("sigla"));

                if (LOG_ON) {
                    Log.d(TAG, "Uf " + uf.getNome());
                }
                ufs.add(uf);
            }

            if (LOG_ON) {
                Log.d(TAG,ufs.size() + " encontrados. ");
            }

        } catch (JSONException e) {
            throw  new IOException(e.getMessage(),e);
        }
        return ufs;
    }

    private static String readFile(Context context) throws IOException {
        return FileUtils.readRawFileString(context, R.raw.ufs,"UTF-8");
    }

}
