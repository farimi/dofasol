package com.lxf.dofasol.mpg;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DofasolMpgApplication {

    public static void main(String[] args) {
        ApplicationContext application = SpringApplication.run(DofasolMpgApplication.class, args);
        AutoGenerator generator = application.getBean(AutoGenerator.class);
        generator.execute();
    }

}
