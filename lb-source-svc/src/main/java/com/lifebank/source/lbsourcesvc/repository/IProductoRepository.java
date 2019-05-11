package com.lifebank.source.lbsourcesvc.repository;

import com.lifebank.source.lbsourcesvc.pojo.database.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductoRepository extends JpaRepository<Producto, Integer> {


    //  ver los productos de un clientes
    List<Producto> findByidpropietario(int id_cliente);

    // comprobar si la cuenta existe
    // obtener el monto de esa cuenta.
    Producto findByidproducto(String id_prod);

}
