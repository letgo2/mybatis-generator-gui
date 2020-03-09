package com.zzg.mybatis.generator.bridge.custommybatisgenerator.element;

import com.zzg.mybatis.generator.bridge.custommybatisgenerator.CustomIntrospectedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

import java.util.Iterator;

/**
 * 自定义表映射的sql
 * @author dfg
 * @date 2020/3/9 15:39
 */
public class CustomTableMappingElementGenerator extends AbstractXmlElementGenerator {
    public CustomTableMappingElementGenerator() {
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id", CustomIntrospectedTable.tableMapping));
        this.context.getCommentGenerator().addComment(answer);
        StringBuilder sb = new StringBuilder();
        /*Iterator iter = this.introspectedTable.getAllColumns().iterator();

        while(iter.hasNext()) {
            sb.append("t.");
            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase((IntrospectedColumn)iter.next()));
            if (iter.hasNext()) {
                sb.append(", ");
            }

            if (sb.length() > 80) {
                answer.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
        }*/
        // 添加表名
        sb.append(this.introspectedTable.getFullyQualifiedTable());

        if (sb.length() > 0) {
            answer.addElement(new TextElement(sb.toString()));
        }

        if (this.context.getPlugins().sqlMapBaseColumnListElementGenerated(answer, this.introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
