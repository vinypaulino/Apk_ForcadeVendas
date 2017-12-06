package com.najasoftware.fdv.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.najasoftware.fdv.FdvApplication;
import com.najasoftware.fdv.model.Cliente;
import com.najasoftware.fdv.model.ClienteStatusEnum;
import com.najasoftware.fdv.model.Endereco;
import com.najasoftware.fdv.model.Telefone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 04/03/2016.
 * lemoel@gmail.com
 */
public class ClienteDAO extends BancoDAO {

    //Colunas
    private final String TABLE = "CLIENTES";
    private final String ID = "_id";
    private final String CNPJ = "CNPJ";
    private final String RG = "RG";
    private final String ORGAO_RG = "ORGAO_RG";
    private final String INSCRICAO_ESTADUAL = "INSCRICAO_ESTADUAL";
    private final String DT_CADASTRO = "DT_CADASTRO";
    private final String DT_ULTIMA_ALTERACAO = "DT_ULTIMA_ALTERACAO";
    private final String NOME = "NOME";
    private final String NOME_FANTASIA = "NOME_FANTASIA";
    private final String STATUS = "STATUS";
    private final String STATUS_FINANCEIRO = "STATUS_FINANCEIRO";
    private final String LATITUDE = "LATITUDE";
    private final String LONGITUDE = "LONGITUDE";
    private final String EMAIL = "EMAIL";
    private final String OBS = "OBS";
    private final String VENDEDOR_ID = "VENDEDOR_ID";

    //Buscando o Vendedor Logado
    private FdvApplication app = FdvApplication.getInstance();

    private Context context;
    private String sql;

    public ClienteDAO(Context context) {
        super(context);
        this.context = context;
    }

