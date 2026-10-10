package com.revision.springboot2026.core;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** A plain component used to demonstrate Spring's default singleton scope. */
// @Component asks component scanning to register this class as a bean.
@Component
// Singleton is also the default, but writing it here makes the scope lesson visible.
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CoreDemoComponent {
    public String description() {
        return "This component is one shared singleton bean in this application context.";
    }
}
