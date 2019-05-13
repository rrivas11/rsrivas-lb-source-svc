package com.lifebank.source.lbsourcesvc.pojo.login;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "tkn"
})
public class LoginResponse {

    @JsonProperty("tkn")
    private String tkn;

    /**
     * No args constructor for use in serialization
     *
     */
    public LoginResponse() {
    }

    /**
     *
     * @param tkn
     */
    public LoginResponse(String tkn) {
        super();
        this.tkn = tkn;
    }

    @JsonProperty("tkn")
    public String getTkn() {
        return tkn;
    }

    @JsonProperty("tkn")
    public void setTkn(String tkn) {
        this.tkn = tkn;
    }


}
