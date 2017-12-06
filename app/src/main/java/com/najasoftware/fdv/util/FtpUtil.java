package com.najasoftware.fdv.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.najasoftware.fdv.dao.ConfigFtpDAO;
import com.najasoftware.fdv.model.ConfigFtp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Lemoel Marques - NajaSoftware on 07/03/2016.
 * lemoel@gmail.com
 */
public class FtpUtil {

    //private Handler mhandler = new Handler();
    private final Context context;
    ProgressDialog mdialog;
    FTPClient ftpClient = null;
    private String TAG = "classeFTP";

    public FtpUtil(Context context) {
        this.context = context;
    }


    public boolean desconectar() {
        try {
            ftpClient.logout();
            ftpClient.disconnect();
            ftpClient = null;
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Erro: ao desconectar. " + e.getMessage());
        }

        return false;
    }

    public boolean conectar(ConfigFtp configFtp) {

        ConfigFtpDAO configFtpDAO = new ConfigFtpDAO(context);
        String msg;

        if (configFtp == null) {
            configFtp = configFtpDAO.getConfigFtp();
        }

        try {
            ftpClient = new FTPClient();

            if (configFtp.getPorta() == 0)
                ftpClient.connect(configFtp.getHost());
            else
                ftpClient.connect(configFtp.getHost(), configFtp.getPorta());

            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                boolean status = ftpClient.login(configFtp.getLogin(), configFtp.getSenha());

                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();

                return status;
            } else {
                ftpClient.disconnect();
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro: não foi possível conectar ao servidor: " + e.getMessage(), e);
        }

        return false;
    }

    public boolean checkConexaoFTP(ConfigFtp configFtp) {
        boolean retorno = conectar(configFtp);
        if (retorno) desconectar();
        return retorno;
    }

    public void mensagem(String title, String msg, boolean error) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        if (error) {
            alerta.setTitle(Html.fromHtml("<font color='#ce3e2e'>" + title + "</font>"));
            alerta.setMessage(msg);
        } else {
            alerta.setTitle(Html.fromHtml("<font color='#64af72'>" + title + "</font>"));
            alerta.setMessage(msg);
        }

        alerta.setNeutralButton("OK", null);
        alerta.show();
    }

    public boolean mudarDiretorio(String diretorio) {
        try {
            ftpClient.changeWorkingDirectory(diretorio);
        } catch (Exception e) {
            Log.e(TAG, "Erro: não foi possível mudar o diretório para "
                    + diretorio);
        }
        return false;
    }

    /*
     * O metodo Download recebe os seguintes parametros: O diretorio de origem,
	 * o arquivo de origem e o de destino. E criada uma variavel do tipo
	 * Outputstream para o arquivo ser passado como parametro, logo em seguida
	 * definimos o tipo de arquivo como BINARY_FILE_TYPE.
	 * O metodo mais importante e o RetrieveFile sendo responsavel por baixar o arquivo.
	 * Apos estas etapas executamos o metodo Desconectar(). Temos como retorno
	 * um booleano que indica se ocorreu tudo corretamente.
     */

    public boolean download(String diretorioOrigem, String arqOrigem, String arqDestino) {

        boolean status = false;
        try {
            mudarDiretorio(diretorioOrigem);

            FileOutputStream desFileStream = new FileOutputStream(arqDestino);

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalActiveMode();
            ftpClient.enterLocalPassiveMode();

            status = ftpClient.retrieveFile(arqOrigem, desFileStream);
            desFileStream.close();
            desconectar();
            return status;
        } catch (Exception e) {
            Log.e(TAG, "Erro: Falha ao efetuar download. " + e.getMessage());
        }
        return status;
    }

    public boolean upload(String fileNameOrigem, String fileNameDestino) {

        File file = new File(context.getFilesDir(), fileNameOrigem);

        boolean status = false;
        try {
            FileInputStream arqEnviar = new FileInputStream(file);
            ftpClient.setFileTransferMode(FTPClient.STREAM_TRANSFER_MODE);
            ftpClient.setFileType(FTPClient.STREAM_TRANSFER_MODE);
            ftpClient.storeFile(fileNameDestino, arqEnviar);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Erro: Falha ao efetuar Upload. " + e.getMessage());
        }
        return status;
    }

}