package com.iflytek.liteflow.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.iflytek.liteflow.serializer.BaseNodeDeserializer;
import com.iflytek.liteflow.model.BaseNode;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;


@AutoConfiguration
public class JacksonConfig {

    /**
     * 注册Node的反序列化器
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            SimpleModule module = new SimpleModule("NodeModule");
            // 注册反序列化器
            module.addDeserializer(BaseNode.class, new BaseNodeDeserializer());
            builder.modules(module);
        };
    }
}