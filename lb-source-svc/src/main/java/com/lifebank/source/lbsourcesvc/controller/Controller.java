package com.lifebank.source.lbsourcesvc.controller;

import com.lifebank.source.lbsourcesvc.process.ProductProcess;
import com.lifebank.source.lbsourcesvc.utility.JsonConvert;
import com.lifebank.source.lbsourcesvc.utility.MDCHandler;
import com.lifebank.source.lbsourcesvc.utility.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@PropertySource("classpath:application.properties")
@RequestMapping("${service.url.recpay}")
public class Controller {

    @Autowired
    private Environment env;
    @Autowired
    private RestClient restClient;
    @Autowired
    private ProductProcess productProcess;


    private Logger log;

    public Controller() {
        this.log = LoggerFactory.getLogger(getClass());
    }


    @GetMapping("${app-properties.controller.products}")
    public Object products(@PathVariable("idclient") String idclient) {
        JsonConvert jsonConvert = new JsonConvert();
        try {
            return productProcess.process(1);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            MDCHandler.removeContextVariables();
        }
    }


}
