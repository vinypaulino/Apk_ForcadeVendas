package com.najasoftware.fdv.service;

import android.content.Context;
import android.util.Log;

import com.najasoftware.fdv.dao.CidadeDAO;
import com.najasoftware.fdv.model.Cidade;
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
public class CidadesService {

    private static final boolean LOG_ON = false;
    private static final String TAG = "UfsService";

    public static List<Cidade> getCidades(Context context) throws IOException {
        try {
            String fileName = String.format("cidades_fdv.json");
            Log.d(TAG,"Abrindo arquivo: " + fileName);

            //Lê o arquivo da memoria interna
            String json = FileUtils.readFile(context,fileName,"UTF-8");

            if(json == null) {
                Log.d(TAG,"Arquivo " + fileName + " Não encontrado");
                return null;
            }
            List<Cidade> cidades = parserJson(context,json);
            return cidades;

        } catch (Exception e) {
            Log.e(TAG, "Erro ao ler os cidades: " + e.getMessage(), e);
            return null;
        }
    }

    private static List<Cidade> parserJson(Context context, String json) throws IOException {

        List<Cidade> cidades = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(json);
            JSONObject obj = root.getJSONObject("cidades");
            JSONArray jsonCidades = obj.getJSONArray("cidade");

            for (int i = 0; i < jsonCidades.length(); i++) {
                JSONObject jsonCidade = jsonCidades.getJSONObject(i);
                Cidade cidade = new Cidade();

                //Lê informações de cada produto
                cidade.setId(jsonCidade.optLong("id"));
                cidade.setNome(jsonCidade.optString("nome"));
                cidade.setUf(new Uf(jsonCidade.optLong("uf_id")));

                if (LOG_ON) {
                    Log.d(TAG, "Cidade " + cidade.getNome());
                }
                cidades.add(cidade);
            }

            if (LOG_ON) {
                Log.d(TAG, cidades.size() + " encontrados. ");
            }

        } catch (JSONException e) {
            throw new IOException(e.getMessage(), e);
        }
        return cidades;
    }

    public String toJson(Context context) {

        CidadeDAO cidadeDAO = new CidadeDAO(context);
        List<Cidade> cidades = cidadeDAO.getCidades();
        JSONObject root = new JSONObject();
        JSONObject nomeArrayCidade = new JSONObject();

        try {

            JSONArray jsonCidades = new JSONArray();

            for (Cidade cidade : cidades) {
                JSONObject jsonCidade = new JSONObject();
                jsonCidade.put("id", cidade.getId().toString());
                jsonCidade.put("nome", cidade.getNome());
                jsonCidade.put("uf_id", cidade.getUf().getId());
                jsonCidades.put(jsonCidade);
            }
            nomeArrayCidade.put("cidade", jsonCidades);
            root.put("cidades", nomeArrayCidade);
        } catch (Exception e) {

        }
        return  root.toString();
    }
}
