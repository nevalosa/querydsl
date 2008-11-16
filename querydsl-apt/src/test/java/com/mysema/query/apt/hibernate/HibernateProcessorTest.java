/*
 * Copyright (c) 2008 Mysema Ltd.
 * All rights reserved.
 * 
 */
package com.mysema.query.apt.hibernate;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.mysema.query.apt.Constructor;
import com.mysema.query.apt.Field;
import com.mysema.query.apt.Parameter;
import com.mysema.query.apt.Type;

/**
 * HibernateProcessorTest provides.
 * 
 * @author tiwe
 * @version $Id$
 */
public class HibernateProcessorTest  {
    
    private Type type;
    
    private Writer writer = new StringWriter();
    
    private Map<String, Object> model = new HashMap<String, Object>();
        
    public HibernateProcessorTest(){
        type = new Type("com.mysema.query.DomainSuperClass","com.mysema.query.DomainClass","DomainClass");
        Field field = new Field("field",null,"java.lang.String","java.lang.String",Field.Type.STRING);
        type.addField(field);
        Parameter param = new Parameter("name","java.lang.String");
        type.addConstructor( new Constructor(Collections.singleton(param)));
    }
    
    @Test
    public void testDomainTypesAsInnerClass() throws Exception{        
        model.put("domainTypes", Collections.singleton(type));
        model.put("pre", "");
        model.put("include", "");
        model.put("package",  "com.mysema.query");
        model.put("classSimpleName", "Test");
        
        // as inner classes        
        HibernateProcessor.DOMAIN_INNER_TMPL.serialize(model, writer);
    }
    
    @Test
    public void testDomainTypesAsOuterClasses() throws Exception{
        model.put("type", type);
        model.put("pre", "");
        model.put("include", "");
        model.put("package",  "com.mysema.query");
        model.put("classSimpleName", "Test");
        
        // as outer classes        
        HibernateProcessor.DOMAIN_OUTER_TMPL.serialize(model, writer);
    }

    @Test
    public void testDTOTypes() throws Exception{
        model.put("dtoTypes", Collections.singleton(type));
        model.put("pre", "");
        model.put("package", "com.mysema.query");
        model.put("classSimpleName", "Test");
        
        // as inner classes        
        HibernateProcessor.DTO_INNER_TMPL.serialize(model, writer);
    }
        
}
