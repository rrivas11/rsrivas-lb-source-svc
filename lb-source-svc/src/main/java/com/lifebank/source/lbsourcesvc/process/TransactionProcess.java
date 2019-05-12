package com.lifebank.source.lbsourcesvc.process;

import com.lifebank.source.lbsourcesvc.pojo.common.ServiceMessage;
import com.lifebank.source.lbsourcesvc.pojo.common.Status;
import com.lifebank.source.lbsourcesvc.pojo.database.Favorito;
import com.lifebank.source.lbsourcesvc.pojo.database.Producto;
import com.lifebank.source.lbsourcesvc.pojo.database.Transaccion;
import com.lifebank.source.lbsourcesvc.pojo.transaction.SetTransactionRequest;
import com.lifebank.source.lbsourcesvc.pojo.transaction.SetTransactionResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class TransactionProcess extends SourceProcess {
    private Logger log;


    public ServiceMessage processP(SetTransactionRequest request,int id_cliente){
        Status status = new Status();
        ServiceMessage serviceMessage;
        Producto prdO = new Producto();
        Producto prdD = new Producto();
        Transaccion tra = new Transaccion();
        SetTransactionResponse response = new SetTransactionResponse();


        // Validar si se repite el numero de cuenta del origin con el del del destino
        if(request.getDestino().equalsIgnoreCase(request.getOrigen())){
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.mjs6"));
            serviceMessage = new ServiceMessage(status, null);
            return serviceMessage;
        }

        // Validar si existe las cuentas OJO
        try {
            prdO = productoRepository.findByidproducto(request.getOrigen());
            prdD = productoRepository.findByidproducto(request.getDestino());

        }catch (Exception e){
            log.error("Se produjo un error , en la línea {} en el método {}, detalle del error {}", e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName(), e);
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.mjs5"));
            serviceMessage = new ServiceMessage(status, null);
            return serviceMessage;
        }

        return executeTransaction(prdO,prdD,id_cliente,id_cliente,request);


    }


    public ServiceMessage processT(SetTransactionRequest request,int id_cliente){
        Status status = new Status();
        ServiceMessage serviceMessage;
        Producto prdO;
        Producto prdD = new Producto();
        Transaccion tra = new Transaccion();
        SetTransactionResponse response = new SetTransactionResponse();
        Favorito favorito;

        // validar numero de beneficiario este asoaciado al cliente y cual es la cuenta asociada.

        favorito = favoritoRepository.findByidclienteAndIdbene(id_cliente,Integer.valueOf(request.getIdBeneficiario()));

        if(favorito == null) {
                status.setCode(env.getProperty("appProperties.code.c400"));
                status.setMessage(env.getProperty("appProperties.messages.mjs10"));
                serviceMessage = new ServiceMessage(status, null);
                return serviceMessage;
         }
        // Validar  si la cuenta origen pertenece al cliente y obtenemos la informacion de la cuenta del beneficiario
        try {
            prdO = productoRepository.findByidproducto(request.getOrigen());
            prdD = productoRepository.findByidproducto(favorito.getProductoBen().getIdproducto()); // cambiar parametro de entrada.

        }catch (Exception e){
            log.error("Se produjo un error al consultar la base de datos, en la línea {} en el método {}, detalle del error {}", e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName(), e);
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.mjs5"));
            serviceMessage = new ServiceMessage(status, null);
            return serviceMessage;
        }

        if(prdD == null || prdO == null){
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.mjs5"));
            serviceMessage = new ServiceMessage(status, null);
            return serviceMessage;
        }


       return executeTransaction(prdO,prdD,id_cliente,Integer.valueOf(request.getIdBeneficiario()),request);

    }


    public ServiceMessage executeTransaction( Producto prdO,  Producto prdD,int id_cliente, int id_dest,SetTransactionRequest request ){

        Transaccion tra = new Transaccion();
        SetTransactionResponse response = new SetTransactionResponse();
        Status status = new Status();
        ServiceMessage serviceMessage;


        // Validar si las cuentas estan asoaciadas a su respectivo dueños.
        if (prdO.getCliente().getIdcli() == id_cliente
                && prdD.getCliente().getIdcli() == Integer.valueOf(id_dest)) {

            // Validar que sea del tipo de producto que solicito el cliente.
            if(prdD.getTipoProducto().getIdTipo() == request.getTipoProducto()
                    && prdO.getTipoProducto().getIdTipo() == 3){

                // Validar si el monto es suficiente
                if(prdO.getSaldo() > request.getMonto()){

                    try {
                        tra.setId_origen(request.getOrigen());
                        tra.setId_destino(prdD.getIdproducto());
                        tra.setMonto(request.getMonto());
                        tra.setFecha(LocalDateTime.now());
                        tra.setDescripcion("Tranferencia a: " + prdD.getIdproducto());
                        tra = transaccionRepository.save(tra);


                        response.setCodigoAutorizacion(String.valueOf(tra.getTra_id()));
                        status.setCode(env.getProperty("appProperties.code.c200"));
                        serviceMessage = new ServiceMessage(status, response);
                        return serviceMessage;

                    }catch (Exception e){ // se devuelve mensaje de error si la operacion no se pudo realizar.
                        status.setCode(env.getProperty("appProperties.code.c400"));
                        status.setMessage(env.getProperty("appProperties.messages.mjs1"));
                        serviceMessage = new ServiceMessage(status, null);
                        return serviceMessage;
                    }

                }else{
                    status.setCode(env.getProperty("appProperties.code.c400"));
                    status.setMessage(env.getProperty("appProperties.messages.mjs9"));
                    serviceMessage = new ServiceMessage(status, null);
                    return serviceMessage;
                }

            }else{
                status.setCode(env.getProperty("appProperties.code.c400"));
                status.setMessage(env.getProperty("appProperties.messages.mjs8"));
                serviceMessage = new ServiceMessage(status, null);
                return serviceMessage;
            }

        }else{
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.mjs7"));
            serviceMessage = new ServiceMessage(status, null);
            return serviceMessage;
        }

    }


}
