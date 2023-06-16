package com.hy.demo;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.github.davidfantasy.mybatisplus.generatorui.GeneratorConfig;
import com.github.davidfantasy.mybatisplus.generatorui.MybatisPlusToolsApplication;
import com.github.davidfantasy.mybatisplus.generatorui.mbp.NameConverter;
import com.github.davidfantasy.mybatisplus.generatorui.mbp.TemplateVaribleInjecter;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

public class GeberatorUIServer {



    public static void main(String[] args) {
        GeneratorConfig config = GeneratorConfig.builder().jdbcUrl("jdbc:postgresql://10.32.128.113:5432/gcfx10")
                .userName("postgres")
                .password("123456")
                .driverClassName("org.postgresql.Driver")
                //数据库schema，MSSQL,PGSQL,ORACLE,DB2类型的数据库需要指定
//                .schemaName("postgres")
                //数据库表前缀，生成entity名称时会去掉(v2.0.3新增)
//                .tablePrefix("t_")
                //如果需要修改entity及其属性的命名规则，以及自定义各类生成文件的命名规则，可自定义一个NameConverter实例，覆盖相应的名称转换方法，详细可查看该接口的说明：                
                .nameConverter(new NameConverter() {
                    @Override
                    public String entityNameConvert(String tableName) {
                        // 重写实体类类名生成规则  例子 test_user_cc -> Testusercc
                        if (Strings.isNullOrEmpty(tableName)) {
                            return "";
                        }else {
                            String str = tableName.toLowerCase().replaceAll("_", "");
                            return str.substring(0,1).toUpperCase()+str.substring(1,str.length());
                        }

                    }

                    @Override
                    public String propertyNameConvert(String fieldName) {
                        // 保留源数据库字段类型
                        if(Strings.isNullOrEmpty(fieldName)){
                            return "";
                        }else {
                            return fieldName.toLowerCase();
                        }
                    }

                    @Override
                    public String mapperNameConvert(String entityName) {
                        return NameConverter.super.mapperNameConvert(entityName);
                    }

                    @Override
                    public String mapperXmlNameConvert(String entityName) {
                        return NameConverter.super.mapperXmlNameConvert(entityName);
                    }

                    /**
                     * 自定义Service类文件的名称规则，entityName是NameConverter.entityNameConvert处理表名后的返回结果，如有特别的需求可以自定义实现
                     */
                    @Override
                    public String serviceNameConvert(String entityName) {
                        return entityName + "Service";
                    }

                    @Override
                    public String serviceImplNameConvert(String entityName) {
                        return NameConverter.super.serviceImplNameConvert(entityName);
                    }

                    /**
                     * 自定义Controller类文件的名称规则
                     */
                    @Override
                    public String controllerNameConvert(String entityName) {
                        return entityName + "Controller";
                    }

                    @Override
                    public String customFileNameConvert(String fileType, String entityName) {
                        return NameConverter.super.customFileNameConvert(fileType, entityName);
                    }
                })
                .templateVaribleInjecter(new TemplateVaribleInjecter(){
                    @Override
                    public Map<String, Object> getCustomTemplateVaribles(TableInfo tableInfo){
                        Map<String, Object> params=new HashMap<>();
                        // mapper 注解参数
                        params.put("mapperAnnotationSimpleName","Mapper");
                        params.put("mapperAnnotationName","org.apache.ibatis.annotations.Mapper");
                        // 开启 controller swagger 参数
                        params.put("swaggerAnnotation","Api");

                        return params;
                    }
                })
                //所有生成的java文件的父包名，后续也可单独在界面上设置 mapperAnnotationClass.name
                .basePackage("com.github.davidfantasy.mybatisplustools.example")
                .port(8068)
                .build();
        MybatisPlusToolsApplication.run(config);
    }

}