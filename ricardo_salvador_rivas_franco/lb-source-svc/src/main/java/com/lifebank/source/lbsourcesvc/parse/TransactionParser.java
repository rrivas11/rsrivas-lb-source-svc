package com.lifebank.source.lbsourcesvc.parse;

import com.lifebank.source.lbsourcesvc.pojo.database.Producto;
import com.lifebank.source.lbsourcesvc.pojo.database.Transaccion;
import com.lifebank.source.lbsourcesvc.pojo.transaction.GetTransactionRequest;
import com.lifebank.source.lbsourcesvc.pojo.transaction.GetTransactionResponse;
import com.lifebank.source.lbsourcesvc.pojo.transaction.Transaction;
import org.springframework.core.env.Environment;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionParser {
    private Environment env;

    public TransactionParser(Environment env) {
        this.env = env;
    }

    public GetTransactionResponse parser(Producto prd, List<Transaccion> traList, GetTransactionRequest req ){
        GetTransactionResponse getTransactionResponse = new GetTransactionResponse();
         getTransactionResponse.setId(req.getCliente());
         getTransactionResponse.setStartDate(req.getStartDate());
         getTransactionResponse.setEndDate(req.getEndDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        Transaction transaction;
         List<Transaction> transactionsList = new ArrayList<>();

        if(prd.getTipoProducto().getIdTipo() == Integer.valueOf(env.getProperty("appProperties.creditCard"))){
            getTransactionResponse.setLimit(prd.getMonto());
            getTransactionResponse.setAvailable(prd.getSaldo());
            getTransactionResponse.setInterestAmount(prd.getInteresAcumulado());
            getTransactionResponse.setInterestRate(prd.getTasa());
            getTransactionResponse.setMonthlyCut(prd.getCorte());
        }else if(prd.getTipoProducto().getIdTipo() == Integer.valueOf(env.getProperty("appProperties.loan"))){
            getTransactionResponse.setTotal(prd.getMonto());
            getTransactionResponse.setDebt(prd.getSaldo());
            getTransactionResponse.setInterestRate(prd.getTasa());
            getTransactionResponse.setInterestAmount(prd.getInteresAcumulado());
        }

        for(Transaccion t : traList){
            transaction= new Transaction();
            transaction.setId(String.valueOf(t.getTra_id()));
            transaction.setDate(t.getFecha().format(formatter));
            transaction.setDescription(t.getDescripcion());

            if(t.getId_origen().equalsIgnoreCase(prd.getIdproducto())){
                transaction.setAmount((t.getMonto()*-1));
            }else{
                transaction.setAmount(t.getMonto());
            }
            transactionsList.add(transaction);
        }
        getTransactionResponse.setTransactions(transactionsList);
        return getTransactionResponse;
    }
}
