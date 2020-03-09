package com.zzg.mybatis.generator.bridge.custommybatisgenerator;

/**
 * 自定义dao层方法名和map文件CRUD操作名
 * @author dfg
 */
public class CustomIntrospectedTable {
    /** 查询通过ID */
    public static String findById = "findById";

    /** 保存 */
    public static String save = "save";

    /** 字段不为null保存 */
    public static String saveSelective = "saveSelective";

    /** update */
    public static String update = "update";

    /** 删除单个 */
    public static String deleteById = "deleteById";

    /** 删除多个 */
    public static String deleteByIds = "deleteByIds";

    /** 单表查询列名的字段名称 */
    public static String findTable = "columns";

    /** 多表查询列名的字段名称 */
    public static String findTables = "selectColumns";

    /** 表名sql映射 */
    public static String tableMapping = "table";
}
