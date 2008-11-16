<#import "/macros.ftl" as cl>
package ${package};

import static com.mysema.query.grammar.types.HqlTypes.*;
import com.mysema.query.grammar.types.*;

/**
 * ${classSimpleName} provides types for use in Query DSL constructs
 *
 */
public class ${pre}${classSimpleName} extends Constructor<${type.name}>{
    <#list type.constructors as co>    
        public ${pre}${type.simpleName}(<#list co.parameters as pa>Expr<${pa.typeName}> ${pa.name}<#if pa_has_next>,</#if></#list>){
            super(${type.name}.class<#list co.parameters as pa>,${pa.name}</#list>);
        }
    </#list>   
}
