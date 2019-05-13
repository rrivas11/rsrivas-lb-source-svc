
package com.lifebank.source.lbsourcesvc.pojo.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tipoProducto",
    "origen",
    "destino",
    "monto"
})
public class SetTransactionRequest {

    @JsonProperty("tipoProducto")
    private Integer tipoProducto;
    @JsonProperty("origen")
    private String origen;
    @JsonProperty("destino")
    private String destino;
    @JsonProperty("beneficiario")
    private String idBeneficiario;
    @JsonProperty("monto")
    private Double monto;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SetTransactionRequest() {
    }

    /**
     * 
     * @param destino
     * @param tipoProducto
     * @param monto
     * @param origen
     */
    public SetTransactionRequest(Integer tipoProducto, String origen, String destino, Double monto) {
        super();
        this.tipoProducto = tipoProducto;
        this.origen = origen;
        this.destino = destino;
        this.monto = monto;
    }

    @JsonProperty("tipoProducto")
    public Integer getTipoProducto() {
        return tipoProducto;
    }

    @JsonProperty("tipoProducto")
    public void setTipoProducto(Integer tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    @JsonProperty("origen")
    public String getOrigen() {
        return origen;
    }

    @JsonProperty("origen")
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    @JsonProperty("destino")
    public String getDestino() {
        return destino;
    }

    @JsonProperty("destino")
    public void setDestino(String destino) {
        this.destino = destino;
    }

    @JsonProperty("monto")
    public Double getMonto() {
        return monto;
    }

    @JsonProperty("monto")
    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(String idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }
}
