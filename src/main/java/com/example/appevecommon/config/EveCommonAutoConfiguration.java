package com.example.appevecommon.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@EntityScan(basePackages = "com.example.appevecommon.Models")
@ComponentScan(basePackages = "com.example.appevecommon.Service.Exception")
public class EveCommonAutoConfiguration {
}