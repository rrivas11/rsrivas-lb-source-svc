
package com.lifebank.source.lbsourcesvc.pojo.cliente;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "nombreBeneficiario",
    "cuentaBeneficiario",
    "tipo de la cuenta",
    "correoElectronico"
})
public class AddBeneficiaryRequest {

    @JsonProperty("nombreBeneficiario")
    private String nombreBeneficiario;
    @JsonProperty("cuentaBeneficiario")
    private String cuentaBeneficiario;
    @JsonProperty("tipo de la cuenta")
    private Integer tipoDeLaCuenta;
    @JsonProperty("correoElectronico")
    private String correoElectronico;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddBeneficiaryRequest() {
    }

    /**
     * 
     * @param correoElectronico
     * @param cuentaBeneficiario
     * @param nombreBeneficiario
     * @param tipoDeLaCuenta
     */
    public AddBeneficiaryRequest(String nombreBeneficiario, String cuentaBeneficiario, Integer tipoDeLaCuenta, String correoElectronico) {
        super();
        this.nombreBeneficiario = nombreBeneficiario;
        this.cuentaBeneficiario = cuentaBeneficiario;
        this.tipoDeLaCuenta = tipoDeLaCuenta;
        this.correoElectronico = correoElectronico;
    }

    @JsonProperty("nombreBeneficiario")
    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    @JsonProperty("nombreBeneficiario")
    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    @JsonProperty("cuentaBeneficiario")
    public String getCuentaBeneficiario() {
        return cuentaBeneficiario;
    }

    @JsonProperty("cuentaBeneficiario")
    public void setCuentaBeneficiario(String cuentaBeneficiario) {
        this.cuentaBeneficiario = cuentaBeneficiario;
    }

    @JsonProperty("tipo de la cuenta")
    public Integer getTipoDeLaCuenta() {
        return tipoDeLaCuenta;
    }

    @JsonProperty("tipo de la cuenta")
    public void setTipoDeLaCuenta(Integer tipoDeLaCuenta) {
        this.tipoDeLaCuenta = tipoDeLaCuenta;
    }

    @JsonProperty("correoElectronico")
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    @JsonProperty("correoElectronico")
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

}
