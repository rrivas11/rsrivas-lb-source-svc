package com.lifebank.source.lbsourcesvc.parse;

import com.lifebank.source.lbsourcesvc.pojo.database.Producto;
import com.lifebank.source.lbsourcesvc.pojo.products.Accounts;
import com.lifebank.source.lbsourcesvc.pojo.products.GetProductResponse;
import com.lifebank.source.lbsourcesvc.pojo.products.Product;
import org.springframework.core.env.Environment;


import java.util.ArrayList;
import java.util.List;

public class ProductParser {
    private Environment env;

    public ProductParser(Environment env) {
        this.env = env;
    }

    public GetProductResponse parser(List<Producto> dbprd){
        GetProductResponse productResponse  = new GetProductResponse();
        Accounts accounts = new Accounts();
        List<Product> creditCardList= new ArrayList<>();
        List<Product> loanList= new ArrayList<>();
        List<Product> personalList= new ArrayList<>();
        Product prod;

        for(Producto p : dbprd){
            prod = new Product();
            prod.setId(p.getIdproducto());
            prod.setName(p.getNombre());

            if(p.getTipoProducto().getIdTipo() == Integer.valueOf(env.getProperty("appProperties.creditCard"))){
                creditCardList.add(prod);
            }else if(p.getTipoProducto().getIdTipo() == Integer.valueOf(env.getProperty("appProperties.loan"))){
                loanList.add(prod);
            }else {
                personalList.add(prod);
            }
        }
        accounts.setCreditCard(creditCardList);
        accounts.setLoan(loanList);
        accounts.setPersonal(personalList);

        productResponse.setAccounts(accounts);

        return productResponse;
    }

}
