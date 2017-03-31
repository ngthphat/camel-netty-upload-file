package vn.ngthphat.camel.processor;

import io.netty.handler.codec.http.multipart.*;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.netty4.http.NettyHttpMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.ngthphat.camel.dom.ResponseStatus;
import vn.ngthphat.camel.dom.ResponseWrapper;

import java.io.IOException;

/**
 * Created by phatnguyen on 12/13/16.
 */
public class UploadProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        // get netty message
        NettyHttpMessage nettyHttpMessage = exchange.getIn(NettyHttpMessage.class);
        // use HttpPostRequestDecoder to extract form data
        HttpPostRequestDecoder postRequest = new HttpPostRequestDecoder(nettyHttpMessage.getHttpRequest());
        getHttpDataAttributes(postRequest);
        // return status to client
        exchange.getIn().setBody(new ResponseWrapper(ResponseStatus.Success));
    }
    public void getHttpDataAttributes(HttpPostRequestDecoder request) {
        ResponseWrapper result = new ResponseWrapper();
        try {
            for (InterfaceHttpData part : request.getBodyHttpDatas()) {
                if (part instanceof MixedAttribute) {
                    Attribute attribute = (MixedAttribute) part;
                    LOGGER.info(String.format("Found part with key: %s and value: %s ", attribute.getName(), attribute.getValue()));
                } else if (part instanceof MixedFileUpload) {
                    MixedFileUpload attribute = (MixedFileUpload) part;
                    LOGGER.info(String.format("Found part with key: %s and value: %s ", attribute.getName(), attribute.getFilename()));
                }
            }
        } catch (IOException e) {
            String errorMsg = String.format("Cannot parse request");
            result.setMessage(errorMsg);
            LOGGER.error(errorMsg,e);
        } finally {
            request.destroy();
        }
    }
}
