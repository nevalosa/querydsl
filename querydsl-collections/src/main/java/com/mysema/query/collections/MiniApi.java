/*
 * Copyright (c) 2008 Mysema Ltd.
 * All rights reserved.
 * 
 */
package com.mysema.query.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.mysema.query.collections.alias.AliasAwareExprFactory;
import com.mysema.query.collections.alias.AliasFactory;
import com.mysema.query.grammar.Grammar;
import com.mysema.query.grammar.OrderSpecifier;
import com.mysema.query.grammar.types.Expr;
import com.mysema.query.grammar.types.Path;
import com.mysema.query.grammar.types.PathExtractor;
import com.mysema.query.grammar.types.PathMetadata;
import com.mysema.query.grammar.types.ColTypes.ExtString;
import com.mysema.query.grammar.types.Expr.EBoolean;
import com.mysema.query.grammar.types.Expr.EComparable;
import com.mysema.query.grammar.types.Expr.ESimple;
import com.mysema.query.grammar.types.Path.*;

/**
 * MiniApi provides static convenience methods for query construction
 *
 * @author tiwe
 * @version $Id$
 */
public class MiniApi {
    
    private static final AliasFactory aliasFactory = new AliasFactory();
    
    private static final ExprFactory exprFactory = new AliasAwareExprFactory(aliasFactory);
    
    private static final PSimple<Object> it = new PSimple<Object>(Object.class,PathMetadata.forVariable("it"));
    
    public static <A> A alias(Class<A> cl, String var){
        return aliasFactory.createAliasForVar(cl, var);
    }
    
    public static <A> ColQuery<?> from(Expr<A> path, A... arr){
        return from(path, Arrays.asList(arr));
    }
    
    @SuppressWarnings("unchecked")
    public static <A> ColQuery<?> from(Expr<A> path, Iterable<A> col){
        return new ColQuery().from((Path<?>)path, col);
    }
    
    @SuppressWarnings("unchecked")
    public static <A> Iterable<A> select(Iterable<A> from, Expr.EBoolean where, OrderSpecifier<?>... order){
        Path<A> path = (Path<A>) new PathExtractor().handle(where).getPath();
        ColQuery query = new ColQuery().from(path, from).where(where).orderBy(order);
        return query.iterate((Expr<A>)path);
    }
                
    public static <A> Iterable<A> reject(Iterable<A> from, Expr.EBoolean where, OrderSpecifier<?>...order){
        return select(from, Grammar.not(where), order);
    }
    
    public static EBoolean $(Boolean arg){
        return exprFactory.create(arg);
    }
    
    public static <D extends Comparable<D>> EComparable<D> $(D arg){
        return exprFactory.create(arg);
    }
    
    public static ExtString $(String arg){
        return exprFactory.create(arg);
    }

    public static PBooleanArray $(Boolean[] args){
        return exprFactory.create(args);
    }
    
    public static <D extends Comparable<D>> PComparableArray<D> $(D[] args){
        return exprFactory.create(args);
    }
    
    public static PStringArray $(String[] args){
        return exprFactory.create(args);
    }
    
    public static <D> PComponentCollection<D> $(Collection<D> args){
        return exprFactory.create(args);
    }
    
    public static <D> PComponentList<D> $(List<D> args){
        return exprFactory.create(args);
    }
    
    public static <D> ESimple<D> $(D arg){
        return exprFactory.create(arg);
    }

    @SuppressWarnings("unchecked")
    public static <D> PSimple<D> $(){
        return (PSimple<D>) it;
    }
         
}
