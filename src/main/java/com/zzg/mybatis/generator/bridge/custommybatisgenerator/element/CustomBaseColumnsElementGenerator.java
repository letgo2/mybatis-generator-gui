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
 * @Description 自定义xml文件基础查询列的生成
 * @author      dfg
 * @since       2019/10/30 17:34
 */
public class CustomBaseColumnsElementGenerator extends AbstractXmlElementGenerator {
    public CustomBaseColumnsElementGenerator() {
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id", CustomIntrospectedTable.findTable));
        this.context.getCommentGenerator().addComment(answer);
        StringBuilder sb = new StringBuilder();
        Iterator iter = this.introspectedTable.getAllColumns().iterator();

        while(iter.hasNext()) {
            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase((IntrospectedColumn)iter.next()));
            if (iter.hasNext()) {
                sb.append(", ");
            }

            if (sb.length() > 80) {
                answer.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
        }

        if (sb.length() > 0) {
            answer.addElement(new TextElement(sb.toString()));
        }

        if (this.context.getPlugins().sqlMapBaseColumnListElementGenerated(answer, this.introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
