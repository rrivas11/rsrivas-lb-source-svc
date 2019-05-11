package com.loyalty.source.lbsourcesvc.process;

import com.loyalty.source.lbsourcesvc.pojo.Cliente;
import com.loyalty.source.lbsourcesvc.pojo.Favorito;
import com.loyalty.source.lbsourcesvc.pojo.Producto;
import com.loyalty.source.lbsourcesvc.pojo.Transaccion;
import com.loyalty.source.lbsourcesvc.repository.IClienteRepository;
import com.loyalty.source.lbsourcesvc.repository.IFavoritoRepository;
import com.loyalty.source.lbsourcesvc.repository.IProductoRepository;
import com.loyalty.source.lbsourcesvc.repository.ITransaccionRepository;
import com.loyalty.source.lbsourcesvc.utility.RestClient;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductProcess {
    @Autowired
    private Environment env;
    @Autowired
    private RestClient restClient;
    @Autowired
    private IProductoRepository productoRepository;
    @Autowired
    private IFavoritoRepository favoritoRepository;
    @Autowired
    private IClienteRepository clienteRepository;
    @Autowired
    private ITransaccionRepository transaccionRepository;

    private Logger log;


    public Object process(int idCliente){

        Favorito fav = new Favorito();


        fav.setIdcliente(1);
        fav.setIdbene(2);
        fav.setIdprod("0777773");
        fav.setFecha(LocalDateTime.now());
        fav.setCorreo("eperez@hotmail.com");

        favoritoRepository.save(fav);


        Transaccion tra = new Transaccion();

        tra.setId_origen("0888882");
        tra.setId_destino("0888883");
        tra.setMonto(145.34);
        tra.setFecha(LocalDateTime.now());
        tra.setDescripcion("Abono");

        transaccionRepository.save(tra);


        Cliente c =  clienteRepository.findByusuarioAndPass("rrivas","123");
        Producto prd = productoRepository.findByidproducto("0888882");
        List<Producto> listPrd = productoRepository.findByidpropietario(idCliente);
        Favorito favorito = favoritoRepository.findByidclienteAndIdbene(1,2);
        int i = favoritoRepository.updateEmail("correo@yahoo.com",1,2);
        favoritoRepository.delete(favorito);



        return listPrd.get(0);
    }


}
