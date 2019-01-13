package api.javajuke.data.handler;

import api.javajuke.data.annotation.ApiVersion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.stream.IntStream;

public class ApiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private final String prefix;

    @Value("${juke.api.version}")
    private int latestVersion;

    public ApiVersionRequestMappingHandlerMapping(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Overrides the base method by adding a check if the method contains an ApiVersion
     * annotation. If the annotation exists, the versions that are set in the annotation will be
     * combined into the existing RequestMappingInfo patterns for that method.
     *
     * @param method the current method
     * @param handlerType the current handler type
     *
     * @return the default RequestMappingInfo object or a combined one with new version patterns
     */
    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
        if(info == null) return null;

        ApiVersion methodAnnotation = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        // Check if the method has the ApiVersion annotation
        if(methodAnnotation != null) {
            RequestCondition<?> methodCondition = getCustomMethodCondition(method);
            // Concatenate our ApiVersion with the usual request mapping
            info = createApiVersionInfo(methodAnnotation, methodCondition).combine(info);
        } else {
            ApiVersion typeAnnotation = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
            // Check if the controller has the ApiVersion annotation
            if(typeAnnotation != null) {
                RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);
                // Concatenate our ApiVersion with the usual request mapping
                info = createApiVersionInfo(typeAnnotation, typeCondition).combine(info);
            }
        }

        return info;
    }

    /**
     * Creates a new RequestMappingInfo object containing patterns for each version which
     * is specified in the ApiVersion annotation.
     *
     * @param annotation the ApiVersion annotation which was used
     * @param customCondition the custom condition based on the handler type
     *
     * @return RequestMappingInfo object with patterns containing the versions specified in the annotation
     */
    private RequestMappingInfo createApiVersionInfo(ApiVersion annotation, RequestCondition<?> customCondition) {
        int[] values = annotation.value();
        // Check if the versions contain the latest version, and if so, create an empty slot in the array
        int length = IntStream.of(values).anyMatch(x -> x == latestVersion) ? values.length + 1 : values.length;
        String[] patterns = new String[length];
        for(int i = 0; i < values.length; i++) {
            // Build the URL prefix
            patterns[i] = prefix + values[i];
        }

        return new RequestMappingInfo(
                new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(), useSuffixPatternMatch(), useTrailingSlashMatch(), getFileExtensions()),
                new RequestMethodsRequestCondition(),
                new ParamsRequestCondition(),
                new HeadersRequestCondition(),
                new ConsumesRequestCondition(),
                new ProducesRequestCondition(),
                customCondition);
    }
}
