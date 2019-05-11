package com.loyalty.source.lbsourcesvc.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonConvert {
    private static ObjectMapper mapper;
    private Logger log;

    public JsonConvert() {
        this.log = LoggerFactory.getLogger(getClass());
    }

    public  String convertObjectToJson(Object requestObject) {
        try {
            mapper = new ObjectMapper();
            return mapper.writeValueAsString(requestObject);
        } catch (JsonProcessingException e) {
            log.error(" Hubo un error al consultar convertObjectToJson {}, en la línea{}, del método {}",
                    e, e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName());
            return null;
        }
    }

}
