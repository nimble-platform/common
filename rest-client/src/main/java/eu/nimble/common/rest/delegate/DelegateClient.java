package eu.nimble.common.rest.delegate;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(name = "delegate-service", url = "${nimble.delegate-service.url:}")
@Profile("!test")
public interface DelegateClient extends IDelegateClient {

}