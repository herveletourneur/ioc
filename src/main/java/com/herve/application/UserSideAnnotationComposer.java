package com.herve.application;

import com.herve.application.parts.ApplicationPartsCompositionBuilder;

import java.util.function.Consumer;

public interface UserSideAnnotationComposer {
    ApplicationBuilder userSideUse(Consumer<ApplicationPartsCompositionBuilder> userSideAnnotation);
}
