package com.herve.application.parts;

import com.herve.application.ClientSideAnnotationComposer;
import com.herve.application.DefaultApplicationBuilder;
import com.herve.application.DomainAnnotationComposer;
import com.herve.application.UserSideAnnotationComposer;
import org.reflections.Reflections;

import java.util.function.Consumer;

public class PartsAnnotationAnnotationComposer implements ClientSideAnnotationComposer, DomainAnnotationComposer, UserSideAnnotationComposer {
    private final Reflections reflections;
    private final ApplicationPartsCompositionBuilder clientSideCompositionBuilder;
    private final ApplicationPartsCompositionBuilder domainCompositionBuilder;
    private final ApplicationPartsCompositionBuilder userSideCompositionBuilder;

    public PartsAnnotationAnnotationComposer(Reflections reflections, ApplicationPartsCompositionBuilder clientSideCompositionBuilder, ApplicationPartsCompositionBuilder domainCompositionBuilder, ApplicationPartsCompositionBuilder userSideCompositionBuilder) {
        this.reflections = reflections;
        this.clientSideCompositionBuilder = clientSideCompositionBuilder;
        this.domainCompositionBuilder = domainCompositionBuilder;
        this.userSideCompositionBuilder = userSideCompositionBuilder;
    }

    @Override
    public DomainAnnotationComposer clientSideUse(Consumer<ApplicationPartsCompositionBuilder> clientSideCustomizer) {
        clientSideCustomizer.accept(this.clientSideCompositionBuilder);
        return this;
    }

    @Override
    public UserSideAnnotationComposer domainUse(Consumer<ApplicationPartsCompositionBuilder> domainAnnatation) {
        domainAnnatation.accept(this.domainCompositionBuilder);
        return this;
    }

    @Override
    public DefaultApplicationBuilder userSideUse(Consumer<ApplicationPartsCompositionBuilder> userSideAnnotation) {
        userSideAnnotation.accept(this.userSideCompositionBuilder);
        ApplicationParts applicationParts = new ApplicationParts(clientSideCompositionBuilder.composition(), domainCompositionBuilder.composition(), this.userSideCompositionBuilder.composition());
        return new DefaultApplicationBuilder(reflections, applicationParts);
    }
}
