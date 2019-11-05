package com.zzg.mybatis.generator.bridge.custommybatisgenerator;

import com.github.javaparser.JavaParser;
import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.comments.Comment;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mybatis.generator.api.dom.OutputUtilities.newLine;

/**
 * @Description 自定义java文件的合并
 * @author      dfg
 * @since       2019/10/30 17:25
 */
public class CustomDefaultShellCallback extends DefaultShellCallback {

    /**
     * 是否支持java文件合并
     */
    private boolean isMerge;
    /**
     * Instantiates a new default shell callback.
     *
     * @param overwrite the overwrite
     */
    public CustomDefaultShellCallback(boolean overwrite, boolean isMerge) {
        super(overwrite);
        this.isMerge = isMerge;
    }

    @Override
    public boolean isMergeSupported() {
        return isMerge;
    }

    /**
     * 重写合并java文件的方法
     * @param newFileSource
     * @param existingFile
     * @param javadocTags
     * @param fileEncoding
     * @return
     */
    @Override
    public String mergeJavaFile(String newFileSource, File existingFile, String[] javadocTags, String fileEncoding) {

        CompilationUnit newCompilationUnit = JavaParser.parse(newFileSource);
        CompilationUnit existingCompilationUnit = null;
        try {
            existingCompilationUnit = JavaParser.parse(existingFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }

        return mergerFile(newCompilationUnit, existingCompilationUnit);
    }


    /**
     * 具体实现合并java文件
     * @param newCompilationUnit
     * @param existingCompilationUnit
     * @return
     */
    private String mergerFile(CompilationUnit newCompilationUnit,CompilationUnit existingCompilationUnit){

        StringBuilder sb = new StringBuilder(newCompilationUnit.getPackageDeclaration().get().toString());
        newCompilationUnit.removePackageDeclaration();

        //合并imports
        NodeList<ImportDeclaration> imports = newCompilationUnit.getImports();
        imports.addAll(existingCompilationUnit.getImports());
        Set importSet = new HashSet<ImportDeclaration>();
        importSet.addAll(imports);

        NodeList<ImportDeclaration> newImports = new NodeList<>();
        newImports.addAll(importSet);
        newCompilationUnit.setImports(newImports);
        for (ImportDeclaration i:newCompilationUnit.getImports()) {
            sb.append(i.toString());
        }
        newLine(sb);
        NodeList<TypeDeclaration<?>> types = newCompilationUnit.getTypes();
        NodeList<TypeDeclaration<?>> oldTypes = existingCompilationUnit.getTypes();

        for (int i = 0;i<types.size();i++) {
            //截取Class
            String classNameInfo = types.get(i).toString().substring(0, types.get(i).toString().indexOf("{")+1);
            sb.append(classNameInfo);
            newLine(sb);
            //合并fields
            List<FieldDeclaration> fields = types.get(i).getFields();
            List<FieldDeclaration> oldFields = oldTypes.get(i).getFields();
            for (FieldDeclaration f: fields){
                sb.append("    ");
                String fieldAndComment = f.toString();
                int privateIndex = fieldAndComment.indexOf("private");
                sb.append(fieldAndComment.substring(0, privateIndex));
                sb.append("    ");
                sb.append(fieldAndComment.substring(privateIndex, fieldAndComment.length()));
                newLine(sb);
                newLine(sb);
            }

            for (FieldDeclaration f: oldFields){
                boolean flag = true;
                for (String tag : MergeConstants.OLD_ELEMENT_TAGS) {
                    if (f.toString().contains(tag)) {
                        flag = false;
                        break;
                    }
                }
                addMethodAndField(sb, flag, f.getComment(), f.getTokenRange(), f.toString());
            }

            //合并methods
            List<MethodDeclaration> methods = types.get(i).getMethods();
            List<MethodDeclaration> existingMethods = oldTypes.get(i).getMethods();
            for (MethodDeclaration f: methods){
                addMethodAndComment(f.toString(), sb);
                newLine(sb);
                newLine(sb);
            }
            for (MethodDeclaration m:existingMethods){
                boolean flag = true;
                for (String tag : MergeConstants.OLD_ELEMENT_TAGS) {
                    if (m.toString().contains(tag)) {
                        flag = false;
                        break;
                    }
                }
                addMethodAndField(sb, flag, m.getComment(), m.getTokenRange(), m.toString());
            }

            //判断是否有内部类
            types.get(i).getChildNodes();
            for (Node n:types.get(i).getChildNodes()){
                if (n.toString().contains("static class")){
                    sb.append(n.toString());
                }
                if (n.toString().contains("enum")) {
                    sb.append(n.toString());
                }
            }

        }

        return sb.append(System.getProperty("line.separator")).append("}").toString();
    }

    private void addMethodAndField(StringBuilder sb, boolean flag, Optional<Comment> comment2, Optional<TokenRange> tokenRange, String s) {
        if (flag){
            sb.append("    ");
            Optional<Comment> comment = comment2;
            if (comment.isPresent()) {
                sb.append(comment.get().getTokenRange().get().toString());
                newLine(sb);
                sb.append("    ");
                sb.append(tokenRange.get().toString());
            } else {
                sb.append(s);
            }
            newLine(sb);
            newLine(sb);
        }
    }

    /**
     * 给生成的属性跟方法添加格式
     * @param s
     * @param sb
     */
    private void addMethodAndComment(String s, StringBuilder sb) {
        int publicIndex = s.indexOf("public");

        if (publicIndex == -1) {
            sb.append("    ");
            sb.append(s);
            return ;
        }
        if (publicIndex != 0) {
            sb.append("    ");
        }

        sb.append(s.substring(0, publicIndex));
        sb.append("    ");
        int returnIndex = s.indexOf("  ");
        sb.append(s.substring(publicIndex, returnIndex));
        sb.append("    ");
        sb.append(s.substring(returnIndex, s.length() - 1));
        sb.append("    }");
    }
}
