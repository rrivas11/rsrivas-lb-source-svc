package com.loyalty.source.lbsourcesvc.pojo;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name = "tipoproducto")
public class TipoProducto {
    @Id
    @Column(name = "tipo_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  int idTipo;

    @Column(name = "tipo_nombre")
    private  String tipo_nombre;

    @OneToMany(mappedBy = "Tipoproducto")
    @JsonIgnore
    private List<Producto> productos;

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getTipo_nombre() {
        return tipo_nombre;
    }

    public void setTipo_nombre(String tipo_nombre) {
        this.tipo_nombre = tipo_nombre;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
