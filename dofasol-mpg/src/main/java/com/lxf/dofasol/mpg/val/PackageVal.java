package com.lxf.dofasol.mpg.val;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class PackageVal {

    /**功能模块名称*/
    @Value("${mpg.package.moduleName}")
    private String moduleName;

    /**父包名*/
    @Value("${mpg.package.parentPackerName}")
    private String parentPackerName;

}
