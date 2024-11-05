package com.herve.application.parts;

import java.lang.annotation.Annotation;
import java.util.*;

public class ApplicationPartsCompositionBuilder {
    private final Set<Class<? extends Annotation>> beanToDetect = new LinkedHashSet<>();

    public ApplicationPartsCompositionBuilder add(Class<? extends Annotation> typeToScan) {
        beanToDetect.add(typeToScan);
        return this;
    }

    public ApplicationPartsCompositionBuilder add(Class<? extends Annotation>... typeToScans) {
        beanToDetect.addAll(Arrays.stream(typeToScans).toList());
        return this;
    }

    public Set<Class<? extends Annotation>> composition() {
        return Collections.unmodifiableSet(beanToDetect);
    }

}
