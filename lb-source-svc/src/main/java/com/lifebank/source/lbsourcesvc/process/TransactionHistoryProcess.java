package com.lifebank.source.lbsourcesvc.process;

import com.lifebank.source.lbsourcesvc.parse.TransactionParser;
import com.lifebank.source.lbsourcesvc.pojo.common.ServiceMessage;
import com.lifebank.source.lbsourcesvc.pojo.common.Status;
import com.lifebank.source.lbsourcesvc.pojo.database.Producto;
import com.lifebank.source.lbsourcesvc.pojo.database.Transaccion;
import com.lifebank.source.lbsourcesvc.pojo.transaction.GetTransactionRequest;
import com.lifebank.source.lbsourcesvc.pojo.transaction.GetTransactionResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionHistoryProcess extends SourceProcess {
    private Logger log;


    public ServiceMessage process(String id_cuenta, String start, String end){
        Status status = new Status();
        ServiceMessage serviceMessage;
        TransactionParser parser = new TransactionParser(env);
        List<Transaccion> transaccionList = new ArrayList<>();
        List<Transaccion> transaccionListD = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        GetTransactionResponse response;
        GetTransactionRequest req = new GetTransactionRequest();

        LocalDateTime startDT = LocalDateTime.parse(start+" 00:00", formatter);
        LocalDateTime endDT = LocalDateTime.parse(end+ " 23:59", formatter);


        try{
            Producto prd = productoRepository.findByidproducto(id_cuenta);
            transaccionList = transaccionRepository.obtenerTransactionO(startDT,endDT,id_cuenta);

            req.setCliente(String.valueOf(prd.getCliente().getIdcli()));
            req.setEndDate(end);
            req.setStartDate(start);
            response = parser.parser(prd,transaccionList,req);
            status.setCode(env.getProperty("appProperties.code.c200"));
            serviceMessage = new ServiceMessage(status, response);
            return serviceMessage;
        }catch (Exception e){
            log.error("TransactionHistoryProcess - Hubo un error en consulta a la base , en la línea {} en el método {}, detalle del error {}", e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName(), e);
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.mjs4"));
            serviceMessage = new ServiceMessage(status, null);
            return  serviceMessage;
        }

    }

}
