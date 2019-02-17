package uk.co.aaditech.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
@PropertySource("classpath:swagger.properties")
public class SwaggerConfig {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("uk.co.aaditech.controllers"))
				.paths(PathSelectors.ant("/programme/**")).build().apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo("Movies Search",
				"This application demonstrates documenting os Spring Boot app with Swagger using SpringFox.", "1.0.0",
				"TERMS OF SERVICE URL",
				new Contact("Gautam Pachnanda", "http://www.aaditech.co.uk", "gpachnanda@gmail.com"), "MIT License",
				"LICENSE URL", Collections.emptyList());
	}
}
