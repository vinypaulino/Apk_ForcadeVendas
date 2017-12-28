package com.najasoftware.fdv.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.najasoftware.fdv.R;
import com.najasoftware.fdv.model.Pedido;

import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 31/05/2016.
 * lemoel@gmail.com
 */
public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidosViewHolder> {

    private final List<Pedido> pedidos;
    private final Context context;
    private PedidosOnClickListener pedidosOnClickListener;

    public PedidoAdapter(Context context, List<Pedido> pedidos, PedidosOnClickListener pedidosOnClickListener) {
        this.context = context;
        this.pedidos = pedidos;
        this.pedidosOnClickListener = pedidosOnClickListener;

    }

    @Override
    public int getItemCount() {
        return this.pedidos != null ? this.pedidos.size() : 0;
    }

    @Override
    public PedidosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_pedido, viewGroup, false);
        final PedidosViewHolder holder = new PedidosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final PedidosViewHolder holder, final int position) {
        // Atualiza a view
        Pedido pedido = pedidos.get(position);
        holder.tNomeFantasia.setText(pedido.getCliente().getNomeFantasia());
        holder.tNomeCliente.setText(pedido.getCliente().getNome());
        holder.tFormaPgto.setText(pedido.getFormaPgto().getNome());
        holder.tPedidoTotalItens.setText(Integer.toString(pedido.getItens().size()));
        holder.tPedidoTotal.setText(pedido.getTotalSemDesconto().toString());
        holder.tPedidoDesconto.setText(pedido.getDesconto().toString());
        holder.tPedidoComDesconto.setText(pedido.getTotalComDesconto().toString());
        holder.tDtEnvio.setText(pedido.getDataEnvioServidor());
        holder.tDtPedido.setText(pedido.getDataVenda());

        // Click curto
        if (pedidosOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // A variável position é final
                    pedidosOnClickListener.onClickPedido(holder.itemView, position);
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
                        pedidosOnClickListener.onLongClickPedido(holder.itemView,position,"ligar");
                        return false;
                    }
                });

                //Tela de detalhes do cliente.
                editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        pedidosOnClickListener.onLongClickPedido(holder.itemView,position,"editar");
                        return false;
                    }
                });
            }
        });
    }

    public interface PedidosOnClickListener {
        public void onClickPedido(View view, int idx);
        public void onLongClickPedido (View view, int idx,String menu);
    }

    // ViewHolder com as views
    public static class PedidosViewHolder extends RecyclerView.ViewHolder {

        public TextView tNomeFantasia,
                tNomeCliente,
                tFormaPgto,
                tPedidoTotalItens,
                tPedidoTotal,
                tPedidoDesconto,
                tPedidoComDesconto,
                tDtEnvio,
                tDtPedido;

        public CardView cardView;

        public PedidosViewHolder(View view) {
            super(view);
            // Cria as views para salvar no ViewHolder
            tNomeFantasia = (TextView) view.findViewById(R.id.tvNomeFantasia);
            tNomeCliente = (TextView) view.findViewById(R.id.tvNomeCliente);
            tFormaPgto = (TextView) view.findViewById(R.id.tvFormaPgto);
            tPedidoTotalItens = (TextView) view.findViewById(R.id.tvPedidoTotalItens);
            tPedidoTotal = (TextView) view.findViewById(R.id.tvPedidoTotal);
            tPedidoDesconto = (TextView) view.findViewById(R.id.tvPedidoDesconto);
            tPedidoComDesconto = (TextView) view.findViewById(R.id.tvPedidoComDesconto);
            tDtEnvio = (TextView) view.findViewById(R.id.tvDtEnvio);
            tDtPedido = (TextView) view.findViewById(R.id.tvDtPedido);
            cardView = (CardView) view.findViewById(R.id.card_view_pedido);
        }
    }
}
