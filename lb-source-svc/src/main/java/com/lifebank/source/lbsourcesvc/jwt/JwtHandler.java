package com.lifebank.source.lbsourcesvc.jwt;

import com.lifebank.source.lbsourcesvc.pojo.database.Cliente;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;


public class JwtHandler {
    private Map<String, Object> mapIn = new HashMap<>();
    private Map<String, Object> mapOut = new HashMap<>();
    private  TokenMethods tokenMethods;
    private static Logger log;
    private Environment env;

    public JwtHandler(Environment env) {
        this.env = env;
        tokenMethods = new TokenMethods(env);
        this.log = LoggerFactory.getLogger(getClass());

    }

    public String generateJWT(Cliente c){
        TokenMethods tokenMethods = new TokenMethods(env);
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        }catch (UnknownHostException e){
            log.error(" Hubo un error al consultar getHostAddress {}, en la línea{}, del método {}", e ,e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName());
            return null;
        }
        mapIn.put("id_cliente", c.getIdcli());
        mapIn.put("ip", ip);

        String tkn = "Bearer "+tokenMethods.createToken(mapIn);
        return tkn;
    }

    /* El metodo devolvera
        0 Si el JWT ingresado no es valido o fue alterado.
        -1 Si el JWT ya Expiro
        -2 si la IP no coincide
        -3 si hubo un problema al obtener la IP

        Si devuelve mayor a 0 , el JWT es valido.
    */

    public int validate(String tkn){

        tkn = tkn.substring(7);

        int id_cliente=0;
        String ip = "";

        try {
            mapOut = tokenMethods.readClaims(tkn, env.getProperty("appProperties.jwt.secret"));
        }catch (ExpiredJwtException  e){
            e.printStackTrace();
            log.error(e.getMessage());
            log.error("JWT ya expiro");
            return -1;
        }catch (MalformedJwtException e){
            e.printStackTrace();
            log.error(e.getMessage());
            log.error("JWT no valido.");
            return 0;
        }

        for (Map.Entry<String, Object> entry : mapOut.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("id_cliente")) {
                id_cliente = Integer.valueOf(entry.getValue().toString());
            }
            if (entry.getKey().equalsIgnoreCase("ip")) {
                ip = entry.getValue().toString();
            }
        }

        try {
            if(!ip.equalsIgnoreCase(InetAddress.getLocalHost().getHostAddress())){
                log.error("Direccion IP no coincide."+ InetAddress.getLocalHost().getHostAddress());
                return -2;

            }
        }catch (UnknownHostException e){
            log.error(" Hubo un error al consultar getHostAddress {}, en la línea{}, del método {}", e ,e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName());
            return -3;
        }


        return id_cliente;
    }


    public HttpStatus obtainHttpCode(int i){

        switch (i){
            case 0:
                return HttpStatus.FORBIDDEN; // 403
            case -1:
                return  HttpStatus.valueOf(440);
            case -2:
                return  HttpStatus.FORBIDDEN; // 403
            default:
                return  HttpStatus.NOT_FOUND;
        }
    }
    public String obtainMsj(int i){

        switch (i){
            case 0:
                return env.getProperty("appProperties.messages.mjs12"); // 403
            case -1:
                return env.getProperty("appProperties.messages.mjs13"); // 440
            case -2:
                return  env.getProperty("appProperties.messages.mjs14"); // 403
            default:
                return  env.getProperty("appProperties.messages.mjs1");
        }
    }


}
