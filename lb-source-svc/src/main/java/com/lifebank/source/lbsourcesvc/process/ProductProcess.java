package com.lifebank.source.lbsourcesvc.process;

import com.lifebank.source.lbsourcesvc.parse.ProductParser;
import com.lifebank.source.lbsourcesvc.pojo.common.ServiceMessage;
import com.lifebank.source.lbsourcesvc.pojo.common.Status;
import com.lifebank.source.lbsourcesvc.pojo.database.Cliente;
import com.lifebank.source.lbsourcesvc.pojo.database.Favorito;
import com.lifebank.source.lbsourcesvc.pojo.database.Producto;
import com.lifebank.source.lbsourcesvc.pojo.database.Transaccion;
import com.lifebank.source.lbsourcesvc.pojo.products.GetProductResponse;
import com.lifebank.source.lbsourcesvc.repository.IClienteRepository;
import com.lifebank.source.lbsourcesvc.repository.IFavoritoRepository;
import com.lifebank.source.lbsourcesvc.repository.IProductoRepository;
import com.lifebank.source.lbsourcesvc.repository.ITransaccionRepository;
import com.lifebank.source.lbsourcesvc.utility.RestClient;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductProcess extends SourceProcess {
    private Logger log;


    public ServiceMessage process(String user){
        Status status = new Status();
        ServiceMessage serviceMessage;
        ProductParser parser = new ProductParser(env);
        GetProductResponse response = new GetProductResponse();

        try{
            Cliente c = clienteRepository.findByusuario(user);
            List<Producto> listPrd = productoRepository.findByidpropietario(c.getIdcli());
            if(listPrd.size()>0){
                response = parser.parser(listPrd);
                status.setCode(env.getProperty("appProperties.code.c200"));
                serviceMessage = new ServiceMessage(status, response);
            }else{
                status.setCode(env.getProperty("appProperties.code.c400"));
                status.setMessage(env.getProperty("appProperties.messages.mjs4"));
                serviceMessage = new ServiceMessage(status, null);
            }

            return serviceMessage;
        }catch (Exception e){
            log.error("ProductProcess - Hubo un error en consulta a la base , en la línea {} en el método {}, detalle del error {}", e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName(), e);
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.mjs4"));
            serviceMessage = new ServiceMessage(status, null);
            return  serviceMessage;
        }

    }


}
