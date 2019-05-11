package com.loyalty.source.lbsourcesvc.repository;

import com.loyalty.source.lbsourcesvc.pojo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRepository extends JpaRepository<Producto, Integer> {

    Producto findByidpropietario(String id_cliente);

}
