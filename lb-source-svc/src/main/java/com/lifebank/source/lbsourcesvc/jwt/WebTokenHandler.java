package com.lifebank.source.lbsourcesvc.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.env.Environment;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class WebTokenHandler {
    private Environment env;
    private Logger log;
    public WebTokenHandler(Environment env) {
        this.env = env;
        this.log = LoggerFactory.getLogger(this.getClass());

    }
    public boolean compareWebToken(Object object, String jsonWebToken, Class<?> TemplateObject) {    
       try {
    	Field[] templateFields = TemplateObject.getDeclaredFields();
        TokenMethods tokenMethods = new TokenMethods(env);
        Map<String, Object> maToken = tokenMethods.readClaims(jsonWebToken, env.getProperty("configuration.jwt.secret").toString());
        Map<String, Object> map = getObjectMap(object);
        for(Field field : templateFields) {
            String fieldName = field.getName();
            if(!compareEntries(maToken, map, fieldName))
            {
                return false;
            }
        }
            return true;
       }catch(Exception e) {
           log.error(" Hubo un error al consultar cms.overview.credit-card-data {}, en la línea{}, del método {}",
                   e, e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName());


           return false;
       }
            
    }
    private boolean compareEntries(Map<String, Object> jsonMap, Map<String, Object> objectMap, String propertyName) {
        try {
            if(String.valueOf(jsonMap.get(propertyName)).equalsIgnoreCase(String.valueOf(objectMap.get(propertyName)))){
                return true;
            }
            else
            {
                return false;
            }    
        }
        catch(Exception e) {
            log.error(" Hubo un error al consultar compareEntries {}, en la línea{}, del método {}",
                    e, e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName());
            return false;
        }    
    }
    private static Map<String, Object> getObjectMap (Object object) {
        final BeanWrapper src = new BeanWrapperImpl(object);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Map<String, Object>map = new HashMap<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            map.put(pd.getName(), src.getPropertyValue(pd.getName()));
        }
        return map;
    }
}