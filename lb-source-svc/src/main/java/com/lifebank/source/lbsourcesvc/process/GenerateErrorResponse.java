package com.lifebank.source.lbsourcesvc.process;

import com.lifebank.source.lbsourcesvc.pojo.common.ServiceMessage;
import com.lifebank.source.lbsourcesvc.pojo.common.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class GenerateErrorResponse {
    @Autowired
    private Environment env;


    public ServiceMessage getGeneralError(){

        Status status = new Status();
        status.setCode(env.getProperty("appProperties.code.c401"));
        status.setMessage(env.getProperty("appProperties.messages.mjs1"));
        ServiceMessage serviceMessage = new ServiceMessage(status,null);
        return serviceMessage;
    }

}
