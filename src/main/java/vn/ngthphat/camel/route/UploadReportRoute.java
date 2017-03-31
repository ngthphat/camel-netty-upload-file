package vn.ngthphat.camel.route;

import org.apache.camel.Processor;

/**
 * Created by phatnguyen on 12/13/16.
 */
public class UploadReportRoute extends BaseRestRoute{

    private Processor processor;

    public UploadReportRoute(Processor processor){
        this.processor = processor;
    }

    @Override
    public void configure() throws Exception {
        super.configure();
        rest("/upload")
                .description("Upload Multiple Report via camel and netty")
                .consumes("multipart/form-data")
                .produces("application/json")
                .post("/report")
                .to("direct:uploadReportProcessor");

        from("direct:uploadReportProcessor")
                .routeId("uploadProcessorRouteId")
                .process(processor);
    }
}
