package com.loyalty.source.lbsourcesvc.repository;

import com.loyalty.source.lbsourcesvc.pojo.Favorito;
import com.loyalty.source.lbsourcesvc.pojo.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransaccionRepository extends JpaRepository<Transaccion, Integer> {
}
