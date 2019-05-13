
package com.lifebank.source.lbsourcesvc.pojo.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "date",
    "description",
    "amount"
})
public class Transaction {

    @JsonProperty("id")
    private String id;
    @JsonProperty("date")
    private String date;
    @JsonProperty("description")
    private String description;
    @JsonProperty("amount")
    private Double amount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Transaction() {
    }

    /**
     * 
     * @param amount
     * @param id
     * @param description
     * @param date
     */
    public Transaction(String id, String date, String description, Double amount) {
        super();
        this.id = id;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("amount")
    public Double getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
