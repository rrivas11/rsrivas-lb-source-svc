
package com.lifebank.source.lbsourcesvc.pojo.products;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "accounts"
})
public class GetProductResponse {

    @JsonProperty("accounts")
    private Accounts accounts;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetProductResponse() {
    }

    /**
     * 
     * @param accounts
     */
    public GetProductResponse(Accounts accounts) {
        super();
        this.accounts = accounts;
    }

    @JsonProperty("accounts")
    public Accounts getAccounts() {
        return accounts;
    }

    @JsonProperty("accounts")
    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

}
