
package com.lifebank.source.lbsourcesvc.pojo.transaction;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "startDate",
    "endDate",
    "limit",
    "available",
    "interestRate",
    "interestAmount",
    "MonthlyCut",
    "total",
    "debt",
    "transactions"
})
public class GetTransactionResponse {

    @JsonProperty("id")
    private String id;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("limit")
    private Double limit;
    @JsonProperty("available")
    private Double available;
    @JsonProperty("interestRate")
    private Double interestRate;
    @JsonProperty("interestAmount")
    private Double interestAmount;
    @JsonProperty("MonthlyCut")
    private Integer monthlyCut;
    @JsonProperty("total")
    private Double total;
    @JsonProperty("debt")
    private Double debt;
    @JsonProperty("transactions")
    private List<Transaction> transactions = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetTransactionResponse() {
    }

    /**
     * 
     * @param total
     * @param limit
     * @param id
     * @param transactions
     * @param startDate
     * @param debt
     * @param interestAmount
     * @param endDate
     * @param available
     * @param monthlyCut
     * @param interestRate
     */
    public GetTransactionResponse(String id, String startDate, String endDate, Double limit, Double available, Double interestRate, Double interestAmount, Integer monthlyCut, Double total, Double debt, List<Transaction> transactions) {
        super();
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.limit = limit;
        this.available = available;
        this.interestRate = interestRate;
        this.interestAmount = interestAmount;
        this.monthlyCut = monthlyCut;
        this.total = total;
        this.debt = debt;
        this.transactions = transactions;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("startDate")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("startDate")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("endDate")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("endDate")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("limit")
    public Double getLimit() {
        return limit;
    }

    @JsonProperty("limit")
    public void setLimit(Double limit) {
        this.limit = limit;
    }

    @JsonProperty("available")
    public Double getAvailable() {
        return available;
    }

    @JsonProperty("available")
    public void setAvailable(Double available) {
        this.available = available;
    }

    @JsonProperty("interestRate")
    public Double getInterestRate() {
        return interestRate;
    }

    @JsonProperty("interestRate")
    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    @JsonProperty("interestAmount")
    public Double getInterestAmount() {
        return interestAmount;
    }

    @JsonProperty("interestAmount")
    public void setInterestAmount(Double interestAmount) {
        this.interestAmount = interestAmount;
    }

    @JsonProperty("MonthlyCut")
    public Integer getMonthlyCut() {
        return monthlyCut;
    }

    @JsonProperty("MonthlyCut")
    public void setMonthlyCut(Integer monthlyCut) {
        this.monthlyCut = monthlyCut;
    }

    @JsonProperty("total")
    public Double getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Double total) {
        this.total = total;
    }

    @JsonProperty("debt")
    public Double getDebt() {
        return debt;
    }

    @JsonProperty("debt")
    public void setDebt(Double debt) {
        this.debt = debt;
    }

    @JsonProperty("transactions")
    public List<Transaction> getTransactions() {
        return transactions;
    }

    @JsonProperty("transactions")
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
