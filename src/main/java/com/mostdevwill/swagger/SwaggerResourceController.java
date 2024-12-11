package com.mostdevwill.swagger;

import static org.springdoc.core.utils.Constants.API_DOCS_URL;

import java.io.IOException;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springdoc.core.customizers.SpringDocCustomizers;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.providers.SpringDocProviders;
import org.springdoc.core.service.AbstractRequestService;
import org.springdoc.core.service.GenericResponseService;
import org.springdoc.core.service.OpenAPIService;
import org.springdoc.core.service.OperationService;
import org.springdoc.webmvc.api.OpenApiWebMvcResource;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerResourceController extends OpenApiWebMvcResource {

    public SwaggerResourceController(String groupName, ObjectFactory<OpenAPIService> openAPIBuilderObjectFactory, AbstractRequestService requestBuilder, GenericResponseService responseBuilder, OperationService operationParser, SpringDocConfigProperties springDocConfigProperties, SpringDocProviders springDocProviders, SpringDocCustomizers springDocCustomizers) {
        super(groupName, openAPIBuilderObjectFactory, requestBuilder, responseBuilder, operationParser, springDocConfigProperties, springDocProviders, springDocCustomizers);
    }

    @Autowired
    public SwaggerResourceController(ObjectFactory<OpenAPIService> openAPIBuilderObjectFactory, AbstractRequestService requestBuilder, GenericResponseService responseBuilder, OperationService operationParser, SpringDocConfigProperties springDocConfigProperties, SpringDocProviders springDocProviders, SpringDocCustomizers springDocCustomizers) {
        super(openAPIBuilderObjectFactory, requestBuilder, responseBuilder, operationParser, springDocConfigProperties, springDocProviders, springDocCustomizers);
    }

    @Override
    public byte[] openapiJson(HttpServletRequest request, @Value(API_DOCS_URL) String apiDocsUrl, Locale locale) throws JsonProcessingException {
        byte[] annotationsOpenAPIBytes = super.openapiJson(request, apiDocsUrl, locale);
        SwaggerParseResult result = new OpenAPIParser().readLocation("static/openapi-product.yaml", null, null);
        OpenAPI fileOpenAPI = result.getOpenAPI();
        ObjectMapper objectMapper = springDocProviders.jsonMapper();
        OpenAPI annotationsOpenAPI;
        try {
            annotationsOpenAPI = objectMapper.readValue(annotationsOpenAPIBytes, OpenAPI.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Paths annotationPaths = annotationsOpenAPI.getPaths();
        Paths pathPaths = fileOpenAPI.getPaths();
        annotationPaths.putAll(pathPaths);

        return writeJsonValue(annotationsOpenAPI);
//        return annotationsOpenAPIBytes;
    }

    @GetMapping("/custom-swagger-json")
    public byte[] getJson() throws JsonProcessingException {
        SwaggerParseResult result = new OpenAPIParser().readLocation("static/openapi-product.yaml", null, null);
        OpenAPI fileOpenAPI = result.getOpenAPI();

        return writeJsonValue(fileOpenAPI);
    }
}
