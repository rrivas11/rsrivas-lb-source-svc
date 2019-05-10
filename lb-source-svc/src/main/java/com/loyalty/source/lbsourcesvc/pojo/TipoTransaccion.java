package com.loyalty.source.lbsourcesvc.pojo;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name = "tipo_transacction")
public class TipoTransaccion {

    @Id
    @Column(name = "tip_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int tipo_id;

    @Column(name = "tip_descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "tipoTransaccion")
    @JsonIgnore
    private List<Transaccion> transaccions ;
}
