package vn.ngthphat.camel;

import org.apache.camel.main.Main;
import vn.ngthphat.camel.processor.UploadProcessor;
import vn.ngthphat.camel.route.UploadReportRoute;

/**
 * Created by phatnguyen on 12/13/16.
 */
public class App {
    public static void main(String[] args) throws Exception {
        UploadReportRoute uploadService = new UploadReportRoute(new UploadProcessor());
        Main main = new Main();
        main.addRouteBuilder(uploadService);
        main.run(args);

        Runtime.getRuntime().addShutdownHook(new Thread(main::completed));

    }
}
