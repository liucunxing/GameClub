package com.base.utils.config;

import com.base.utils.common.FileStorageUtils;
import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConditionalOnClass(FileStorageUtils.class)//当FileStorageService类存在时，配置文件才加载，也就是当Service被依赖注入使用时,减少bean创建开销
public class MinIOConfig {
    @Autowired
    private MinIOProperties minIOProperties;

    @Bean
    public MinioClient buildMinioClient() {
        return MinioClient
                .builder()
                .credentials(minIOProperties.getAccessKey(), minIOProperties.getSecretKey())
                .endpoint(minIOProperties.getEndpoint())
                .build();
    }
}
