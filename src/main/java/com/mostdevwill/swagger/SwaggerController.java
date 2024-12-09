package com.mostdevwill.swagger;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.springdoc.webmvc.ui.SwaggerConfigResource;
import org.springdoc.webmvc.ui.SwaggerWelcomeCommon;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class SwaggerController extends SwaggerConfigResource {
    /**
     * Instantiates a new Swagger config resource.
     *
     * @param swaggerWelcomeCommon the swagger welcome common
     */
    public SwaggerController(SwaggerWelcomeCommon swaggerWelcomeCommon) {
        super(swaggerWelcomeCommon);
    }

    @Override
    public Map<String, Object> openapiJson(HttpServletRequest request) {
        // вот тут мержим
        Map<String, Object> stringObjectMap = super.openapiJson(request);
        return stringObjectMap;
    }

    @GetMapping("/doc-swagger")
    public Map<String, Object> getDoc() {
        return new HashMap<>();
    }
}
