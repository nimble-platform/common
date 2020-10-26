package eu.nimble.common.rest.indexing;

import feign.Response;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

public interface IIndexingServiceClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/party", consumes = "application/json", produces = "application/json")
    Response setParty(@RequestHeader("Authorization") String bearerToken, @RequestBody String party);

    @RequestMapping(method = RequestMethod.DELETE, value = "/party", produces = "application/json")
    Response removeParty(@RequestHeader("Authorization") String bearerToken, @RequestParam(value = "uri") String partyId);

    @RequestMapping(method = RequestMethod.POST, value = "/class", consumes = "application/json", produces = "application/json")
    Response setClass(@RequestHeader("Authorization") String bearerToken, @RequestBody String prop);

    @RequestMapping(method = RequestMethod.POST, value = "/class/search", consumes = "application/json", produces = "application/json")
    Response searchClass(@RequestHeader("Authorization") String bearerToken, @RequestBody String search);

    @RequestMapping(method = RequestMethod.GET, value = "/class/select", produces = "application/json")
    Response selectClass(@RequestHeader("Authorization") String bearerToken, @RequestParam(value = "rows") String rows, @RequestParam("q") String q, @RequestParam(value = "fq",required = false) Set<String> fq);

    @RequestMapping(method = RequestMethod.POST, value = "/catalogue", consumes = "application/json", produces = "application/json")
    Response postCatalogue(@RequestHeader("Authorization") String bearerToken, @RequestParam(value = "catalogueId") String catalogueId, @RequestBody String catalogueItems);

    @RequestMapping(method = RequestMethod.DELETE, value = "/catalogue", produces = "application/json")
    Response deleteCatalogue(@RequestHeader("Authorization") String bearerToken, @RequestParam("catalogueId") String catalogueId);

    @RequestMapping(method = RequestMethod.POST, value = "/item", consumes = "application/json", produces = "application/json")
    Response setItem(@RequestHeader("Authorization") String bearerToken, @RequestBody String prop);

    @RequestMapping(method = RequestMethod.DELETE, value = "/item", produces = "application/json")
    Response deleteItem(@RequestHeader("Authorization") String bearerToken, @RequestParam("uri") String uri);

    @RequestMapping(method = RequestMethod.DELETE, value = "/item/clear", produces = "application/json")
    Response clearItemIndex(@RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.POST, value = "/property", consumes = "application/json", produces = "application/json")
    Response setProperty(@RequestHeader("Authorization") String bearerToken, @RequestBody String prop);

    @RequestMapping(method = RequestMethod.GET, value = "/property", produces = "application/json")
    Response getProperty(@RequestHeader("Authorization") String bearerToken, @RequestParam(value = "uri") String uri);

    @RequestMapping(method = RequestMethod.GET, value = "/properties", produces = "application/json")
    Response getProperties(@RequestHeader("Authorization") String bearerToken,@RequestParam(value = "uri",required = false) Set<String> uris, @RequestParam(value = "class",required = false) Set<String> classUris);

    @RequestMapping(method = RequestMethod.POST, value = "/item/search", consumes = "application/json", produces = "application/json")
    Response searchItem(@RequestHeader("Authorization") String bearerToken,@RequestBody String query);

    @RequestMapping(method = RequestMethod.GET, value = "/code/select", produces = "application/json")
    Response selectCode(@RequestHeader("Authorization") String bearerToken, @RequestParam(value = "q",required = false) String query);
}
