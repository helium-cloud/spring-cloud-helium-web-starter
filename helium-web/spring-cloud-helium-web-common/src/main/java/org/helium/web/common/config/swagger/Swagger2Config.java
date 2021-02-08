package org.helium.web.common.config.swagger;

import org.helium.web.common.config.CommonConfig;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author coral
 */

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Autowired
    private CommonConfig commonConfig;

    @Bean
    public Docket createRestApi() {


        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select()
                //扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(commonConfig.getTitle())
                .description(commonConfig.getDescription())
                .termsOfServiceUrl(commonConfig.getTermsOfServiceUrl())
                .contact(new Contact(commonConfig.getName(), commonConfig.getUrl(), commonConfig.getEmail()))
                .version(commonConfig.getVersion())
                .build();
    }
}
