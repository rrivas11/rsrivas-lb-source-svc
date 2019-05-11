package com.lifebank.source.lbsourcesvc.pojo.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "codigo_autorizacion"
})
public class SetTransactionResponse {

    @JsonProperty("codigo_autorizacion")
    private String codigoAutorizacion;

    /**
     * No args constructor for use in serialization
     *
     */
    public SetTransactionResponse() {
    }

    /**
     *
     * @param codigoAutorizacion
     */
    public SetTransactionResponse(String codigoAutorizacion) {
        super();
        this.codigoAutorizacion = codigoAutorizacion;
    }

    @JsonProperty("codigo_autorizacion")
    public String getCodigoAutorizacion() {
        return codigoAutorizacion;
    }

    @JsonProperty("codigo_autorizacion")
    public void setCodigoAutorizacion(String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }

}