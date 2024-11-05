package com.herve.application.parts;

import com.herve.application.configuration.ConfigurationBeanCreationException;

import java.lang.annotation.Annotation;
import java.util.*;

public record ApplicationParts(Set<Class<? extends Annotation>> clientSideTypeToDetect, Set<Class<? extends Annotation>> domainTypeToDetect, Set<Class<? extends Annotation>> userSideTypeToDetect) {
    public ApplicationParts {
        int size = clientSideTypeToDetect.size() + domainTypeToDetect.size() + userSideTypeToDetect.size();
        var uniques = new HashSet<>(clientSideTypeToDetect);
        uniques.addAll(domainTypeToDetect);
        uniques.addAll(userSideTypeToDetect);
        if (uniques.size() != size) {
            throw new ConfigurationBeanCreationException("Layers have common type for injection detection. Layer have to be segregate");
        }
    }


    public List<Class<? extends Annotation>> allTypeToDetect() {
        List<Class<? extends Annotation>> types = new ArrayList<>();
        types.addAll(clientSideTypeToDetect);
        types.addAll(domainTypeToDetect);
        types.addAll(userSideTypeToDetect);
        return types;
    }
}
