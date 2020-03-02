package eu.nimble.common.rest.delegate;

import feign.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IDelegateClient {
    @RequestMapping(method = RequestMethod.POST, value = "/collaboration-groups/document/{documentId}",consumes = "application/json",produces = "application/json")
    Response addFederatedMetadataToCollaborationGroup(@RequestHeader("Authorization") String bearerToken,
                                                      @RequestHeader("federationId") String federationId,
                                                      @PathVariable("documentId") String documentId,
                                                      @RequestBody String body,
                                                      @RequestParam("partyId") String partyId,
                                                      @RequestParam("delegateId") String delegateId);

    @RequestMapping(method = RequestMethod.GET, value = "/document/{documentId}/group-id-tuple", produces = "application/json")
    Response getGroupIdTuple(@RequestHeader("Authorization") String bearerToken,
                             @RequestHeader("federationId") String federationId,
                             @PathVariable("documentId") String documentId,
                             @RequestParam("partyId") String partyId,
                             @RequestParam("delegateId") String delegateId);

    @RequestMapping(method = RequestMethod.GET, value = "/process-instance-groups/order-document", produces = "application/json")
    Response getOrderDocument(@RequestHeader("Authorization") String bearerToken,
                              @RequestParam("processInstanceId") String processInstanceId,
                              @RequestParam("orderResponseId") String orderResponseId,
                              @RequestParam("delegateId") String delegateId);

    @RequestMapping(method = RequestMethod.GET, value = "/party/{partyId}", produces = "application/json")
    Response getParty(@RequestHeader("Authorization") String bearerToken,
                      @PathVariable("partyId") Long partyId,
                      @RequestParam("includeRoles") boolean includeRoles,
                      @RequestParam("delegateId") String delegateId);

    @RequestMapping(method = RequestMethod.GET, value = "/parties/{partyIds}", produces = "application/json")
    Response getParty(@RequestHeader("Authorization") String bearerToken,
                      @PathVariable("partyIds") String partyIds,
                      @RequestParam("includeRoles") boolean includeRoles,
                      @RequestParam("delegateIds") List<String> delegateIds);

    @RequestMapping(method = RequestMethod.GET, value = "/person/{personId}", produces = "application/json")
    Response getPerson(@RequestHeader("Authorization") String bearerToken,
                      @PathVariable("personId") String personId,
                      @RequestParam("delegateId") String delegateId);

    @RequestMapping(method = RequestMethod.GET, value = "/collaboration-groups/{id}", produces = "application/json")
    Response getCollaborationGroup(@RequestHeader("Authorization") String bearerToken,
                                   @PathVariable("id") String id,
                      @RequestParam("delegateId") String delegateId);

    @RequestMapping(method = RequestMethod.GET, value = "/collaboration-groups/unmerge", produces = "application/json")
    Response unMergeCollaborationGroup(@RequestHeader("Authorization") String bearerToken,
                                       @RequestParam("groupId") String groupId,
                                   @RequestParam("delegateId") String delegateId);

    @RequestMapping(method = RequestMethod.GET, value = "/catalogueline/{hjid}", produces = "application/json")
    Response getCatalogLineByHjid(@RequestHeader("Authorization") String bearerToken,
                                       @PathVariable("hjid") Long hjid);

    @RequestMapping(method = RequestMethod.GET, value = "/documents/expected-orders", produces = "application/json")
    Response getExpectedOrders(@RequestHeader("Authorization") String bearerToken,
                               @RequestParam("forAll") Boolean forAll,
                                  @RequestParam("unShippedOrderIds") List<String> unShippedOrderIds);

    @RequestMapping(method = RequestMethod.GET, value = "/eureka/app-name", produces = "application/json")
    Response getFederationId();
}