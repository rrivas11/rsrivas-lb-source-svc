
package com.lifebank.source.lbsourcesvc.pojo.products;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "loan",
    "creditCard",
    "personal"
})
public class Accounts {

    @JsonProperty("loan")
    private List<Product> loan = null;
    @JsonProperty("creditCard")
    private List<Product> creditCard = null;
    @JsonProperty("personal")
    private List<Product> personal = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Accounts() {
    }

    /**
     * 
     * @param creditCard
     * @param personal
     * @param loan
     */
    public Accounts(List<Product> loan, List<Product> creditCard, List<Product> personal) {
        super();
        this.loan = loan;
        this.creditCard = creditCard;
        this.personal = personal;
    }

    @JsonProperty("loan")
    public List<Product> getLoan() {
        return loan;
    }

    @JsonProperty("loan")
    public void setLoan(List<Product> loan) {
        this.loan = loan;
    }

    @JsonProperty("creditCard")
    public List<Product> getCreditCard() {
        return creditCard;
    }

    @JsonProperty("creditCard")
    public void setCreditCard(List<Product> creditCard) {
        this.creditCard = creditCard;
    }

    @JsonProperty("personal")
    public List<Product> getPersonal() {
        return personal;
    }

    @JsonProperty("personal")
    public void setPersonal(List<Product> personal) {
        this.personal = personal;
    }

}
