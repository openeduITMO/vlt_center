package com.spring.boot.vlt.config.property;

import java.util.List;
import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

public class GroupConcatFunction implements SQLFunction {

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public boolean hasParenthesesIfNoArguments() {
        return true;
    }

    @Override
    public Type getReturnType(Type firstArgumentType, Mapping mapping)
            throws QueryException {
        return StandardBasicTypes.STRING;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String render(Type firstArgumentType, List arguments,
                         SessionFactoryImplementor factory) throws QueryException {
        if (arguments.size() != 1) {
            throw new QueryException("group_concat should have only one argument");
        }
        return "group_concat(" + arguments.get(0) + ")";
    }
}
