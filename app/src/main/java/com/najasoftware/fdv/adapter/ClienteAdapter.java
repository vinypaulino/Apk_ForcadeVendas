package com.najasoftware.fdv.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.najasoftware.fdv.R;
import com.najasoftware.fdv.model.Cliente;
import com.najasoftware.fdv.model.Endereco;
import com.najasoftware.fdv.model.Telefone;

import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 04/04/2016.
 * lemoel@gmail.com
 */
public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClientesViewHolder> {

    protected static final String TAG = "fdv";
    private final List<Cliente> clientes;
    private final Context context;
    private ClienteOnClickListener clienteOnClickListener;

    public ClienteAdapter(Context context, List<Cliente> clientes, ClienteOnClickListener clienteOnClickListener) {
        this.context = context;
        this.clientes = clientes;
        this.clienteOnClickListener = clienteOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.clientes != null ? this.clientes.size() : 0;
    }

    @Override
    public ClientesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_cliente, viewGroup, false);
        final ClientesViewHolder holder = new ClientesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ClientesViewHolder holder, final int position) {
        // Atualiza a view
        Cliente c = clientes.get(position);
        holder.tNomeFantasia.setText(c.getNomeFantasia());
        holder.tNome.setText(c.getNome());
        holder.tCnpjCpf.setText(c.getCnpj());

        String telefone = "";
        int i = 0;

        if (c.getTelefones() != null) {
            for (Telefone tel : c.getTelefones()) {
                i++;
                telefone += tel.getNumero();
                if (i < c.getTelefones().size()) {
                    telefone += ", ";
                }
            }
        }
        holder.tTelefone.setText(telefone);
        holder.tEmail.setText(c.getEmail());

        i = 0;
        String endereco;
        if (c.getEnderecos() != null) {
            for (Endereco end : c.getEnderecos()) {
                i++;
                holder.tRua.setText(end.getRua());
                holder.tNumero.setText(end.getNumero());

                if (end.getCidade().getNome() != null) {
                    holder.tCidadeNome.setText(end.getCidade().getNome());
                    holder.tUf.setText(end.getCidade().getUf().getSigla());
                } else {
                    holder.tCidadeNome.setText("Não cadastrado");
                    holder.tUf.setText("NN");
                }

                holder.tvNomeBairro.setText(end.getBairro().toString());
            }
        }

        switch (c.getStatusFinanceiro()) {
            //Bom
            case "B":
                holder.imageViewStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.refresh_progress_2));
                break;
            //Regular
            case "G":
                holder.imageViewStatus.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_orange_light));
                break;
            //Ruim
            case "R":
                holder.imageViewStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.refresh_progress_3));
                break;
        }

        // Click curto
        if (clienteOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // A variável position é final
                    clienteOnClickListener.onClickCliente(holder.itemView, position);
                }
            });
        }

        // Click longo
        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

                MenuItem editar = menu.add("Editar");
                MenuItem ligar = menu.add("Ligar");

                //Fazendo a ligação para o Cliente
                ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        clienteOnClickListener.onLongClickCliente(holder.itemView,position,"ligar");
                        return false;
                    }
                });

                //Tela de detalhes do cliente.
                editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        clienteOnClickListener.onLongClickCliente(holder.itemView,position,"editar");
                        return false;
                    }
                });
            }
        });
    }

    public interface ClienteOnClickListener {
        public void onClickCliente(View view, int idx);
        public void onLongClickCliente (View view, int idx,String menu);
    }

    // ViewHolder com as views
    public static class ClientesViewHolder extends RecyclerView.ViewHolder {
        public TextView tNomeFantasia;
        public TextView tNome;
        public TextView tCnpjCpf;
        public TextView tTelefone;
        public TextView tEmail;
        public TextView tRua;
        public TextView tNumero;
        public TextView tCidadeNome;
        public TextView tUf;
        public TextView tvNomeBairro;
        public ImageView imageViewStatus;

//        ImageView img;
//        ProgressBar progress;
        CardView cardView;

        public ClientesViewHolder(View view) {
            super(view);
            // Cria as views para salvar no ViewHolder
            tNomeFantasia = (TextView) view.findViewById(R.id.tvNomeFantasia);
            tNome = (TextView) view.findViewById(R.id.tvNomeCliente);
            tCnpjCpf = (TextView) view.findViewById(R.id.tvCnpjCpf);
            tTelefone = (TextView) view.findViewById(R.id.tvTelefoneNumero);
            tEmail = (TextView) view.findViewById(R.id.tvEmailEnd);
            tRua = (TextView) view.findViewById(R.id.tvNomeRua);
            tNumero = (TextView) view.findViewById(R.id.tvNumero);
            tCidadeNome = (TextView) view.findViewById(R.id.tvCidade);
            tUf = (TextView) view.findViewById(R.id.tvUf);
            tvNomeBairro = (TextView) view.findViewById(R.id.tvNomeBairro);
            //img = (ImageView) view.findViewById(R.id.img_cliente);
            //progress = (ProgressBar) view.findViewById(R.id.progressImg_cliente);
            cardView = (CardView) view.findViewById(R.id.card_view_cliente);
            imageViewStatus = (ImageView)view.findViewById(R.id.imgVStatus);
        }
    }
}
