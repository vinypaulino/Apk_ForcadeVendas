package com.najasoftware.fdv.model;

import java.util.List;

/**
 * Created by Lemoel Marques - NajaSoftware on 07/03/2016.
 * lemoel@gmail.com
 */
@org.parceler.Parcel
public class Pedido {

    private Long id;
    private Cliente cliente;
    private Vendedor vendedor;
    private String obs;
    private int status;
    private String dataVenda;
    private String dataEnvioServidor;
    private Double totalSemDesconto;
    private Double totalComDesconto;
    private Double desconto;
    private List<Item> itens;
    private FormaPgto formaPgto;
    private String formaPgtoObs;

    public Pedido(Long id) {
        this.id = id;
    }

    public Pedido() {}

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    @Override
    public String toString() {
        return getId() + " - " + getObs();
    }

    public Double getTotalSemDesconto() {
        return totalSemDesconto;
    }

    public void setTotalSemDesconto(Double totalSemDesconto) {
        this.totalSemDesconto = totalSemDesconto;
    }

    public Double getTotalComDesconto() {
        return totalComDesconto;
    }

    public void setTotalComDesconto(Double totalComDesconto) {
        this.totalComDesconto = totalComDesconto;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public FormaPgto getFormaPgto() {
        return formaPgto;
    }

    public void setFormaPgto(FormaPgto formaPgto) {
        this.formaPgto = formaPgto;
    }

    public String getFormaPgtoObs() {
        return formaPgtoObs;
    }

    public void setFormaPgtoObs(String formaPgtoObs) {
        this.formaPgtoObs = formaPgtoObs;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getDataEnvioServidor() {
        return dataEnvioServidor;
    }

    public void setDataEnvioServidor(String dataEnvioServidor) {
        this.dataEnvioServidor = dataEnvioServidor;
    }
}
