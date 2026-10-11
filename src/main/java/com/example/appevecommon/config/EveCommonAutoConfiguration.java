package com.example.appevecommon.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@AutoConfiguration
@EntityScan(basePackages = "com.example.appevecommon.Models")
@ComponentScan(basePackages = "com.example.appevecommon.Service.Exception")
@PropertySource(value = "classpath:eve-common-defaults.properties", ignoreResourceNotFound = true)
public class EveCommonAutoConfiguration {
}