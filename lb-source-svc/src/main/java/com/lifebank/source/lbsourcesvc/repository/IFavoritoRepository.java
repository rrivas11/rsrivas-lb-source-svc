package com.lifebank.source.lbsourcesvc.repository;

import com.lifebank.source.lbsourcesvc.pojo.database.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IFavoritoRepository extends JpaRepository<Favorito, Integer> {


    Favorito findByidclienteAndIdbene(int idcliente, int idbene);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE favorito fv SET fv.correo=(:correo) WHERE fv.idbene = :idbene AND fv.idcliente = :idcliente")
    @Transactional
    int updateEmail(@Param("correo") String correo, @Param("idcliente") int idcliente, @Param("idbene") int idbene);


}
