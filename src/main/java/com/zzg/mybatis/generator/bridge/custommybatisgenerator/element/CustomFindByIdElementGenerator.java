package com.zzg.mybatis.generator.bridge.custommybatisgenerator.element;

import com.zzg.mybatis.generator.bridge.custommybatisgenerator.CustomIntrospectedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Iterator;

/**
 * @Description 自定义xml文件通过id查询的sql
 * @author      dfg
 * @since       2019/10/30 17:36
 */
public class CustomFindByIdElementGenerator extends AbstractXmlElementGenerator {
    public CustomFindByIdElementGenerator() {
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", CustomIntrospectedTable.findById));
        answer.addAttribute(new Attribute("resultMap", this.introspectedTable.getBaseResultMapId()));

        String parameterType;
        if (this.introspectedTable.getRules().generatePrimaryKeyClass()) {
            parameterType = this.introspectedTable.getPrimaryKeyType();
        } else if (this.introspectedTable.getPrimaryKeyColumns().size() > 1) {
            parameterType = "map";
        } else {
            parameterType = ((IntrospectedColumn) this.introspectedTable.getPrimaryKeyColumns().get(0)).getFullyQualifiedJavaType().toString();
        }

        answer.addAttribute(new Attribute("parameterType", parameterType));
        this.context.getCommentGenerator().addComment(answer);
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        if (StringUtility.stringHasValue(this.introspectedTable.getSelectByPrimaryKeyQueryId())) {
            sb.append('\'');
            sb.append(this.introspectedTable.getSelectByPrimaryKeyQueryId());
            sb.append("' as QUERYID,");
        }
        answer.addElement(new TextElement(sb.toString()));
        // 自定义查询的include
        XmlElement answer1 = new XmlElement("include");
        answer1.addAttribute(new Attribute("refid", CustomIntrospectedTable.findTable));
        answer.addElement(answer1);

        sb.setLength(0);
        sb.append("from ");
//        sb.append(this.introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        // 自定义表名的映射
        XmlElement answer2 = new XmlElement("include");
        answer2.addAttribute(new Attribute("refid", CustomIntrospectedTable.tableMapping));
        answer.addElement(answer2);

        boolean and = false;
        Iterator var6 = this.introspectedTable.getPrimaryKeyColumns().iterator();

        while (var6.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn) var6.next();
            sb.setLength(0);
            if (and) {
                sb.append("  and ");
            } else {
                sb.append("where ");
                and = true;
            }

            sb.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            answer.addElement(new TextElement(sb.toString()));
        }

        if (this.context.getPlugins().sqlMapSelectByPrimaryKeyElementGenerated(answer, this.introspectedTable)) {
            parentElement.addElement(answer);
        }

    }
}
