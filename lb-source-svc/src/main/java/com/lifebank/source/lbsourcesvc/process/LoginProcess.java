package com.lifebank.source.lbsourcesvc.process;

import com.lifebank.source.lbsourcesvc.encrypt.EncryptHandler;
import com.lifebank.source.lbsourcesvc.jwt.JwtHandler;
import com.lifebank.source.lbsourcesvc.jwt.TokenMethods;
import com.lifebank.source.lbsourcesvc.pojo.common.ServiceMessage;
import com.lifebank.source.lbsourcesvc.pojo.common.Status;
import com.lifebank.source.lbsourcesvc.pojo.database.Cliente;
import com.lifebank.source.lbsourcesvc.pojo.login.LoginResponse;
import com.lifebank.source.lbsourcesvc.repository.IClienteRepository;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginProcess extends SourceProcess {
    private Logger log;

    public LoginProcess() {
        this.log = LoggerFactory.getLogger(getClass());

    }

    public ResponseEntity<?> authenticationProcess(String user, String pass) {
        Status status = new Status();
        ServiceMessage serviceMessage;
        LoginResponse loginResponse = new LoginResponse();
        JwtHandler jwtHandler = new JwtHandler(env);
        HttpStatus httpStatus;

        String cadBase64 = (EncryptHandler.cifrarBase64(user) + EncryptHandler.cifrarBase64(pass));
        pass = EncryptHandler.getStringMessageDigest(cadBase64, EncryptHandler.SHA512);
        try {
            Cliente c = clienteRepository.findByusuarioAndPass(user, pass);
            if(c != null) {

                status.setCode(env.getProperty("appProperties.code.c200"));
                MDC.put("cliente", c.getIdcli());
                loginResponse.setTkn(jwtHandler.generateJWT(c));
                httpStatus = HttpStatus.OK;
                serviceMessage = new ServiceMessage(status, loginResponse);
                log.info("Login  response: " + mapper.writeValueAsString(serviceMessage));

            }else{
                status.setCode(env.getProperty("appProperties.code.c401"));
                status.setMessage(env.getProperty("appProperties.messages.mjs2"));
                serviceMessage = new ServiceMessage(status, null);
                httpStatus = HttpStatus.UNAUTHORIZED;
            }

        } catch (Exception e) {
            log.error("Hubo en consulta a la base , en la línea {} en el método {}, detalle del error {}", e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName(), e);
            status.setCode(env.getProperty("appProperties.code.c401"));
            status.setMessage(env.getProperty("appProperties.messages.mjs2"));
            serviceMessage = new ServiceMessage(status, null);
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(serviceMessage,httpStatus);
    }

}

