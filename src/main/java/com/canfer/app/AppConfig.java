package com.canfer.app;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;

@Configuration
@EnableJpaRepositories
public class AppConfig implements WebMvcConfigurer {
	// we need to add the argument resolvers to use the annotations for spec arg resolver.
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new SpecificationArgumentResolver());
		resolvers.add(new PageableHandlerMethodArgumentResolver());
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
	}

}
