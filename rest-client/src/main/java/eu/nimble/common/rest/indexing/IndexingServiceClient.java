package eu.nimble.common.rest.indexing;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(name = "indexing-service", url = "${nimble.indexing.url:}")
@Profile("!test")
public interface IndexingServiceClient extends IIndexingServiceClient{
}