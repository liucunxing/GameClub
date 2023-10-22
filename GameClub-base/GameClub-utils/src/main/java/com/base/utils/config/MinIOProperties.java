package com.base.utils.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "minio") //将配置文件指定前缀"minio"绑定到类的属性中,可读取自己或引入它的模块的配置文件，方便分模块配置管理
public class MinIOProperties {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String endpoint;
    private String readPath;
}
