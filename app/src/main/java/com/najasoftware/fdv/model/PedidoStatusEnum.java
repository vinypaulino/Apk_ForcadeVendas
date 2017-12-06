package com.najasoftware.fdv.model;

/**
 * Created by Lemoel Marques - NajaSoftware on 22/03/2016.
 * lemoel@gmail.com
 */
public enum PedidoStatusEnum {

    AGUARDANDO_ENVIO(1),
    ENVIADO(2);

    public int status;

    PedidoStatusEnum(int i) {
        status = i;
    }

    public int getStatus(){
        return status;
    }
}
