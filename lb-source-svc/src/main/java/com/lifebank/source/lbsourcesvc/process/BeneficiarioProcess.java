package com.lifebank.source.lbsourcesvc.process;

import com.lifebank.source.lbsourcesvc.pojo.cliente.AddBeneficiaryRequest;
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

import java.time.LocalDateTime;

@Service
public class BeneficiarioProcess extends SourceProcess {
    private Logger log;


    public  ResponseEntity<Object> updateProcess(UpdateMailRequest request, int id_cliente, String beneficiaryID) {
        Status status = new Status();
        ServiceMessage serviceMessage;
        Favorito favorito;

        favorito = favoritoRepository.findByidclienteAndIdbene(id_cliente,Integer.valueOf(beneficiaryID));
        if(favorito == null) {
            status.setCode(env.getProperty("appProperties.code.c404"));
            status.setMessage(env.getProperty("appProperties.messages.mjs10"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.NOT_FOUND);
            //return serviceMessage;
        }
        int i = favoritoRepository.updateEmail(request.getNuevoCorreo(),id_cliente,Integer.valueOf(beneficiaryID));

        if(i <= 0 ){
            status.setCode(env.getProperty("appProperties.code.c404"));
            status.setMessage(env.getProperty("appProperties.messages.mjs1"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

    }

    public  ResponseEntity<Object> deleteProcess( int id_cliente, String beneficiaryID) {
        Status status = new Status();
        ServiceMessage serviceMessage;
        Favorito favorito;

        favorito = favoritoRepository.findByidclienteAndIdbene(id_cliente,Integer.valueOf(beneficiaryID));
        if(favorito == null) {
            status.setCode(env.getProperty("appProperties.code.c404"));
            status.setMessage(env.getProperty("appProperties.messages.mjs10"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.NOT_FOUND);
            //return serviceMessage;
        }

        try {
            favoritoRepository.delete(favorito);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        }catch (Exception e){
            status.setCode(env.getProperty("appProperties.code.c404"));
            status.setMessage(env.getProperty("appProperties.messages.mjs1"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.NOT_FOUND);
        }
    }

    public  ResponseEntity<Object> addProcess(AddBeneficiaryRequest request, int id_cliente) {
        Status status = new Status();
        ServiceMessage serviceMessage;
        Favorito favorito;

        Producto prd = productoRepository.findByidproducto(request.getCuentaBeneficiario());
        if(prd == null ){
            status.setCode(env.getProperty("appProperties.code.c404"));
            status.setMessage(env.getProperty("appProperties.messages.mjs4"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.NOT_FOUND);
        }

        if ((prd.getTipoProducto().getIdTipo() != request.getTipoDeLaCuenta()) ){
            status.setCode(env.getProperty("appProperties.code.c404"));
            status.setMessage(env.getProperty("appProperties.messages.mjs8"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.NOT_FOUND);
        }


        favorito = favoritoRepository.findByidclienteAndIdbene(id_cliente,prd.getIdpropietario());
        if(favorito != null) {
            status.setCode(env.getProperty("appProperties.code.c409"));
            status.setMessage(env.getProperty("appProperties.messages.mjs11"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.CONFLICT);
            //return serviceMessage;
        }
        favorito = new Favorito();
        favorito.setIdcliente(id_cliente);
        favorito.setIdbene(prd.getIdpropietario());
        favorito.setIdprod(prd.getIdproducto());
        favorito.setFecha(LocalDateTime.now());
        favorito.setNombre(request.getNombreBeneficiario());
        favorito.setCorreo(request.getCorreoElectronico());

        Favorito f;

       f = favoritoRepository.save(favorito);

        if(f == null ){
            status.setCode(env.getProperty("appProperties.code.c404"));
            status.setMessage(env.getProperty("appProperties.messages.mjs1"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.CREATED);

    }


}
