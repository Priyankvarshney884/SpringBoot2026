package com.revision.springboot2026.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Java-based configuration for objects that are not created by component scanning. */
// @Configuration tells Spring this class contains bean factory methods.
@Configuration
public class CoreDemoConfiguration {

    // @Bean tells Spring to call this method and manage the returned object as a bean.
    // initMethod/destroyMethod let us see the start and shutdown parts of bean lifecycle.
    @Bean(initMethod = "initialize", destroyMethod = "cleanup")
    public CoreLifecycleProbe coreLifecycleProbe() {
        return new CoreLifecycleProbe();
    }

    // The method name becomes the bean name: "coreDemoSettings".
    @Bean
    public CoreDemoSettings coreDemoSettings() {
        return new CoreDemoSettings("Created by a @Bean method");
    }
}
