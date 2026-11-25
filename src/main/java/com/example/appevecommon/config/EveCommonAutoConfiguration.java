package com.example.appevecommon.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@AutoConfiguration
@EntityScan(basePackages = "com.example.appevecommon.Models")
@ComponentScan(basePackages = "com.example.appevecommon.Service.Exception")
@ComponentScan(basePackages = "com.example.appevecommon.Service.Email")
@Import(RedisCacheErrorConfig.class)
@PropertySource(value = "classpath:eve-common-defaults.properties", ignoreResourceNotFound = true)
@EnableAsync
public class EveCommonAutoConfiguration {}