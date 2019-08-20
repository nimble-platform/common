package eu.nimble.common.rest.datachannel;

import feign.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IDataChannelClient {
    @RequestMapping(method = RequestMethod.GET, value = "/epc/list", produces = "application/json")
    Response getEPCCodesForOrders(@RequestHeader("Authorization") String bearerToken, @RequestParam(value = "orders") List<String> orderIds);

    @RequestMapping(method = RequestMethod.GET, value = "/epc/code/{code}", produces = "application/json")
    Response getEPCCodesForOrder(@RequestHeader("Authorization") String bearerToken, @PathVariable("code") String storeId);

    @RequestMapping(method = RequestMethod.POST, value = "/channel/", produces = "application/json", consumes = "application/json")
    Response createChannel(@RequestHeader("Authorization") String bearerToken, @RequestBody String request);
}