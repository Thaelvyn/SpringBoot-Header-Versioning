package uk.co.webdex.versioniser;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @author Julien Frisquet
 */
public class VersioniserHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestCondition<?> getCustomMethodCondition(final Method method) {
        final Versioniser methodAnnotation = AnnotationUtils.findAnnotation(method, Versioniser.class);
        return methodAnnotation != null ? new VersioniserRequestCondition(methodAnnotation.header(), methodAnnotation.value()) : null;
    }
}
