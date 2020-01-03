package eu.nimble.common.rest.delegate;

import feign.Response;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.GET, value = "/collaboration-groups/{id}", produces = "application/json")
    Response getCollaborationGroup(@RequestHeader("Authorization") String bearerToken,
                                   @PathVariable("id") String id,
                      @RequestParam("delegateId") String delegateId);

    @RequestMapping(method = RequestMethod.GET, value = "/collaboration-groups/unmerge", produces = "application/json")
    Response unMergeCollaborationGroup(@RequestHeader("Authorization") String bearerToken,
                                       @PathVariable("groupId") String groupId,
                                   @RequestParam("delegateId") String delegateId);

}