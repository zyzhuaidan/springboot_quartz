package com.xingzhou.springboot_quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootQuartzApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(SpringbootQuartzApplication.class, args);
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
