package com.adobe.aem.support.proxy;

import java.util.Optional;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service = Servlet.class)
@SlingServletResourceTypes(
    resourceTypes = "aem/support/proxy",
    methods = "GET",
    extensions = {"html", "json", "xml"}
)
public class ProxyServlet extends SlingSafeMethodsServlet {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String PROXY_PATH = "proxyPath";
    private final static String DEFAULT_EXTENSION = "html";
    
    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) {
        try {
            Optional<String> proxyPath = Optional.ofNullable(request.getResource().getValueMap().get(PROXY_PATH).toString());

            String proxy = null;
            if (proxyPath.isPresent()) {
                // Get the current extension used and append it to the proxyPath
                Optional<String> extension = Optional.ofNullable(request.getRequestPathInfo().getExtension());
                if (extension.isPresent()) {
                    proxy = new StringBuilder(proxyPath.get()).append(".").append(extension.get()).toString();
                } else {
                    proxy = new StringBuilder(proxyPath.get()).append(".").append(DEFAULT_EXTENSION).toString();
                }
                
                request.getRequestDispatcher(proxy).forward(request, response);
            } else {
                logger.warn("{} parameter is not set on resource {}", PROXY_PATH, request.getResource().getPath());
                response.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Failed to proxy request due to error", e);
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }


}
