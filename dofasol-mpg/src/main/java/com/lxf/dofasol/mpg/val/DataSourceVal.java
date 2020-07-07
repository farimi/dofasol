package com.lxf.dofasol.mpg.val;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class DataSourceVal {

        @Value("${mpg.jdbc.userName}")
        private String userName;

        @Value("${mpg.jdbc.password}")
        private String password;

        @Value("${mpg.jdbc.url}")
        private String url;

        @Value("${mpg.jdbc.driverName}")
        private String driverName;

}
