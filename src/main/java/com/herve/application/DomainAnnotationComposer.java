package com.herve.application;

import com.herve.application.parts.ApplicationPartsCompositionBuilder;

import java.util.function.Consumer;

public interface DomainAnnotationComposer {
    UserSideAnnotationComposer domainUse(Consumer<ApplicationPartsCompositionBuilder> domainAnnatation);
}
