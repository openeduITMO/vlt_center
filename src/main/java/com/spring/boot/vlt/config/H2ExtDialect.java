package com.spring.boot.vlt.config;

import org.hibernate.Hibernate;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;

import java.sql.Types;

//@Configuration
public class H2ExtDialect extends H2Dialect {

    public H2ExtDialect() {
        super();
        registerKeyword("SEPARATOR");
        registerFunction("group_concat", new StandardSQLFunction("group_concat", new StringType()));
//        registerFunction("group_concat", new SQLFunctionTemplate(StandardBasicTypes.STRING, "GROUP_CONCAT(?1)"));
//        registerHibernateType(Types.LONGVARCHAR, StandardBasicTypes.TEXT.getName());

    }
}