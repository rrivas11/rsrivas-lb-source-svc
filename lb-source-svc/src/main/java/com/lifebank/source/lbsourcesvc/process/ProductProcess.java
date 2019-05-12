package com.lifebank.source.lbsourcesvc.process;

import com.lifebank.source.lbsourcesvc.jwt.JwtHandler;
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
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductProcess extends SourceProcess {

    public ProductProcess() {
        this.log = LoggerFactory.getLogger(getClass());
    }

    public ResponseEntity<?> process(String token){
        Status status = new Status();
        ServiceMessage serviceMessage;
        ProductParser parser = new ProductParser(env);
        GetProductResponse response;
        JwtHandler jwtHandler = new JwtHandler(env);
        ResponseEntity responseEntity;


        try{

            int id_cliente = jwtHandler.validate(token);
            if(id_cliente <= 0){
                status.setMessage(jwtHandler.obtainMsj(id_cliente));
                serviceMessage = new ServiceMessage(status, null);
                return new ResponseEntity<>(serviceMessage, jwtHandler.obtainHttpCode(id_cliente));
            }

            Cliente c = clienteRepository.findByidcli(id_cliente);

            if(c == null){
                status.setCode(env.getProperty("appProperties.code.c400"));
                status.setMessage(env.getProperty("appProperties.messages.mjs4"));
                serviceMessage = new ServiceMessage(status, null);
                return new ResponseEntity<>(serviceMessage, HttpStatus.BAD_REQUEST);
            }

            List<Producto> listPrd = productoRepository.findByidpropietario(c.getIdcli());
            if(listPrd.size()>0){
                response = parser.parser(listPrd);
                status.setCode(env.getProperty("appProperties.code.c200"));
                serviceMessage = new ServiceMessage(status, response);
                responseEntity = new ResponseEntity <> (serviceMessage,HttpStatus.OK);
            }else{
                status.setCode(env.getProperty("appProperties.code.c400"));
                status.setMessage(env.getProperty("appProperties.messages.mjs4"));
                serviceMessage = new ServiceMessage(status, null);
                responseEntity = new ResponseEntity <> (serviceMessage,HttpStatus.BAD_REQUEST);

            }
            log.info("getProducts response:" + mapper.writeValueAsString(serviceMessage));
            return responseEntity;
        }catch (Exception e){
            log.error("ProductProcess - Hubo un error en consulta a la base , en la línea {} en el método {}, detalle del error {}", e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName(), e);
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.mjs4"));
            serviceMessage = new ServiceMessage(status, null);
            responseEntity = new ResponseEntity <> (serviceMessage,HttpStatus.BAD_REQUEST);
            return  responseEntity;
        }

    }


}
