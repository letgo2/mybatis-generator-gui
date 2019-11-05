package com.zzg.mybatis.generator.bridge.custommybatisgenerator;

import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;

/**
 * @Description 自定义生成器运行类
 * @author      dfg
 * @since       2019/10/30 17:31
 */
public class CustomMybatis3 extends IntrospectedTableMyBatis3Impl {

    /**
     * dao层方法命名
     */
    @Override
    protected void calculateJavaClientAttributes() {

        super.calculateJavaClientAttributes();

        StringBuffer sb = new StringBuffer();
        sb.append(this.calculateJavaClientInterfacePackage());
        sb.append('.');


        sb.append(this.fullyQualifiedTable.getDomainObjectName());
        sb.append("Dao");

        String mapperName = sb.toString();
        super.setMyBatis3JavaMapperType(mapperName);

    }

    /**
     * entity层命名
     */
    @Override
    public void calculateModelAttributes(){
        super.calculateModelAttributes();
        String packageName = super.calculateJavaModelPackage();
        StringBuilder sb = new StringBuilder();

        sb.append(packageName);
        sb.append('.');
        sb.append(this.fullyQualifiedTable.getDomainObjectName());
    }


    

}
