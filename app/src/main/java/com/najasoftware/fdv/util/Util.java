package com.najasoftware.fdv.util;

import android.content.Context;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Lemoel Marques - NajaSoftware on 10/03/2016.
 * lemoel@gmail.com
 */
public class Util {

    private Context context;

    public Util(Context context) {
        this.context = context;
    }

    public Util() {
    }

    public String formataMoeda(Double valor) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return nf.format(valor);
    }

    public void gerarEstruturaDeArquivos(Context context) {

        File fEmpires = new File(context.getFilesDir() + "/empires");
        if (!fEmpires.exists()) {
            fEmpires.mkdir();
        }

    }

    public String dataAtual() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date dataAtual = new Date(System.currentTimeMillis());
        String data = formato.format(dataAtual);
        return data;
    }

    public String dataHora() {
        SimpleDateFormat formato = new SimpleDateFormat("dd_MM_yyyy HH_mm_ss");
        Date dataAtual = new Date(System.currentTimeMillis());
        String data = formato.format(dataAtual);
        return data;
    }


    public String aproximar(Double numero) {
        DecimalFormat aproximardor = new DecimalFormat("0.00");
        String num = aproximardor.format(numero);
        return num.replace(",", ".");
    }

    public String aproximar(String numero) {
        DecimalFormat aproximardor = new DecimalFormat("0.00");
        String num = aproximardor.format(Double.parseDouble(numero));
        return num.replace(",", ".");
    }

    public String md5(String palavra) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger hash = new BigInteger(1, md.digest(palavra.getBytes()));
        return (hash.toString(16));
    }

}
