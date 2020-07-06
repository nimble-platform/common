package eu.nimble.common.rest.indexing;

import feign.Response;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class IIndexingServiceClientFallback implements IIndexingServiceClient {


    @Override
    public Response setParty(String bearerToken, String party) {
        return null;
    }

    @Override
    public Response removeParty(String bearerToken, String partyId) {
        return null;
    }

    @Override
    public Response setClass(String bearerToken, String prop) {
        return null;
    }

    @Override
    public Response searchClass(String bearerToken, String search) {
        return null;
    }

    @Override
    public Response selectClass(String bearerToken, String rows, String q, Set<String> fq) {
        return null;
    }

    @Override
    public Response postCatalogue(String bearerToken, String catalogueId, String catalogueItems) {
        return null;
    }

    @Override
    public Response deleteCatalogue(String bearerToken, String catalogueId) {
        return null;
    }

    @Override
    public Response setItem(String bearerToken, String prop) {
        return null;
    }

    @Override
    public Response deleteItem(String bearerToken, String uri) {
        return null;
    }

    @Override
    public Response clearItemIndex(String bearerToken) {
        return null;
    }

    @Override
    public Response setProperty(String bearerToken, String prop) {
        return null;
    }

    @Override
    public Response getProperties(String bearerToken, Set<String> uris, Set<String> classUris) {
        return null;
    }

    @Override
    public Response searchItem(String bearerToken, String query) {
        return null;
    }
}
