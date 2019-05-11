package com.lifebank.source.lbsourcesvc.process;

import com.lifebank.source.lbsourcesvc.encrypt.EncryptHandler;
import com.lifebank.source.lbsourcesvc.pojo.common.ServiceMessage;
import com.lifebank.source.lbsourcesvc.pojo.common.Status;
import com.lifebank.source.lbsourcesvc.pojo.database.Cliente;
import com.lifebank.source.lbsourcesvc.pojo.login.LoginResponse;
import com.lifebank.source.lbsourcesvc.repository.IClienteRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class LoginProcess extends SourceProcess {
    private Logger log;


    public ServiceMessage authenticationProcess(String user, String pass) {
        Status status = new Status();
        ServiceMessage serviceMessage;
        LoginResponse loginResponse = new LoginResponse();


        String cadBase64 = (EncryptHandler.cifrarBase64(user) + EncryptHandler.cifrarBase64(pass));
        pass = EncryptHandler.getStringMessageDigest(cadBase64, EncryptHandler.SHA512);
        try {
            Cliente c = clienteRepository.findByusuarioAndPass(user, pass);
            loginResponse.setTkn("token");  // Agregar logica de Token
            status.setCode(env.getProperty("appProperties.code.c200"));
            serviceMessage = new ServiceMessage(status, loginResponse);

        } catch (Exception e) {
            log.error("Hubo en consulta a la base , en la línea {} en el método {}, detalle del error {}", e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName(), e);
            status.setCode(env.getProperty("appProperties.code.c401"));
            status.setMessage(env.getProperty("appProperties.messages.mjs2"));
            serviceMessage = new ServiceMessage(status, null);
        }
        return serviceMessage;
    }


    public  ServiceMessage authorizationProcess(String Token){
        Status status = new Status();
        ServiceMessage serviceMessage;
        try {
            // Logica de validacion de token
            status.setCode(env.getProperty("appProperties.code.c200"));
            serviceMessage = new ServiceMessage(status, null);

        } catch (Exception e) {
            log.error("Hubo en consulta a la base , en la línea {} en el método {}, detalle del error {}", e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName(), e);
            status.setCode(env.getProperty("appProperties.code.c400"));
            status.setMessage(env.getProperty("appProperties.messages.mjs3"));
            serviceMessage = new ServiceMessage(status, null);
        }

        return serviceMessage;

    }
}

