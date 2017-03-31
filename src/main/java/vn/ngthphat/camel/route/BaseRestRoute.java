package vn.ngthphat.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

/**
 * Created by phatnguyen on 12/13/16.
 */
public class BaseRestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        getContext().setUseMDCLogging(true);
        Long maxSize = 2L * 1024 * 1024;
        restConfiguration().component("netty4-http")
                .host("localhost")
                .port("8881")
                .endpointProperty("chunkedMaxContentLength", maxSize.toString())
                .bindingMode(RestBindingMode.auto);
    }
}
