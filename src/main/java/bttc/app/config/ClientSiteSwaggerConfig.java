package bttc.app.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class ClientSiteSwaggerConfig {
    @Bean
    public Docket apiDocsConfig() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("bttc.app"))
                .paths(PathSelectors.any()).build()
                .globalOperationParameters(setupGlobalOperationParametersAndHeaders())
                .apiInfo(metaData());
    }

    private List<Parameter> setupGlobalOperationParametersAndHeaders() {
        return newArrayList(
                new ParameterBuilder().name("Authorization")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .build());

    }

    private ApiInfo metaData() {
      return new ApiInfo(
                "BOITSANAPE REST API",
                "Spring Boot REST API for Boitsanape Business Solution",
                "1.0",
                "Terms of service",
                new Contact(
                        "Thabiso Mokhele",
                        "http://boitsanape.co.za",
                        "tmokhele@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
    }
}
