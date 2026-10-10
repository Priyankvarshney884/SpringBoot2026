package com.revision.bootpoc.autoconfigure;

import com.revision.bootpoc.BootDemoFeature;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/** A tiny real auto-configuration candidate used to see Boot's conditional bean behavior. */
// Boot discovers this class from META-INF/spring/...AutoConfiguration.imports.
@AutoConfiguration
// Only activate this configuration when the web stack is available on the classpath.
@ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
// Users can turn this demo off with --boot-demo.enabled=false.
@ConditionalOnProperty(prefix = "boot-demo", name = "enabled", havingValue = "true", matchIfMissing = true)
public class BootDemoAutoConfiguration {

    // Supply a default bean only when the application has not provided its own replacement.
    @Bean
    @ConditionalOnMissingBean(BootDemoFeature.class)
    public BootDemoFeature bootDemoFeature() {
        return new BootDemoFeature("Created by conditional Spring Boot auto-configuration");
    }
}
