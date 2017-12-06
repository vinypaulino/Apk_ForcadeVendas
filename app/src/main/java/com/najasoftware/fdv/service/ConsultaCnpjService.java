package com.najasoftware.fdv.service;

import android.content.Context;
import android.util.Log;

import com.najasoftware.fdv.model.Credencial;

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
public class ConsultaCnpjService {

    private static final boolean LOG_ON = false;
    private static final String TAG = "CredencialService";

    public static List<Credencial> getDadosIniciais(Context context) throws IOException {
        try {
            String fileName = String.format("credencial.json");
            Log.d(TAG, "Abrindo arquivo: " + fileName);

            //Lê o arquivo da memoria interna
            String json = FileUtils.readFile(context, fileName, "UTF-8");
            if (json == null) {
                Log.d(TAG, "Arquivo " + fileName + " Não encontrado");
                return null;
            }
            List<Credencial> credencialList = parserJson(context, json);
            return credencialList;
        } catch (Exception e) {
            Log.e(TAG, "Erro ao ler os clientes: " + e.getMessage(), e);
            return null;
        }
    }

    private static List<Credencial> parserJson(Context context, String json) throws IOException {

        List<Credencial> credenciaisList = new ArrayList<Credencial>();

        try {
            JSONObject root = new JSONObject(json);
            JSONObject obj = root.getJSONObject("credenciais");
            JSONArray jsonCredenciaisArray = obj.getJSONArray("cliente");

            for (int i = 0; i < jsonCredenciaisArray.length(); i++) {
                JSONObject jsonCredencial = jsonCredenciaisArray.getJSONObject(i);
                Credencial credencial = new Credencial();

                //Lê informações de cada produto
                credencial.setCnpj(jsonCredencial.optString("cnpj"));
                credencial.setSenha(jsonCredencial.optString("senha"));
                credencial.setKey(jsonCredencial.optString("key"));

                if (LOG_ON) {
                    Log.d(TAG, "Credenciais " + credencial.getCnpj());
                }

                credenciaisList.add(credencial);
            }

            if (LOG_ON) {
                Log.d(TAG, credenciaisList.size() + " encontrados. ");
            }

        } catch (JSONException e) {
            throw new IOException(e.getMessage(), e);
        }
        return credenciaisList;
    }

}