    public boolean insere(Cliente cliente) {
        ContentValues dados = pegaDadosDoCliente(cliente);

        try {
            Long idCliente = getDb().insert(TABLE, null, dados);

            if (idCliente != -1) {
                if (cliente.getTelefones().size() > 0) {
                    for (Telefone tel : cliente.getTelefones()) {
                        ContentValues dadosTel = pegaDadosDoTelefone(tel);
                        getDb().insert("TELEFONES", null, dadosTel);
                    }
                }

                if (cliente.getEnderecos().size() > 0) {
                    for (Endereco end : cliente.getEnderecos()) {
                        ContentValues dadosEnd = pegaDadosDoEndereco(end);
                        getDb().insert("ENDERECOS", null, dadosEnd);
                    }
                }
            } else {
                return false;
            }
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, "Cliente já cadastrado", Toast.LENGTH_LONG);
            return false;
        }
        return true;
    }

    private ContentValues pegaDadosDoEndereco(Endereco end) {
        ContentValues dados = new ContentValues();
        dados.put("CLIENTE_CNPJ", end.getCliente().getCnpj());
        dados.put("CIDADE_ID", end.getCidade().getId());
        dados.put("NOME_RUA", end.getRua());
        dados.put("NUMERO", end.getNumero());
        dados.put("BAIRRO", end.getBairro());
        dados.put("CEP", end.getCep());
        dados.put("COMPLEMENTO", end.getComplemento());
        return dados;
    }

    @NonNull
    private ContentValues pegaDadosDoCliente(Cliente cliente) {
        ContentValues dados = new ContentValues();

        dados.put(CNPJ, cliente.getCnpj());
        dados.put(RG, cliente.getRg());
        dados.put(ORGAO_RG, cliente.getOrgaoExpedidorRg());
        dados.put(INSCRICAO_ESTADUAL, cliente.getInscricacaoEstadual());
        dados.put(NOME, cliente.getNome());
        dados.put(NOME_FANTASIA, cliente.getNomeFantasia());
        dados.put(DT_CADASTRO, cliente.getDtCadastro());
        dados.put(DT_ULTIMA_ALTERACAO, cliente.getDtUltimaAlteracao());
        dados.put(EMAIL, cliente.getEmail());
        dados.put(OBS, cliente.getObs());
        dados.put(STATUS, cliente.getStatus());
        dados.put(STATUS_FINANCEIRO, cliente.getStatusFinanceiro());
        dados.put(LATITUDE, cliente.getLatitude());
        dados.put(LONGITUDE, cliente.getLongitude());
        dados.put(VENDEDOR_ID,cliente.getVendedorId());

        return dados;
    }

    public List<Cliente> buscaClientes() throws SQLiteException {

        boolean verClientesDosOutros = verClientesDosOutros();

        Cliente cliente = null;

        if (verClientesDosOutros == false) {

            String args[] = new String[]{app.getVendedor().getId().toString()};

            String where = VENDEDOR_ID + " = ?";

            List<Cliente> clientes = new ArrayList<Cliente>();
            try {
                //Cursor SELECT * FROM CLIENTES;
                //ultimo parametro e para limitar a consulta de clientes (Viny)
                Cursor c = getDb().query(TABLE, null, where, args, null, null, "nome_fantasia, cnpj", "100");
                clientes = this.toList(c);
                c.close();
                return clientes;
            } finally {
                getDb().close();
            }

        } else {

            List<Cliente> clientes = new ArrayList<Cliente>();

            try {
                //Cursor SELECT * FROM CLIENTES;
                Cursor c = getDb().query(TABLE, null, null, null, null, null, "nome_fantasia, cnpj", "100");
                clientes = this.toList(c);
                c.close();
                return clientes;
            } finally {
                getDb().close();
            }
        }

    }

    private boolean verClientesDosOutros(){

        boolean verTodosClientes = false;

        sql = " SELECT * FROM PARAMETROS ";

        Cursor c = getDb().rawQuery(sql,null);
        if (c.moveToNext()) {
            String x = c.getString(c.getColumnIndex("VER_TODOS_CLIENTE"));
            verTodosClientes = Boolean.parseBoolean((x.equals("1")) ? "true" : "false");
        }
        return  verTodosClientes;
    }


    public Cliente getCliente(String clienteCnpj) {

        Cliente cliente = null;
        sql = "SELECT * FROM " + TABLE + " WHERE " + CNPJ + " = ?;";
        String args[] = new String[]{clienteCnpj.toString()};

        try {
            Cursor c = getDb().rawQuery(sql, args);
            List<Cliente> clientes = toList(c);

            if (clientes.size() > 0) {
                cliente = clientes.get(0);
            }

            c.close();
            return cliente;
        } finally {
            getDb().close();
        }

    }

    public List<Cliente> buscalAll(String query) {

        boolean verTodosClientes = verClientesDosOutros();
        List<Cliente> clientes = new ArrayList<>();

        if (verTodosClientes == false) {

            sql = " SELECT * FROM CLIENTES C " +
                    " LEFT JOIN ENDERECOS END ON END.CLIENTE_CNPJ = C.CNPJ " +
                    " LEFT JOIN TELEFONES TEL ON TEL.CLIENTE_CNPJ = C.CNPJ " +
                    " LEFT JOIN CIDADES CID ON CID._id = END.CIDADE_ID " +
                    " WHERE (TEL.NUM_TEL LIKE  ? " +
                    " OR C.NOME LIKE ? " +
                    " OR C.NOME_FANTASIA LIKE ? " +
                    " OR C.EMAIL LIKE ? " +
                    " OR C.CNPJ LIKE ? " +
                    " OR END.BAIRRO LIKE ? " +
                    " OR CID.NOME LIKE ? )" +
                    " AND C.VENDEDOR_ID  = ? " +
                    " GROUP BY C.CNPJ " +
                    "LIMIT 50";

            query = "%" + query + "%";
            String args[] = new String[]{query, query, query, query, query, query, query, app.getVendedor().getId().toString()};

            try {
                Cursor c = getDb().rawQuery(sql, args);
                clientes = this.toList(c);
                c.close();
            } finally {
                getDb().close();
            }

        } else {
            sql = " SELECT * FROM CLIENTES C " +
                    " LEFT JOIN ENDERECOS END ON END.CLIENTE_CNPJ = C.CNPJ " +
                    " LEFT JOIN TELEFONES TEL ON TEL.CLIENTE_CNPJ = C.CNPJ " +
                    " LEFT JOIN CIDADES CID ON CID._id = END.CIDADE_ID " +
                    " WHERE (TEL.NUM_TEL LIKE  ? " +
                    " OR C.NOME LIKE ? " +
                    " OR C.NOME_FANTASIA LIKE ? " +
                    " OR C.EMAIL LIKE ? " +
                    " OR C.CNPJ LIKE ? " +
                    " OR END.BAIRRO LIKE ? " +
                    " OR CID.NOME LIKE ? )" +
                    " GROUP BY C.CNPJ " +
                    "LIMIT 50";

            query = "%" + query + "%";
            String args[] = new String[]{query, query, query, query, query, query, app.getVendedor().getId().toString()};

            try {
                Cursor c = getDb().rawQuery(sql, args);
                clientes = this.toList(c);
                c.close();
            } finally {
                getDb().close();
            }
        }

        return clientes;

    }

    private List<Cliente> toList(Cursor c) {

        List<Cliente> clientes = new ArrayList<>();
        TelefoneDAO telefoneDAO = new TelefoneDAO(context);
        EnderecoDAO enderecoDAO = new EnderecoDAO(context);

        if (c.moveToNext()) {
            do {
                Cliente cliente = new Cliente();
                cliente.setCnpj(c.getString(c.getColumnIndex(CNPJ)));
                cliente.setRg(c.getString(c.getColumnIndex(RG)));
                cliente.setOrgaoExpedidorRg(c.getString(c.getColumnIndex(ORGAO_RG)));
                cliente.setInscricacaoEstadual(c.getString(c.getColumnIndex(INSCRICAO_ESTADUAL)));
                cliente.setNome(c.getString(c.getColumnIndex(NOME)));
                cliente.setNomeFantasia(c.getString(c.getColumnIndex(NOME_FANTASIA)));
                cliente.setStatus(c.getInt(c.getColumnIndex(STATUS)));
                cliente.setStatusFinanceiro(c.getString(c.getColumnIndex(STATUS_FINANCEIRO)));
                cliente.setVendedorId(c.getLong(c.getColumnIndex(VENDEDOR_ID)));

                //Coordenadas geograficas do cliente;
                cliente.setLatitude(c.getDouble(c.getColumnIndex(LATITUDE)));
                cliente.setLongitude(c.getDouble(c.getColumnIndex(LONGITUDE)));

                cliente.setEmail(c.getString(c.getColumnIndex(EMAIL)));
                cliente.setDtCadastro(c.getString(c.getColumnIndex(DT_CADASTRO)));
                cliente.setDtUltimaAlteracao(c.getString(c.getColumnIndex(DT_ULTIMA_ALTERACAO)));
                cliente.setObs(c.getString(c.getColumnIndex(OBS)));

                //Buscar Telefones
                List<Telefone> telefones = new ArrayList<>();
                telefones = telefoneDAO.getTelefone(cliente);
                cliente.setTelefones(telefones);

                //Buscar Enderecos
                List<Endereco> enderecos = new ArrayList<>();
                enderecos = enderecoDAO.getEnderecos(cliente);
                cliente.setEnderecos(enderecos);

                clientes.add(cliente);
            } while (c.moveToNext());
        }

        return clientes;
    }

    public void deleteAll() {

        TelefoneDAO tel = new TelefoneDAO(context);
        tel.deleteAll();

        EnderecoDAO end = new EnderecoDAO(context);
        end.deleteAll();

        sql = " DELETE FROM " + TABLE;
        getDb().execSQL(sql);

    }

    @NonNull
    private ContentValues pegaDadosDoTelefone(Telefone tel) {
        ContentValues dados = new ContentValues();
        dados.put("CLIENTE_CNPJ", tel.getCliente().getCnpj());
        dados.put("NUM_TEL", tel.getNumero());
        return dados;
    }

    public boolean update(Cliente cliente) {

        ContentValues dadosCliente = new ContentValues();
        dadosCliente = pegaDadosDoCliente(cliente);
        getDb().update("clientes", dadosCliente, "cnpj = ? ", new String[]{String.valueOf(cliente.getCnpj())});

        if (cliente.getTelefones().size() > 0) {
            for (Telefone tel : cliente.getTelefones()) {
                ContentValues dadosTel = pegaDadosDoTelefone(tel);
                getDb().update("TELEFONES", dadosTel, " CLIENTE_CNPJ = ? AND _id = ? ", new String[]{String.valueOf(cliente.getCnpj()), String.valueOf(tel.getId())});
            }
        }

        if (cliente.getEnderecos().size() > 0) {
            for (Endereco end : cliente.getEnderecos()) {
                ContentValues dadosEnd = pegaDadosDoEndereco(end);
                getDb().update("ENDERECOS", dadosEnd, " CLIENTE_CNPJ = ? ", new String[]{String.valueOf(cliente.getCnpj())});
            }
        }

        return true;

    }

    public void updateStatus(Cliente cliente) {
        ContentValues dadosCliente = new ContentValues();
        dadosCliente.put("STATUS", cliente.getStatus());
        getDb().update("clientes", dadosCliente, "cnpj = ? ", new String[]{String.valueOf(cliente.getCnpj())});
    }

    public List<Cliente> getClientes(ClienteStatusEnum alterado) {
        Cliente cliente = null;
        sql = "SELECT * FROM " + TABLE + " WHERE " + STATUS + " = ?;";
        String args[] = new String[]{Integer.toString(alterado.getStatus())};

        try {
            Cursor c = getDb().rawQuery(sql, args);
            List<Cliente> clientes = toList(c);
            c.close();
            return clientes;
        } finally {
            getDb().close();
        }
    }

    public void statusTo(List<Cliente> clientes, ClienteStatusEnum clienteStatusEnum) {
        ContentValues dados = new ContentValues();
        dados.put(STATUS, clienteStatusEnum.getStatus());

        try {
            for (Cliente cliente : clientes) {
                String[] params = {cliente.getCnpj().toString()};
                getDb().update(TABLE, dados, " CNPJ = ? ", params);
            }
        } finally {
            getDb().close();
        }
    }
}