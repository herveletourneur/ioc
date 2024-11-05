package com.herve.application;

import com.herve.application.parts.ApplicationPartsCompositionBuilder;
import com.herve.application.parts.PartsAnnotationAnnotationComposer;
import org.reflections.Reflections;

public class IoCContainer {

    private IoCContainer() {
    }

    public static ClientSideAnnotationComposer scan(String... pkg) {
        Reflections reflections = new Reflections((Object[]) pkg);
        return new PartsAnnotationAnnotationComposer(reflections, new ApplicationPartsCompositionBuilder(), new ApplicationPartsCompositionBuilder(), new ApplicationPartsCompositionBuilder());
    }
}
