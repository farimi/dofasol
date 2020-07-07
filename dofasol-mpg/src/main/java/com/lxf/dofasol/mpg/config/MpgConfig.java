package com.lxf.dofasol.mpg.config;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.lxf.dofasol.mpg.val.DataSourceVal;
import com.lxf.dofasol.mpg.val.GlobalVal;
import com.lxf.dofasol.mpg.val.PackageVal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
public class MpgConfig {

    @Autowired
    DataSourceVal dataSourceVal;

    @Autowired
    GlobalVal globalVal;

    @Autowired
    PackageVal packageVal;

    @Bean
    public AutoGenerator autoGenerator(DataSourceConfig dataSourceConfig,
                                       GlobalConfig globalConfig,
                                       PackageConfig packageConfig,
                                       StrategyConfig strategyConfig,
                                       TemplateConfig templateConfig) {
        AutoGenerator ag = new AutoGenerator();
        ag.setDataSource(dataSourceConfig);
        ag.setGlobalConfig(globalConfig);
        ag.setPackageInfo(packageConfig);
        //设置要生成的表
        strategyConfig.setInclude(getTables(dataSourceConfig.getConn()));
        ag.setStrategy(strategyConfig);
        ag.setTemplate(templateConfig);
        return ag;
    }


    /**
     * 全局设置
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig()
                .setAuthor(globalVal.getAuthor())
                .setFileOverride(true)
                .setOpen(false)
                .setSwagger2(globalVal.isSwagger2())
                .setEntityName(globalVal.getEntityName())
                .setServiceName(globalVal.getServiceName())
                .setServiceImplName(globalVal.getServiceImplName())
                .setMapperName(globalVal.getMapperName())
                .setXmlName(globalVal.getXmlName())
                .setBaseResultMap(true)
                .setBaseColumnList(true);
        return globalConfig;
    }

    /**
     * 数据源设置
     */
    @Bean
    public DataSourceConfig dataSourceConfig() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setUsername(dataSourceVal.getUserName())
                .setPassword(dataSourceVal.getPassword())
                .setUrl(dataSourceVal.getUrl())
                .setDriverName(dataSourceVal.getDriverName());
        return dataSourceConfig;
    }

    /**
     * 包配置
     */
    @Bean
    public PackageConfig packageConfig() {
        PackageConfig packageConfig = new PackageConfig()
                .setModuleName(packageVal.getModuleName())
                .setParent(packageVal.getParentPackerName());
        //自定义文件输出文件，默认是项目根目录的mpg文件夹下
        packageConfig.setPathInfo(fileOutPath());
        return packageConfig;
    }

    /**
     * 策略配置
     */
    @Bean
    public StrategyConfig strategyConfig() {
        StrategyConfig strategyConfig = new StrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setEntityTableFieldAnnotationEnable(true)
                .setEntityLombokModel(true)
                .setTablePrefix(packageVal.getModuleName());
        return strategyConfig;
    }

    /**
     * 模板配置
     */
    @Bean
    public TemplateConfig templateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController(null);
        return templateConfig;
    }

    /**
     * 获取表
     * */
    private String[] getTables(Connection conn){
        String[] tables = null;
        String sql = null;
        try {
            sql = String .format("select table_name from information_schema.tables " +
                    "where table_schema='%s' and table_name like '%s_%%'"
                    ,conn.getCatalog()
                    ,packageVal.getModuleName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("拼接的sql语句:"+sql);
        try (Connection c = conn;
             Statement statement = c.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<String> list = new ArrayList<>();
            while (resultSet.next()){
                String table = resultSet.getString("table_name");
                log.info("表："+table);
                list.add(table);
            }
            tables = new String[list.size()];
            list.toArray(tables);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    /**
     * 设置文件输出路径
     * */
    private Map<String,String> fileOutPath(){
        String rootPath = System.getProperty("user.dir")+File.separator+"mpg"+File.separator+packageVal.getModuleName()+File.separator;
        Map<String,String> map = new HashMap<>();
        map.put(ConstVal.ENTITY_PATH,rootPath+ "model");
        map.put(ConstVal.SERVICE_PATH,rootPath + "service");
        map.put(ConstVal.SERVICE_IMPL_PATH,rootPath + "impl");
        map.put(ConstVal.MAPPER_PATH,rootPath + "mapper");
        map.put(ConstVal.XML_PATH,rootPath + "xml");
        return map;
    }

}
