package com.zzg.mybatis.generator.bridge.custommybatisgenerator;

import com.zzg.mybatis.generator.bridge.custommybatisgenerator.element.*;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.*;
import org.mybatis.generator.internal.util.messages.Messages;

/**
 * @Description 自定义xml文件生成类
 * @author      dfg
 * @since       2019/10/30 17:32
 */
public class CustomXMLMapperGenerator extends AbstractXmlGenerator {
    public CustomXMLMapperGenerator() {
    }

    protected XmlElement getSqlMapElement() {
        FullyQualifiedTable table = this.introspectedTable.getFullyQualifiedTable();
        this.progressCallback.startTask(Messages.getString("Progress.12", table.toString()));
        XmlElement answer = new XmlElement("mapper");
        String namespace = this.introspectedTable.getMyBatis3SqlMapNamespace();
        answer.addAttribute(new Attribute("namespace", namespace));
        this.context.getCommentGenerator().addRootComment(answer);
        this.addResultMapElement(answer);
        //添加单表查询列名
        this.addBaseColumnsElement(answer);
        //添加多表查询列名
        this.addTablesColumnsElement(answer);
        //findById的mapper
        this.addFindByIdElement(answer);
        //添加save的mapper
        this.addSaveElement(answer);
        //添加saveSelective的mapper
        this.addSaveSelectiveElement(answer);
        //添加update的mapper
        this.addUpdate(answer);
        //添加deleteById的mapper
        this.addDeleteById(answer);
        //添加deleteByIds的mapper
        this.addDeleteByIds(answer);
        return answer;
    }

    protected void addResultMapElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateBaseResultMap()) {
            AbstractXmlElementGenerator elementGenerator = new ResultMapElementGenerator(true);
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addBaseColumnsElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateBaseColumnList()) {
            AbstractXmlElementGenerator elementGenerator = new CustomBaseColumnsElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    protected void addTablesColumnsElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateBaseColumnList()) {
            AbstractXmlElementGenerator elementGenerator = new CustomTablesColumnsElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }
    }

    private void addFindByIdElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new CustomFindByIdElementGenerator();
        this.initializeAndExecuteGenerator(elementGenerator, parentElement);
    }

    private void addSaveElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new CustomSaveElementGenerator(false);
        this.initializeAndExecuteGenerator(elementGenerator, parentElement);
    }

    private void addSaveSelectiveElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new CustomSaveSelectiveElementGenerator();
        this.initializeAndExecuteGenerator(elementGenerator, parentElement);
    }

    private void addUpdate(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new CustomUpdateElementGenerator();
        this.initializeAndExecuteGenerator(elementGenerator, parentElement);
    }

    private void addDeleteById(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new CustomDeleteByIdElementGenerator(false);
        this.initializeAndExecuteGenerator(elementGenerator, parentElement);
    }

    private void addDeleteByIds(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new CustomDeleteByIdsElementGenerator(false);
        this.initializeAndExecuteGenerator(elementGenerator, parentElement);
    }

    protected void addResultMapWithoutBLOBsElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateBaseResultMap()) {
            AbstractXmlElementGenerator elementGenerator = new ResultMapWithoutBLOBsElementGenerator(false);
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addResultMapWithBLOBsElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateResultMapWithBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new ResultMapWithBLOBsElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addExampleWhereClauseElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateSQLExampleWhereClause()) {
            AbstractXmlElementGenerator elementGenerator = new ExampleWhereClauseElementGenerator(false);
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addMyBatis3UpdateByExampleWhereClauseElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateMyBatis3UpdateByExampleWhereClause()) {
            AbstractXmlElementGenerator elementGenerator = new ExampleWhereClauseElementGenerator(true);
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addBaseColumnListElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateBaseColumnList()) {
            AbstractXmlElementGenerator elementGenerator = new BaseColumnListElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addBlobColumnListElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateBlobColumnList()) {
            AbstractXmlElementGenerator elementGenerator = new BlobColumnListElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addSelectByExampleWithoutBLOBsElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateSelectByExampleWithoutBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new SelectByExampleWithoutBLOBsElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addSelectByExampleWithBLOBsElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateSelectByExampleWithBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new SelectByExampleWithBLOBsElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addSelectByPrimaryKeyElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractXmlElementGenerator elementGenerator = new SelectByPrimaryKeyElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addDeleteByExampleElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateDeleteByExample()) {
            AbstractXmlElementGenerator elementGenerator = new DeleteByExampleElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addDeleteByPrimaryKeyElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateDeleteByPrimaryKey()) {
            AbstractXmlElementGenerator elementGenerator = new DeleteByPrimaryKeyElementGenerator(false);
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addInsertElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateInsert()) {
            AbstractXmlElementGenerator elementGenerator = new InsertElementGenerator(false);
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addInsertSelectiveElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateInsertSelective()) {
            AbstractXmlElementGenerator elementGenerator = new InsertSelectiveElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addCountByExampleElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateCountByExample()) {
            AbstractXmlElementGenerator elementGenerator = new CountByExampleElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addUpdateByExampleSelectiveElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateUpdateByExampleSelective()) {
            AbstractXmlElementGenerator elementGenerator = new UpdateByExampleSelectiveElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addUpdateByExampleWithBLOBsElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateUpdateByExampleWithBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new UpdateByExampleWithBLOBsElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addUpdateByExampleWithoutBLOBsElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateUpdateByExampleWithoutBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new UpdateByExampleWithoutBLOBsElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addUpdateByPrimaryKeySelectiveElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateUpdateByPrimaryKeySelective()) {
            AbstractXmlElementGenerator elementGenerator = new UpdateByPrimaryKeySelectiveElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addUpdateByPrimaryKeyWithBLOBsElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateUpdateByPrimaryKeyWithBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new UpdateByPrimaryKeyWithBLOBsElementGenerator();
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void addUpdateByPrimaryKeyWithoutBLOBsElement(XmlElement parentElement) {
        if (this.introspectedTable.getRules().generateUpdateByPrimaryKeyWithoutBLOBs()) {
            AbstractXmlElementGenerator elementGenerator = new UpdateByPrimaryKeyWithoutBLOBsElementGenerator(false);
            this.initializeAndExecuteGenerator(elementGenerator, parentElement);
        }

    }

    protected void initializeAndExecuteGenerator(AbstractXmlElementGenerator elementGenerator, XmlElement parentElement) {
        elementGenerator.setContext(this.context);
        elementGenerator.setIntrospectedTable(this.introspectedTable);
        elementGenerator.setProgressCallback(this.progressCallback);
        elementGenerator.setWarnings(this.warnings);
        elementGenerator.addElements(parentElement);
    }

    @Override
    public Document getDocument() {
        Document document = new Document("-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
        document.setRootElement(this.getSqlMapElement());
        if (!this.context.getPlugins().sqlMapDocumentGenerated(document, this.introspectedTable)) {
            document = null;
        }

        return document;
    }
}
