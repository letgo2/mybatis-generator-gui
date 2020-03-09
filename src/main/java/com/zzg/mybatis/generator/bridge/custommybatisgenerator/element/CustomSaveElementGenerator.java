package com.zzg.mybatis.generator.bridge.custommybatisgenerator.element;

import com.zzg.mybatis.generator.bridge.custommybatisgenerator.CustomIntrospectedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.config.GeneratedKey;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description 自定义xml文件保存的sql
 * @author      dfg
 * @since       2019/10/30 17:37
 */
public class CustomSaveElementGenerator extends AbstractXmlElementGenerator {
    private boolean isSimple;

    public CustomSaveElementGenerator(boolean isSimple) {
        this.isSimple = isSimple;
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert");
        answer.addAttribute(new Attribute("id", CustomIntrospectedTable.save));
        FullyQualifiedJavaType parameterType;
        if (this.isSimple) {
            parameterType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        } else {
            parameterType = this.introspectedTable.getRules().calculateAllFieldsClass();
        }

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

        answer.addElement(new TextElement("insert into "));
        /*// 插入一个表名
        insertClause2.append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime());*/

        // 插入表名sql映射
        XmlElement answer2 = new XmlElement("include");
        answer2.addAttribute(new Attribute("refid", CustomIntrospectedTable.tableMapping));
        answer.addElement(answer2);

        StringBuilder insertClause = new StringBuilder();
        insertClause.append(" (");
        StringBuilder valuesClause = new StringBuilder();
        valuesClause.append("values (");
        List<String> valuesClauses = new ArrayList<>();
        /*// 自动生成的字段移除主键
        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(this.introspectedTable.getAllColumns());*/
        // 获取所有字段
        List<IntrospectedColumn> columns = this.introspectedTable.getAllColumns();

        for(int i = 0; i < columns.size(); ++i) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)columns.get(i);
            insertClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            if (i + 1 < columns.size()) {
                insertClause.append(", ");
                valuesClause.append(", ");
            }

            if (valuesClause.length() > 80) {
                answer.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
                OutputUtilities.xmlIndent(insertClause, 1);
                valuesClauses.add(valuesClause.toString());
                valuesClause.setLength(0);
                OutputUtilities.xmlIndent(valuesClause, 1);
            }
        }

        insertClause.append(')');
        answer.addElement(new TextElement(insertClause.toString()));
        valuesClause.append(')');
        valuesClauses.add(valuesClause.toString());
        Iterator var12 = valuesClauses.iterator();

        while(var12.hasNext()) {
            String clause = (String)var12.next();
            answer.addElement(new TextElement(clause));
        }

        if (this.context.getPlugins().sqlMapInsertElementGenerated(answer, this.introspectedTable)) {
            parentElement.addElement(answer);
        }

    }
}
