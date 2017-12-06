package com.najasoftware.fdv.service;

import android.content.Context;
import android.util.Log;

import com.najasoftware.fdv.model.FormaPgto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import livroandroid.lib.utils.FileUtils;

/**
 * Created by Lemoel Marques - NajaSoftware on 03/05/2016.
 * lemoel@gmail.com
 */
public class FormaPgtoService {

    private static final boolean LOG_ON = false;
    private static final String TAG = "FormaPgtoService";

    public static List<FormaPgto> getFormaPgto(Context context) throws IOException {
        try {
            String fileName = String.format("forma_pgto_fdv.json");
            Log.d(TAG,"Abrindo arquivo: " + fileName);

            //Lê o arquivo da memoria interna
            String json = FileUtils.readFile(context,fileName,"UTF-8");
            if(json == null) {
                Log.d(TAG,"Arquivo " + fileName + " Não encontrado");
                return null;
            }
            List<FormaPgto> formaPgtoList = parserJson(context,json);
            return formaPgtoList;
        } catch (Exception e) {
            Log.e(TAG, "Erro ao ler os clientes: " + e.getMessage(),e);
            return null;
        }
    }

    private static List<FormaPgto> parserJson(Context context, String json) throws IOException {
        List<FormaPgto> formaPgtoList = new ArrayList<FormaPgto>();

        try {
            JSONObject root = new JSONObject(json);
            JSONObject obj = root.getJSONObject("forma_pgtos");
            JSONArray jsonFormaPgtoArray = obj.getJSONArray("forma_pgto");

            for(int i = 0; i< jsonFormaPgtoArray.length(); i++) {
                JSONObject jsonFormaPgto = jsonFormaPgtoArray.getJSONObject(i);
                FormaPgto fp = new FormaPgto();

                //Lê informações de cada produto
                fp.setId(jsonFormaPgto.optLong("id"));
                fp.setNome(jsonFormaPgto.optString("nome"));

                if (LOG_ON) {
                    Log.d(TAG, "Cliente " + fp.getNome());
                }

                formaPgtoList.add(fp);
            }

            if (LOG_ON) {
                Log.d(TAG,formaPgtoList.size() + " encontrados. ");
            }

        } catch (JSONException e) {
            throw  new IOException(e.getMessage(),e);
        }
        return formaPgtoList;
    }
}
