package com.loyalty.source.lbsourcesvc.repository;

import com.loyalty.source.lbsourcesvc.pojo.Cliente;
import com.loyalty.source.lbsourcesvc.pojo.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {

    Cliente findByusuarioAndPass(String user, String pass);
}
