package com.najasoftware.fdv.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.najasoftware.fdv.R;
import com.najasoftware.fdv.model.CategoriaProduto;

import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 12/04/2016.
 * lemoel@gmail.com
 */
public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriasViewHolder> {

    protected static final String TAG = "fdv";
    private final List<CategoriaProduto> categorias;
    private final Context context;
    private CategoriaOnClickListener categoriaOnClickListener;

    public CategoriaAdapter(Context context, List<CategoriaProduto> categorias, CategoriaOnClickListener categoriaOnClickListener) {
        this.context = context;
        this.categorias = categorias;
        this.categoriaOnClickListener = categoriaOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.categorias != null ? this.categorias.size() : 0;
    }

    @Override
    public CategoriasViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_categoria, viewGroup, false);
        CategoriasViewHolder holder = new CategoriasViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CategoriasViewHolder holder, final int position) {

        // Atualiza a view
        CategoriaProduto categoria = categorias.get(position);
        holder.tNome.setText(categoria.getNome());
        holder.tCod.setText(categoria.getId().toString());

        // Click
        if (categoriaOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // A variável position é final
                    categoriaOnClickListener.onClickCategoria(holder.itemView, position);
                }
            });
        }
    }

    public interface CategoriaOnClickListener {
        public void onClickCategoria(View view, int idx);
    }

    // ViewHolder com as views
    public static class CategoriasViewHolder extends RecyclerView.ViewHolder {

        public TextView tNome,tCod;
        ImageView img;
        ProgressBar progress;
        CardView cardView;

        public CategoriasViewHolder(View view) {
            super(view);
            // Cria as views para salvar no ViewHolder
            tNome = (TextView) view.findViewById(R.id.text_nome_categoria);
            tCod = (TextView) view.findViewById(R.id.txt_id);
            cardView = (CardView) view.findViewById(R.id.card_view_categoria);
        }
    }

}