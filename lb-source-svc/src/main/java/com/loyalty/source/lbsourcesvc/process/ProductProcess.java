package com.loyalty.source.lbsourcesvc.process;

import com.loyalty.source.lbsourcesvc.repository.IProductoRepository;
import com.loyalty.source.lbsourcesvc.utility.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ProductProcess {
    @Autowired
    private Environment env;
    @Autowired
    private RestClient restClient;

    private IProductoRepository productoResposity;

    private Logger log;


    public Object process(String idCliente){

        return productoResposity.findByidpropietario(idCliente);
    }


}
