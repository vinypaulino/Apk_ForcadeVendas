package com.najasoftware.fdv.helper;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.najasoftware.fdv.R;
import com.najasoftware.fdv.activity.PedidoActivity;
import com.najasoftware.fdv.model.FormaPgto;
import com.najasoftware.fdv.model.Pedido;
import com.najasoftware.fdv.util.Util;

/**
 * Created by Lemoel Marques - NajaSoftware on 10/03/2016.
 * lemoel@gmail.com
 */
public class PedidoHelper {

    private TextView tvTotalPedidoSemDesconto;
    private TextView tvDesconto;
    private TextView tvTotalComDesconto;
    private Spinner comboFormaPgto;
    private TextView tvQtdeDeItens;
    private EditText etObs;

    public PedidoHelper(PedidoActivity pedidoActivity) {
        tvTotalPedidoSemDesconto = (TextView) pedidoActivity.findViewById(R.id.tvPedidoTotal);
        tvDesconto = (TextView) pedidoActivity.findViewById(R.id.tvPedidoDesconto);
        tvTotalComDesconto = (TextView) pedidoActivity.findViewById(R.id.tvPedidoComDesconto);
        comboFormaPgto = (Spinner) pedidoActivity.findViewById(R.id.comboFormaPgto);
        tvQtdeDeItens = (TextView) pedidoActivity.findViewById(R.id.tvPedidoTotalItens);
        comboFormaPgto.requestFocusFromTouch();
        etObs       =   (EditText) pedidoActivity.findViewById(R.id.etObs);
    }

    public void preencherActivity(Pedido pedido) {
        Util util = new Util();
        tvTotalPedidoSemDesconto.setText(util.formataMoeda(pedido.getTotalSemDesconto()));
        tvDesconto.setText(util.formataMoeda(pedido.getDesconto()));
        tvTotalComDesconto.setText(util.formataMoeda(pedido.getTotalComDesconto()));
        if (pedido.getItens() != null) {
            tvQtdeDeItens.setText(Integer.toString(pedido.getItens().size()));
        }
        etObs.setText(pedido.getObs());
    }

    public String validaFormulario() {

        FormaPgto formaPgto = (FormaPgto) comboFormaPgto.getSelectedItem();
        if (formaPgto.getId() == 0) {
            return "Escolha uma forma de pagamento";
        }
        return "ok";
    }

    public Pedido getPedido(){
        Pedido pedido = new Pedido();
        FormaPgto formaPgto = (FormaPgto)comboFormaPgto.getSelectedItem();
        pedido.setFormaPgto(formaPgto);
        pedido.setObs(etObs.getText().toString());
        return pedido;
    }
}
