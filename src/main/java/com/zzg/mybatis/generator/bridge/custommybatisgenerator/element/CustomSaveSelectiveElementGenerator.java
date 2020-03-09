package com.zzg.mybatis.generator.bridge.custommybatisgenerator.element;

import com.zzg.mybatis.generator.bridge.custommybatisgenerator.CustomIntrospectedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.config.GeneratedKey;

import java.util.Iterator;

/**
 * @Description 自定义xml文件选择保存列的sql
 * @author      dfg
 * @since       2019/10/30 17:38
 */
public class CustomSaveSelectiveElementGenerator extends AbstractXmlElementGenerator {
    public CustomSaveSelectiveElementGenerator() {
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert");
        answer.addAttribute(new Attribute("id", CustomIntrospectedTable.saveSelective));
        FullyQualifiedJavaType parameterType = this.introspectedTable.getRules().calculateAllFieldsClass();
        answer.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));
        this.context.getCommentGenerator().addComment(answer);
        GeneratedKey gk = this.introspectedTable.getGeneratedKey();
        if (gk != null) {
            IntrospectedColumn introspectedColumn = this.introspectedTable.getColumn(gk.getColumn());
            if (introspectedColumn != null) {
                if (gk.isJdbcStandard()) {
                    answer.addAttribute(new Attribute("useGeneratedKeys", "true"));
                    answer.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty()));
                    answer.addAttribute(new Attribute("keyColumn", introspectedColumn.getActualColumnName()));
                } else {
                    answer.addElement(this.getSelectKey(introspectedColumn, gk));
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        /*// 去除表名
        sb.append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime());*/
        answer.addElement(new TextElement(sb.toString()));

        // 自定义表名的映射sql
        XmlElement answer2 = new XmlElement("include");
        answer2.addAttribute(new Attribute("refid", CustomIntrospectedTable.tableMapping));
        answer.addElement(answer2);

        XmlElement insertTrimElement = new XmlElement("trim");
        insertTrimElement.addAttribute(new Attribute("prefix", "("));
        insertTrimElement.addAttribute(new Attribute("suffix", ")"));
        insertTrimElement.addAttribute(new Attribute("suffixOverrides", ","));
        answer.addElement(insertTrimElement);
        XmlElement valuesTrimElement = new XmlElement("trim");
        valuesTrimElement.addAttribute(new Attribute("prefix", "values ("));
        valuesTrimElement.addAttribute(new Attribute("suffix", ")"));
        valuesTrimElement.addAttribute(new Attribute("suffixOverrides", ","));
        answer.addElement(valuesTrimElement);
        /*// 自动生成的字段移除主键
        Iterator var8 = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(this.introspectedTable.getAllColumns()).iterator();*/
        Iterator var8 = this.introspectedTable.getAllColumns().iterator();

        while(true) {
            while(var8.hasNext()) {
                IntrospectedColumn introspectedColumn = (IntrospectedColumn)var8.next();
                if (!introspectedColumn.isSequenceColumn() && !introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
                    sb.setLength(0);
                    sb.append(introspectedColumn.getJavaProperty());
                    sb.append(" != null");
                    XmlElement insertNotNullElement = new XmlElement("if");
                    insertNotNullElement.addAttribute(new Attribute("test", sb.toString()));
                    sb.setLength(0);
                    sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
                    sb.append(',');
                    insertNotNullElement.addElement(new TextElement(sb.toString()));
                    insertTrimElement.addElement(insertNotNullElement);
                    sb.setLength(0);
                    sb.append(introspectedColumn.getJavaProperty());
                    sb.append(" != null");
                    XmlElement valuesNotNullElement = new XmlElement("if");
                    valuesNotNullElement.addAttribute(new Attribute("test", sb.toString()));
                    sb.setLength(0);
                    sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
                    sb.append(',');
                    valuesNotNullElement.addElement(new TextElement(sb.toString()));
                    valuesTrimElement.addElement(valuesNotNullElement);
                } else {
                    sb.setLength(0);
                    sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
                    sb.append(',');
                    insertTrimElement.addElement(new TextElement(sb.toString()));
                    sb.setLength(0);
                    sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
                    sb.append(',');
                    valuesTrimElement.addElement(new TextElement(sb.toString()));
                }
            }

            if (this.context.getPlugins().sqlMapInsertSelectiveElementGenerated(answer, this.introspectedTable)) {
                parentElement.addElement(answer);
            }

            return;
        }
    }
}
