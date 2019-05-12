package com.lifebank.source.lbsourcesvc.process;

import com.lifebank.source.lbsourcesvc.jwt.JwtHandler;
import com.lifebank.source.lbsourcesvc.pojo.cliente.AddBeneficiaryRequest;
import com.lifebank.source.lbsourcesvc.pojo.cliente.UpdateMailRequest;
import com.lifebank.source.lbsourcesvc.pojo.common.ServiceMessage;
import com.lifebank.source.lbsourcesvc.pojo.common.Status;
import com.lifebank.source.lbsourcesvc.pojo.database.Favorito;
import com.lifebank.source.lbsourcesvc.pojo.database.Producto;
import com.lifebank.source.lbsourcesvc.pojo.database.Transaccion;
import com.lifebank.source.lbsourcesvc.pojo.transaction.SetTransactionResponse;
import com.lifebank.source.lbsourcesvc.utility.MDCHandler;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.time.LocalDateTime;

@Service
public class BeneficiarioProcess extends SourceProcess {
    private Logger log;

    public BeneficiarioProcess() {
        this.log = LoggerFactory.getLogger(getClass());

    }

    public  ResponseEntity<Object> updateProcess(UpdateMailRequest request, String token, String beneficiaryID) {
        Status status = new Status();
        ServiceMessage serviceMessage;
        Favorito favorito;
        JwtHandler jwtHandler = new JwtHandler(env);
        String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@"+"[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
        Pattern pattern = Pattern.compile(emailPattern);

        //Validar si el correo no viene vacio
        if(request.getNuevoCorreo() == null){
            log.error("Correo Vacio");
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.msj19"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.BAD_REQUEST);
        }

        //Validar si el correo es valido.
        Matcher matcher = pattern.matcher(request.getNuevoCorreo());
        if (!matcher.matches()) {
            log.error("Correo no valido");
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.msj19"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.BAD_REQUEST);
        }


        int id_cliente = jwtHandler.validate(token);
        if(id_cliente <= 0){
            status.setMessage(jwtHandler.obtainMsj(id_cliente));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, jwtHandler.obtainHttpCode(id_cliente));
        }
        MDC.put("cliente", id_cliente);


        favorito = favoritoRepository.findByidclienteAndIdbene(id_cliente,Integer.valueOf(beneficiaryID));
        if(favorito == null) {
            log.error("Beneficiario no agregado.");
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

        log.info("Correo electronico del beneficiario se actualizo correctamente: "+ request.getNuevoCorreo());
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

    }

    public  ResponseEntity<Object> deleteProcess( String token, String beneficiaryID) {
        Status status = new Status();
        ServiceMessage serviceMessage;
        Favorito favorito;
        JwtHandler jwtHandler = new JwtHandler(env);

        int id_cliente = jwtHandler.validate(token);
        if(id_cliente <= 0){
            status.setMessage(jwtHandler.obtainMsj(id_cliente));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, jwtHandler.obtainHttpCode(id_cliente));
        }
        MDC.put("cliente", id_cliente);


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
            log.info("Beneficiario eliminado"+ favorito.getIdbene());
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        }catch (Exception e){
            status.setCode(env.getProperty("appProperties.code.c404"));
            status.setMessage(env.getProperty("appProperties.messages.mjs1"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.NOT_FOUND);
        }
    }

    public  ResponseEntity<Object> addProcess(AddBeneficiaryRequest request, String token) {
        Status status = new Status();
        ServiceMessage serviceMessage;
        Favorito favorito;

        String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@"+"[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
        Pattern pattern = Pattern.compile(emailPattern);

        //Validar si el correo no viene vacio
        if(request.getCorreoElectronico() == null){
            log.error("Correo Vacio");
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.msj19"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.BAD_REQUEST);
        }

        //Validar si el correo es valido.
        Matcher matcher = pattern.matcher(request.getCorreoElectronico());
        if (!matcher.matches()) {
            log.error("Correo no valido");
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.msj19"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.BAD_REQUEST);
        }


        JwtHandler jwtHandler = new JwtHandler(env);

        int id_cliente = jwtHandler.validate(token);
        if(id_cliente <= 0){
            status.setMessage(jwtHandler.obtainMsj(id_cliente));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, jwtHandler.obtainHttpCode(id_cliente));
        }
        MDC.put("cliente", id_cliente);

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
        if(favorito != null ) {
            status.setCode(env.getProperty("appProperties.code.c409"));
            status.setMessage(env.getProperty("appProperties.messages.mjs11"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.CONFLICT);
        }
        if(id_cliente == prd.getIdpropietario()) {
            status.setCode(env.getProperty("appProperties.code.c409"));
            status.setMessage(env.getProperty("appProperties.messages.msj20"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.CONFLICT);
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
       log.info("beneficiario agregado correctamente"+ f.getIdbene());

        if(f == null ){
            status.setCode(env.getProperty("appProperties.code.c404"));
            status.setMessage(env.getProperty("appProperties.messages.mjs1"));
            serviceMessage = new ServiceMessage(status, null);
            return new ResponseEntity<>(serviceMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.CREATED);

    }


}
