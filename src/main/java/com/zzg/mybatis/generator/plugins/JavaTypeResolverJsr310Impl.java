package com.zzg.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author hanakei
 * @since 2018/4/28
 */
public class JavaTypeResolverJsr310Impl extends JavaTypeResolverDefaultImpl {

    @Override
    protected FullyQualifiedJavaType overrideDefaultType(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
        FullyQualifiedJavaType answer = defaultType;

        switch (column.getJdbcType()) {
            case Types.SMALLINT:
            case Types.TINYINT:
            case Types.BIT:
                answer = new FullyQualifiedJavaType(Integer.class.getName());
                break;
            case Types.DECIMAL:
            case Types.NUMERIC:
                answer = calculateBigDecimalReplacement(column, defaultType);
                break;
            case Types.DATE:
                answer = new FullyQualifiedJavaType(LocalDate.class.getName());
                break;
            case Types.TIME:
                answer = new FullyQualifiedJavaType(LocalTime.class.getName());
                break;
            case Types.TIMESTAMP:
                answer = new FullyQualifiedJavaType(LocalDateTime.class.getName());
                break;
            default:
                break;
        }

        return answer;
    }
}
