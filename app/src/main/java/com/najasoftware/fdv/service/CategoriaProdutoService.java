package com.najasoftware.fdv.service;

import android.content.Context;
import android.util.Log;

import com.najasoftware.fdv.model.CategoriaProduto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import livroandroid.lib.utils.FileUtils;

/**
 * Created by Lemoel Marques - NajaSoftware on 02/05/2016.
 * lemoel@gmail.com
 */
public class CategoriaProdutoService {

    private static final boolean LOG_ON = false;
    private static final String TAG = "CategoriaService";

    public static List<CategoriaProduto> getCategorias(Context context) throws IOException {
        try {
            String fileName = String.format("categorias_fdv.json");
            Log.d(TAG,"Abrindo arquivo: " + fileName);

            //Lê o arquivo da memoria interna
            String json = FileUtils.readFile(context,fileName,"UTF-8");
            if(json == null) {
                Log.d(TAG,"Arquivo " + fileName + " Não encontrado");
                return null;
            }
            List<CategoriaProduto> categorias = parserJson(context,json);
            return categorias;
        } catch (Exception e) {
            Log.e(TAG, "Erro ao ler os produtos: " + e.getMessage(),e);
            return null;
        }
    }

    private static List<CategoriaProduto> parserJson(Context context, String json) throws IOException {
        List<CategoriaProduto> categorias = new ArrayList<CategoriaProduto>();

        try {
            JSONObject root = new JSONObject(json);
            JSONObject obj = root.getJSONObject("categorias");
            JSONArray jsonCategorias = obj.getJSONArray("categoria");

            for(int i = 0; i< jsonCategorias.length(); i++) {
                JSONObject jsonCategoria = jsonCategorias.getJSONObject(i);
                CategoriaProduto ct = new CategoriaProduto();

                //Lê informações de cada produto
                ct.setId(jsonCategoria.optLong("id"));
                ct.setNome(jsonCategoria.optString("nome"));
                ct.setUrlFoto(jsonCategoria.optString("url_foto"));

                if (LOG_ON) {
                    Log.d(TAG, "Categoria " + ct.getNome());
                }

                categorias.add(ct);
            }

            if (LOG_ON) {
                Log.d(TAG,categorias.size() + " encontrados. ");
            }

        } catch (JSONException e) {
            throw  new IOException(e.getMessage(),e);
        }
        return categorias;
    }
}