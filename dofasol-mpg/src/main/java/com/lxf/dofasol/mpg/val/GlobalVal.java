package com.lxf.dofasol.mpg.val;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class GlobalVal {

    /**开发人员*/
    @Value("${mpg.global.author}")
    private String author;

    /**开启 swagger2 模式*/
    @Value("#{${mpg.global.swagger2}}")
    private boolean swagger2=false;

    /**实体命名方式*/
    @Value("${mpg.global.entityName}")
    private String entityName;

    /**mapper 命名方式*/
    @Value("${mpg.global.mapperName}")
    private String mapperName;

    /** xml 命名方式*/
    @Value("${mpg.global.xmlName}")
    private String xmlName;

    /**service命名方式*/
    @Value("${mpg.global.serviceName}")
    private String serviceName;

    /**serviceImp命名方式*/
    @Value("${mpg.global.serviceImplName}")
    private String serviceImplName;

}
