package com.lifebank.source.lbsourcesvc.repository;

import com.lifebank.source.lbsourcesvc.pojo.database.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ITransaccionRepository extends JpaRepository<Transaccion, Integer> {


    @Query(value = "select t from transaccion t where (t.fecha between (:start) and (:end)) and ( (t.id_origen = :id) OR  (t.id_destino = :id)) order by t.fecha DESC")
    public List<Transaccion> obtenerTransactionO(@Param("start") LocalDateTime star, @Param("end") LocalDateTime end , @Param("id") String id);


}
