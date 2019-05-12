package com.lifebank.source.lbsourcesvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifebank.source.lbsourcesvc.pojo.cliente.AddBeneficiaryRequest;
import com.lifebank.source.lbsourcesvc.pojo.cliente.UpdateMailRequest;
import com.lifebank.source.lbsourcesvc.pojo.common.ServiceMessage;
import com.lifebank.source.lbsourcesvc.pojo.login.LoginRequest;
import com.lifebank.source.lbsourcesvc.pojo.transaction.SetTransactionRequest;
import com.lifebank.source.lbsourcesvc.process.*;
import com.lifebank.source.lbsourcesvc.utility.RestClient;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private TransactionHistoryProcess tranctionHistoryProcess;
    @Autowired
    private TransactionProcess transactionProcess;
    @Autowired
    private BeneficiarioProcess beneficiarioProcess;
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
            log.error("Hubo un error es login, en la línea {} en el método {}, detalle del error {}",e.getStackTrace()[0].getLineNumber(),e.getStackTrace()[0].getMethodName(),e);
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
            log.info("getProducts request recibido: user:"+ user );
            ServiceMessage serviceMessage = productProcess.process(user);
            log.info("getProducts response:" + mapper.writeValueAsString(serviceMessage));
            return serviceMessage;
        }catch (Exception e){
            log.error("Hubo un error en getProducts, en la línea {} en el método {}, detalle del error {}",e.getStackTrace()[0].getLineNumber(),e.getStackTrace()[0].getMethodName(),e);
            return generateErrorResponse.getGeneralError();
        }

    }

    //Endpoint para obtener los productos de un cliente
    @GetMapping("${app-properties.controller.transactions}")
    public Object getTransactions(@PathVariable("accountID") String idProd, @RequestParam(value="start", required = true) String start,
                                  @RequestParam(value="end", required = true) String end,
                                  @RequestHeader("token") String user) {
            try {
                date = new Date();
                MDC.put("function", "getTransactions");
                MDC.put("date", dateFormat.format(date));
                log.info("getTransactions request recibido: accountID: "+ idProd +", start: "+ start +", end: "+ end);
                ServiceMessage serviceMessage = tranctionHistoryProcess.process(idProd,start,end);
                log.info("getTransactions response:" + mapper.writeValueAsString(serviceMessage));
                return serviceMessage;
            }catch (Exception e){
                log.error("Hubo un error en getTransactions, en la línea {} en el método {}, detalle del error {}",e.getStackTrace()[0].getLineNumber(),e.getStackTrace()[0].getMethodName(),e);
                return generateErrorResponse.getGeneralError();
            }

    }

    
    //Endpoint para realizar una transaccion a productos propios del cliente
    @PostMapping("${app-properties.controller.transaction-p}")
    public Object setTransactionP(@RequestBody SetTransactionRequest request, @RequestHeader("token") int id_cliente) {
        try {
            date = new Date();
            MDC.put("function", "setTransactionP");
            MDC.put("date", dateFormat.format(date));
            log.info("setTransactionP request recibido" + mapper.writeValueAsString(request));
            ServiceMessage serviceMessage = transactionProcess.processP(request, id_cliente);
            log.info("setTransactionP response:" + mapper.writeValueAsString(serviceMessage));
            return serviceMessage;
        } catch (Exception e) {
            log.error("Hubo un error en setTransactionP, en la línea {} en el método {}, detalle del error {}", e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName(), e);
            return generateErrorResponse.getGeneralError();
        }
    }

    //Endpoint para realizar una transaccion a productos de terceros
    @PostMapping("${app-properties.controller.transaction-t}")
    public Object setTransactionT(@RequestBody SetTransactionRequest request, @RequestHeader("token") int id_cliente) {
        try {
            date = new Date();
            MDC.put("function", "setTransactionT");
            MDC.put("date", dateFormat.format(date));
            log.info("setTransactionT request recibido" + mapper.writeValueAsString(request));
            ServiceMessage serviceMessage = transactionProcess.processT(request, id_cliente);
            log.info("setTransactionT response:" + mapper.writeValueAsString(serviceMessage));
            return serviceMessage;
        } catch (Exception e) {
            log.error("Hubo un error en setTransactionT, en la línea {} en el método {}, detalle del error {}", e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName(), e);
            return generateErrorResponse.getGeneralError();
        }
    }

    //Endpoint para actualizar el E-mail de un beneficiario agregado en lista de favoritos.
    @PatchMapping("${app-properties.controller.update-mail}")
    public ResponseEntity<Object> updateMail(@PathVariable("beneficiaryID") String beneficiaryID, @RequestBody UpdateMailRequest request , @RequestHeader("token") int id_cliente) {
        try {
            date = new Date();
            MDC.put("function", "updateMail");
            MDC.put("date", dateFormat.format(date));
            log.info("updateMail request recibido" + mapper.writeValueAsString(request));
            return beneficiarioProcess.updateProcess(request, id_cliente,beneficiaryID);
        } catch (Exception e) {
            log.error("Hubo un error en updateMail, en la línea {} en el método {}, detalle del error {}", e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName(), e);
            return new ResponseEntity<>(generateErrorResponse.getGeneralError(),HttpStatus.BAD_REQUEST);
        }
    }

    //Endpoint para eliminar beneficiario.
    @DeleteMapping("${app-properties.controller.delete-beneficiary}")
    public ResponseEntity<Object> deleteBeneficiary(@PathVariable("beneficiaryID") String beneficiaryID, @RequestHeader("token") int id_cliente) {
        try {
            date = new Date();
            MDC.put("function", "deleteBeneficiary");
            MDC.put("date", dateFormat.format(date));
            log.info("deleteBeneficiary beneficiaryID: " + beneficiaryID);
            return beneficiarioProcess.deleteProcess(id_cliente,beneficiaryID);
        } catch (Exception e) {
            log.error("Hubo un error en deleteBeneficiary, en la línea {} en el método {}, detalle del error {}", e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName(), e);
            return new ResponseEntity<>(generateErrorResponse.getGeneralError(),HttpStatus.BAD_REQUEST);
        }
    }

    //Endpoint para agrear beneficiario y su cuenta a lista de favoritos
    @PostMapping("${app-properties.controller.add-beneficiary}")
    public ResponseEntity<Object> addBeneficiary(@RequestBody AddBeneficiaryRequest request, @RequestHeader("token") int id_cliente) {
        try {
            date = new Date();
            MDC.put("function", "deleteBeneficiary");
            MDC.put("date", dateFormat.format(date));
            log.info("addBeneficiary request recibido" + mapper.writeValueAsString(request));
            return beneficiarioProcess.addProcess(request,id_cliente);
        } catch (Exception e) {
            log.error("Hubo un error en addBeneficiary, en la línea {} en el método {}, detalle del error {}", e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName(), e);
            return new ResponseEntity<>(generateErrorResponse.getGeneralError(),HttpStatus.BAD_REQUEST);
        }
    }


}
