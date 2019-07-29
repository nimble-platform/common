package eu.nimble.common.rest.datachannel;

import feign.Response;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "data-channel-service", url = "${nimble.data-channel.url:}")
@Profile("!test")
public interface DataChannelClient extends IDataChannelClient {

}