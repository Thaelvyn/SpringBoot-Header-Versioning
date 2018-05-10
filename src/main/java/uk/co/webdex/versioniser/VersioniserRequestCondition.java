package uk.co.webdex.versioniser;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Julien Frisquet
 */
public class VersioniserRequestCondition implements RequestCondition<VersioniserRequestCondition> {

    private final Set<String> acceptHeaderValues;
    private final String headerName;

    public VersioniserRequestCondition(final String headerName, final String ...acceptHeaderValues) {
        this(headerName, Arrays.asList(acceptHeaderValues));
    }

    public VersioniserRequestCondition(final String headerName, final Collection<String> acceptHeaderValues) {
        this.acceptHeaderValues = Collections.unmodifiableSet(new HashSet<>(acceptHeaderValues));
        this.headerName = headerName;
    }

    @Override
    public VersioniserRequestCondition combine(final VersioniserRequestCondition other) {
        final Set<String> allAcceptedHeaders = new LinkedHashSet<>(acceptHeaderValues);
        allAcceptedHeaders.addAll(other.acceptHeaderValues);
        return new VersioniserRequestCondition(headerName, allAcceptedHeaders);
    }

    @Override
    public VersioniserRequestCondition getMatchingCondition(final HttpServletRequest request) {
        final String header = request.getHeader(headerName);
        if (StringUtils.isEmpty(header)) {
            return null;
        }

        final Set<String> headerValues = new HashSet<>(Arrays.asList(header.split(",")));

        if (headerValues.containsAll(acceptHeaderValues)) {
            return this;
        }
        return null;
    }

    @Override
    public int compareTo(final VersioniserRequestCondition other, final HttpServletRequest request) {
        return other.acceptHeaderValues.size() - acceptHeaderValues.size();
    }
}
