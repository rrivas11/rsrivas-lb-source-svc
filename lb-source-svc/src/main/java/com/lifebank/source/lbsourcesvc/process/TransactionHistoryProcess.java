package com.lifebank.source.lbsourcesvc.process;

import com.lifebank.source.lbsourcesvc.jwt.JwtHandler;
import com.lifebank.source.lbsourcesvc.parse.TransactionParser;
import com.lifebank.source.lbsourcesvc.pojo.common.ServiceMessage;
import com.lifebank.source.lbsourcesvc.pojo.common.Status;
import com.lifebank.source.lbsourcesvc.pojo.database.Producto;
import com.lifebank.source.lbsourcesvc.pojo.database.Transaccion;
import com.lifebank.source.lbsourcesvc.pojo.transaction.GetTransactionRequest;
import com.lifebank.source.lbsourcesvc.pojo.transaction.GetTransactionResponse;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionHistoryProcess extends SourceProcess {
    private Logger log;


    public ResponseEntity<?> process(String id_cuenta, String start, String end,String token){
        Status status = new Status();
        ServiceMessage serviceMessage;
        TransactionParser parser = new TransactionParser(env);
        List<Transaccion> transaccionList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        GetTransactionResponse response;
        GetTransactionRequest req = new GetTransactionRequest();
        JwtHandler jwtHandler = new JwtHandler(env);
        ResponseEntity responseEntity;

        LocalDateTime startDT = LocalDateTime.parse(start+" 00:00", formatter);
        LocalDateTime endDT = LocalDateTime.parse(end+ " 23:59", formatter);
        LocalDateTime startAux = startDT.plusMonths(3); // Se le suman 3 meses la fecha Inicio para validacion de Rango Maximo

        int id_cliente = jwtHandler.validate(token);
        if(id_cliente <= 0){
            status.setMessage(jwtHandler.obtainMsj(id_cliente));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, jwtHandler.obtainHttpCode(id_cliente));
        }
        //Validaciones de fecha Inicio no sea mayor a la fecha final
        if(startDT.isAfter(endDT)){
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.msj16"));
            serviceMessage = new ServiceMessage(status, null);
            responseEntity = new ResponseEntity <> (serviceMessage, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }

        //Se valida que el rango no sea mayor a 3 meses
        if(startAux.isBefore(endDT)){
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.msj17"));
            serviceMessage = new ServiceMessage(status, null);
            responseEntity = new ResponseEntity <> (serviceMessage, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }


        try{
            Producto prd = productoRepository.findByidproductoAndIdpropietario(id_cuenta,id_cliente);
            if(prd == null){
                status.setCode(env.getProperty("appProperties.code.c404"));
                status.setMessage(env.getProperty("appProperties.messages.mjs15"));
                serviceMessage = new ServiceMessage(status, null);
                responseEntity = new ResponseEntity <> (serviceMessage, HttpStatus.NOT_FOUND);
                return responseEntity;
            }

            transaccionList = transaccionRepository.obtenerTransactionO(startDT,endDT,id_cuenta);
            req.setCliente(String.valueOf(prd.getCliente().getIdcli()));
            req.setEndDate(end);
            req.setStartDate(start);
            response = parser.parser(prd,transaccionList,req);
            status.setCode(env.getProperty("appProperties.code.c200"));
            serviceMessage = new ServiceMessage(status, response);
            responseEntity = new ResponseEntity <> (serviceMessage, HttpStatus.OK);

            return responseEntity;
        }catch (Exception e){
            log.error("TransactionHistoryProcess - Hubo un error en consulta a la base , en la línea {} en el método {}, detalle del error {}", e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName(), e);
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.mjs4"));
            serviceMessage = new ServiceMessage(status, null);
            responseEntity = new ResponseEntity <> (serviceMessage, HttpStatus.BAD_REQUEST);

            return  responseEntity;
        }

    }

}
