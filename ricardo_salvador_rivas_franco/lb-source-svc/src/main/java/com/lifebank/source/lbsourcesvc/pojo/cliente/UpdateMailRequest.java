
package com.lifebank.source.lbsourcesvc.pojo.cliente;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "nuevoCorreo"
})
public class UpdateMailRequest {

    @JsonProperty("nuevoCorreo")
    private String nuevoCorreo;

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdateMailRequest() {
    }

    /**
     *
     * @param nuevoCorreo
     */
    public UpdateMailRequest(String nuevoCorreo) {
        super();
        this.nuevoCorreo = nuevoCorreo;
    }

    @JsonProperty("nuevoCorreo")
    public String getNuevoCorreo() {
        return nuevoCorreo;
    }

    @JsonProperty("nuevoCorreo")
    public void setNuevoCorreo(String nuevoCorreo) {
        this.nuevoCorreo = nuevoCorreo;
    }

}