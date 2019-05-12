package com.lifebank.source.lbsourcesvc.process;

import com.lifebank.source.lbsourcesvc.pojo.cliente.UpdateMailRequest;
import com.lifebank.source.lbsourcesvc.pojo.common.ServiceMessage;
import com.lifebank.source.lbsourcesvc.pojo.common.Status;
import com.lifebank.source.lbsourcesvc.pojo.database.Favorito;
import com.lifebank.source.lbsourcesvc.pojo.database.Producto;
import com.lifebank.source.lbsourcesvc.pojo.database.Transaccion;
import com.lifebank.source.lbsourcesvc.pojo.transaction.SetTransactionResponse;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BeneficiarioProcess extends SourceProcess {
    private Logger log;


    public  ResponseEntity<Object> process(UpdateMailRequest request, int id_cliente,String beneficiaryID) {
        Status status = new Status();
        ServiceMessage serviceMessage;
        Favorito favorito;

        favorito = favoritoRepository.findByidclienteAndIdbene(id_cliente,Integer.valueOf(beneficiaryID));
        if(favorito == null) {
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.mjs10"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.NOT_FOUND);
            //return serviceMessage;
        }
        int i = favoritoRepository.updateEmail(request.getNuevoCorreo(),id_cliente,Integer.valueOf(beneficiaryID));

        if(i <= 0 ){
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.mjs1"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);

    }
}
