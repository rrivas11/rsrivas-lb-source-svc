package com.lifebank.source.lbsourcesvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifebank.source.lbsourcesvc.pojo.common.ServiceMessage;
import com.lifebank.source.lbsourcesvc.pojo.login.LoginRequest;
import com.lifebank.source.lbsourcesvc.process.*;
import com.lifebank.source.lbsourcesvc.utility.JsonConvert;
import com.lifebank.source.lbsourcesvc.utility.MDCHandler;
import com.lifebank.source.lbsourcesvc.utility.RestClient;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@PropertySource("classpath:application.properties")
@RequestMapping("${service.url.lifebank}")
public class Controller {
    @Autowired
    private Environment env;
    @Autowired
    private RestClient restClient;
    @Autowired
    private ProductProcess productProcess;
    @Autowired
    private LoginProcess loginProcess;
    @Autowired
    private TransactionProcess tranctionProcess;
    @Autowired
    private GenerateErrorResponse generateErrorResponse;

    private Date date;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private ObjectMapper mapper = new ObjectMapper();
    private Logger log;

    public Controller() {
        this.log = LoggerFactory.getLogger(getClass());
    }


    //Endpoint que valida usuario y contraseña.
    @PostMapping("${app-properties.controller.login}")
    public Object login(@RequestBody LoginRequest loginRequest) {
        try {
            date = new Date();
            MDC.put("function", "login");
            MDC.put("date", dateFormat.format(date));
            //log.info("request recibido: "+ mapper.writeValueAsString(loginRequest)); //OJO Quitar contraseña de los logs
            ServiceMessage serviceMessage = loginProcess.authenticationProcess(loginRequest.getUser(),loginRequest.getPass());
            log.info("Login response:" + mapper.writeValueAsString(serviceMessage));
            return serviceMessage;
        }catch (Exception e){
            log.error("Hubo un error  login, en la línea {} en el método {}, detalle del error {}",e.getStackTrace()[0].getLineNumber(),e.getStackTrace()[0].getMethodName(),e);
            return generateErrorResponse.getGeneralError();
        }

    }

    //Endpoint para obtener los productos de un cliente
    @GetMapping("${app-properties.controller.products}")
    public Object getProducts(@PathVariable("user") String user,@RequestHeader("token") String token) {
        try {
            date = new Date();
            MDC.put("function", "getProducts");
            MDC.put("date", dateFormat.format(date));
            log.info("request recibido: user:"+ user );
            ServiceMessage serviceMessage = productProcess.process(user);
            log.info("Login response:" + mapper.writeValueAsString(serviceMessage));
            return serviceMessage;
        }catch (Exception e){
            log.error("Hubo un error  getProducts, en la línea {} en el método {}, detalle del error {}",e.getStackTrace()[0].getLineNumber(),e.getStackTrace()[0].getMethodName(),e);
            return generateErrorResponse.getGeneralError();
        }

    }

    //Endpoint para obtener los productos de un cliente
    @GetMapping("${app-properties.controller.transactions}")
    public Object getTransactions(@PathVariable("accountID") String idProd,
                                  @RequestParam(value="start", required = true) String start,
                                  @RequestParam(value="end", required = true) String end,
                                  @RequestHeader("token") String user) {
        try {
            date = new Date();
            MDC.put("function", "getProducts");
            MDC.put("date", dateFormat.format(date));
            log.info("request recibido: accountID: "+ idProd +", start: "+ start +", end: "+ end);
            ServiceMessage serviceMessage = tranctionProcess.process(idProd,start,end);
            log.info("Login response:" + mapper.writeValueAsString(serviceMessage));
            return serviceMessage;
        }catch (Exception e){
            log.error("Hubo un error  getProducts, en la línea {} en el método {}, detalle del error {}",e.getStackTrace()[0].getLineNumber(),e.getStackTrace()[0].getMethodName(),e);
            return generateErrorResponse.getGeneralError();
        }

    }




}
