package com.najasoftware.fdv.helper;

import android.widget.EditText;
import android.widget.TextView;

import com.najasoftware.fdv.R;
import com.najasoftware.fdv.activity.AddItemPedidoActivity;
import com.najasoftware.fdv.model.Item;
import com.najasoftware.fdv.util.Util;

/**
 * Created by Lemoel Marques - NajaSoftware on 07/03/2016.
 * lemoel@gmail.com
 */
public class AddItemPedidoHelper {

    private TextView campoDescricaoProduto;
    private TextView campoPreco;
    private TextView campoTotal;
    private TextView campoTotalSemDesconto;
    private EditText campoQuantidade;
    private TextView campoDesconto;
    private TextView campoDescontoPorcentagem;
    private Item item;
    private Util util;


    public AddItemPedidoHelper(AddItemPedidoActivity activity) {
        campoDescricaoProduto = (TextView) activity.findViewById(R.id.tDesc);
        campoPreco = (TextView) activity.findViewById(R.id.tPreco);
        campoTotal = (TextView) activity.findViewById(R.id.tvTotalGeral);
        campoTotalSemDesconto = (TextView) activity.findViewById(R.id.tvTotalSemDesconto);
        campoQuantidade = (EditText) activity.findViewById(R.id.etQuantidade);
        campoDesconto = (EditText) activity.findViewById(R.id.etDesconto);
        campoDescontoPorcentagem = (EditText) activity.findViewById(R.id.etDescontoPorcentagem);
        util = new Util();
    }

    public void preencherActivity(Item item) {

        campoDescricaoProduto.setText(item.getProduto().getDescricao());
        campoPreco.setText(item.getProduto().getPreco().toString());

        if (item.getTotalComDesconto() != null) {
            campoTotal.setText(item.getTotalComDesconto().toString());
        }

        if (item.getTotalSemDesconto() != null) {
            campoTotalSemDesconto.setText(item.getTotalSemDesconto().toString());
        }

        if (item.getDesconto() != null) {
            campoDesconto.setText(item.getDesconto().toString());
        }

        if (item.getDescontoPorcentagem() != null){
            campoDescontoPorcentagem.setText(item.getDescontoPorcentagem().toString());
        }

        if(item.getQtde() != null) {
            campoQuantidade.setText(item.getQtde().toString());
        }

        this.item = item;
    }

    public Item getItem() {

        this.item.setQtde(Double.parseDouble(campoQuantidade.getText().toString().trim()));
        this.item.setPrecoSugerido(Double.parseDouble(campoPreco.getText().toString().trim()));
        this.item.setTotalSemDesconto(Double.parseDouble(campoTotalSemDesconto.getText().toString().trim()));
        this.item.setTotalComDesconto(Double.parseDouble(campoTotal.getText().toString().trim()));

        if (campoDesconto.getText().toString().equals("")) {
            this.item.setDesconto(Double.parseDouble("0"));
            this.item.setDescontoPorcentagem(Double.parseDouble("0"));
        } else {
            this.item.setDesconto(Double.parseDouble(util.aproximar(campoDesconto.getText().toString().trim())));
            this.item.setDescontoPorcentagem(Double.parseDouble(util.aproximar(campoDescontoPorcentagem.getText().toString().trim())));
        }
        return this.item;
    }

    public String validaCampos() {
        String qtde = campoQuantidade.getText().toString().trim();
        String totalComDesconto = campoTotal.getText().toString().trim();

        if (qtde.equals("") || qtde.equals("0")) {
            return "Quantidade vazio ou zero";
        } else if (Double.parseDouble(totalComDesconto) < 0) {
            return "Total negativo";
        }
        return "ok";
    }

    public void recalculaChangeQtde(String qtde) {

        if (qtde.equals("")) {
            campoTotalSemDesconto.setText("0");
            campoTotal.setText("0");
        } else {
            String sDesconto = campoDesconto.getText().toString();
            Double preco = Double.parseDouble(campoPreco.getText().toString());
            Double quantidade = Double.parseDouble(qtde);
            Double totalSemDesconto = Double.parseDouble(util.aproximar(quantidade * preco));

            campoTotalSemDesconto.setText(totalSemDesconto.toString());

            if (!sDesconto.equals("")) {
                Double desconto = Double.parseDouble(sDesconto);
                Double totalComDesconto = Double.parseDouble(util.aproximar(totalSemDesconto - desconto));
                campoTotal.setText(totalComDesconto.toString());
            } else {
                campoTotal.setText(totalSemDesconto.toString());
            }
        }
    }

    public void recalculaChangeDesconto(String desconto) {

        String sQtde = campoQuantidade.getText().toString();


        if (sQtde.equals("")) {
            campoTotal.setText("0");
            campoTotalSemDesconto.setText("0");
        } else {
            //Total
            Double qtde = Double.parseDouble(sQtde);
            Double preco = Double.parseDouble(campoPreco.getText().toString());
            Double totalSemDesconto = Double.parseDouble(util.aproximar(qtde * preco));


            //Total com desconto
            if (!desconto.equals("")) {
                Double totalComDesconto = Double.parseDouble(util.aproximar(totalSemDesconto - Double.parseDouble(desconto)));
                Double porcentagem = (Double.parseDouble(desconto) * 100 / totalSemDesconto );
                campoDescontoPorcentagem.setText(porcentagem.toString());
                campoTotalSemDesconto.setText(totalSemDesconto.toString());
                campoTotal.setText(totalComDesconto.toString());
            } else {
                campoTotalSemDesconto.setText(totalSemDesconto.toString());
                campoTotal.setText(totalSemDesconto.toString());
            }
        }
    }

    public void recalculaChangeDescontoPorcentagem(String descontoPorcentagem) {
        String sQtde1 = campoQuantidade.getText().toString();


        if (sQtde1.equals("")) {
            campoTotal.setText("0");
            campoTotalSemDesconto.setText("0");
        } else {
            //Total
            Double qtde1 = Double.parseDouble(sQtde1);
            Double preco = Double.parseDouble(campoPreco.getText().toString());
            Double totalSemDesconto = Double.parseDouble(util.aproximar(qtde1 * preco));


            //Total com desconto
            if (!descontoPorcentagem.equals("")) {

                Double valorDesconto = (Double.parseDouble(String.valueOf(totalSemDesconto)) * Double.parseDouble(descontoPorcentagem)/ 100);
                Double totalComDesconto = Double.parseDouble(util.aproximar(totalSemDesconto - valorDesconto));

                campoDesconto.setText(valorDesconto.toString());
                campoTotalSemDesconto.setText(totalSemDesconto.toString());
                campoTotal.setText(totalComDesconto.toString());
            } else {
                campoTotalSemDesconto.setText(totalSemDesconto.toString());
                campoTotal.setText(totalSemDesconto.toString());
            }
        }

    }
}