package com.lifebank.source.lbsourcesvc.repository;

import com.lifebank.source.lbsourcesvc.pojo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {

    Cliente findByusuarioAndPass(String user, String pass);
}
