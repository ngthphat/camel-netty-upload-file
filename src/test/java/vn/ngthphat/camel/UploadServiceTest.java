package vn.ngthphat.camel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.camel.*;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.netty4.http.NettyHttpMessage;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import vn.ngthphat.camel.dom.ResponseStatus;
import vn.ngthphat.camel.dom.ResponseWrapper;
import vn.ngthphat.camel.processor.UploadProcessor;
import vn.ngthphat.camel.route.UploadReportRoute;

import java.io.IOException;

/**
 * @author phatnguyen
 */
public class UploadServiceTest extends CamelTestSupport {
    @Produce(uri = "direct:mockProducerRoute")
    protected ProducerTemplate templateProducerRoute;
    @EndpointInject(uri = "mock:mockEndRoute")
    protected MockEndpoint mockEndRoute;
    @Before
    public void mockEndpoints() throws Exception {
        AdviceWithRouteBuilder routeMockEndpoint = new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith(templateProducerRoute.getDefaultEndpoint().getEndpointUri());
                interceptSendToEndpoint("uploadProcessorRoute")
                        .skipSendToOriginalEndpoint()
                        .to(mockEndRoute);
            }
        };
        context.getRouteDefinition("uploadProcessorRouteId").adviceWith(context, routeMockEndpoint);
    }
    @Override
    protected RoutesBuilder[] createRouteBuilders() throws Exception {
        return new RoutesBuilder[]{
                new UploadReportRoute(new UploadProcessor())};
    }
    @Test
    public void test_valid_upload_report() throws IOException {
        // expected result
        ResponseStatus status = ResponseStatus.Success;
        // input
        NettyHttpMessage message = null;
        final String body = "------WebKitFormBoundaryc6XX8D4x3Q3dWGwo\n" +
                "Content-Disposition: form-data; name=\"paymentMethod\"\n" +
                "\n" +
                "ippOcbc\n" +
                "------WebKitFormBoundaryc6XX8D4x3Q3dWGwo\n" +
                "Content-Disposition: form-data; name=\"file\"; filename=\"report_file_name.csv\"\n" +
                "Content-Type: text/csv\n" +
                "\n" +
                "KodePembayaran,Nomimal,WaktuPembayaran,OrderID,StoreId,TrxToko,BiayaTransaksi,PPN,NetAmount\n" +
                "TZTQ36050090,20000,2016-11-08 10:25:02,ff72yAAjfeVgiuc9,G020T38X,151568820880,240.00000000,24.000000000,19736.0000\n" +
                "\n" +
                "------WebKitFormBoundaryc6XX8D4x3Q3dWGwo--";
        ByteBuf byteBuf = Unpooled.copiedBuffer(body, CharsetUtil.UTF_8);
        FullHttpRequest httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "uri", byteBuf);
        httpRequest.headers().add("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundaryc6XX8D4x3Q3dWGwo");
        message = new NettyHttpMessage(httpRequest,
                new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.ACCEPTED));
        message.setHeader(Exchange.HTTP_METHOD, HttpMethod.POST);
        final Exchange ex = new DefaultExchange(context);
        ex.setIn(message);
        ResponseWrapper out = templateProducerRoute.send(ex).getIn().getBody(ResponseWrapper.class);
        assertThat(out.getStatus(), Is.is(ResponseStatus.Success));
    }
}
