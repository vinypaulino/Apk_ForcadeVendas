package com.najasoftware.fdv.model;

/**
 * Created by Lemoel Marques - NajaSoftware on 31/03/2016.
 * lemoel@gmail.com
 */
public enum ClienteStatusFinanceiroEnum {

    EMDIA(1),
    DEVEDOR(2),
    BLOQUEADO(3);

    public int statusFinanceiro;

    ClienteStatusFinanceiroEnum(int i) {
        statusFinanceiro = i;
    }

    public int getStatus(){
        return statusFinanceiro;
    }
}

