package com.revision.springboot2026.core;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/** Collects small examples for the Spring Core demo endpoint. */
// Marks this class as application logic for Spring to discover and manage.
@Service
public class CoreDemoService {
    private final GreetingFormatter primaryFormatter;
    private final GreetingFormatter uppercaseFormatter;
    private final CoreDemoSettings settings;
    private final CoreDemoComponent singletonComponent;
    private final ObjectProvider<PrototypeNote> prototypeNotes;
    private final ApplicationContext applicationContext;

    // @Autowired asks Spring to use this constructor. With one constructor it is optional,
    // but it is included here so you can recognize the annotation in older projects.
    @Autowired
    public CoreDemoService(
            // @Primary selects this formatter when Spring sees this type without a qualifier.
            GreetingFormatter primaryFormatter,
            // @Qualifier selects the bean whose registered name matches this string.
            @Qualifier("uppercaseGreetingFormatter") GreetingFormatter uppercaseFormatter,
            CoreDemoSettings settings,
            CoreDemoComponent singletonComponent,
            ObjectProvider<PrototypeNote> prototypeNotes,
            ApplicationContext applicationContext) {
        this.primaryFormatter = primaryFormatter;
        this.uppercaseFormatter = uppercaseFormatter;
        this.settings = settings;
        this.singletonComponent = singletonComponent;
        this.prototypeNotes = prototypeNotes;
        this.applicationContext = applicationContext;
    }

    public Map<String, Object> examples() {
        // ApplicationContext extends BeanFactory, so it can also do basic bean lookups.
        BeanFactory beanFactory = applicationContext;
        GreetingFormatter formatterFromBeanFactory = beanFactory.getBean(GreetingFormatter.class);

        // Ask the singleton bean twice: both lookups should return the same object.
        CoreDemoComponent firstSingleton = applicationContext.getBean(CoreDemoComponent.class);
        CoreDemoComponent secondSingleton = applicationContext.getBean(CoreDemoComponent.class);

        // ObjectProvider asks the container for a fresh prototype each time getObject is called.
        PrototypeNote firstPrototype = prototypeNotes.getObject();
        PrototypeNote secondPrototype = prototypeNotes.getObject();

        return Map.of(
                "springBeanFactorySameAsApplicationContext", beanFactory == applicationContext,
                "beanFactoryLookup", formatterFromBeanFactory.format("BeanFactory"),
                "primaryFormatter", primaryFormatter.format("Spring"),
                "qualifiedFormatter", uppercaseFormatter.format("Spring"),
                "beanFromConfiguration", settings.message(),
                "componentDescription", singletonComponent.description(),
                "singletonLookupsReturnSameObject", firstSingleton == secondSingleton,
                "prototypeObjectIds", List.of(firstPrototype.id(), secondPrototype.id()));
    }
}
