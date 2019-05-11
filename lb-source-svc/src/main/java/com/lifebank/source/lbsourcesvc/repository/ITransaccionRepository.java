package com.lifebank.source.lbsourcesvc.repository;

import com.lifebank.source.lbsourcesvc.pojo.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransaccionRepository extends JpaRepository<Transaccion, Integer> {
}
