package com.lifebank.source.lbsourcesvc.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifebank.source.lbsourcesvc.repository.IClienteRepository;
import com.lifebank.source.lbsourcesvc.repository.IFavoritoRepository;
import com.lifebank.source.lbsourcesvc.repository.IProductoRepository;
import com.lifebank.source.lbsourcesvc.repository.ITransaccionRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class SourceProcess {
    @Autowired
    protected Environment env;
    @Autowired
    protected IProductoRepository productoRepository;
    @Autowired
    protected IFavoritoRepository favoritoRepository;
    @Autowired
    protected IClienteRepository clienteRepository;
    @Autowired
    protected ITransaccionRepository transaccionRepository;

    protected ObjectMapper mapper = new ObjectMapper();

    protected Logger log;


}
