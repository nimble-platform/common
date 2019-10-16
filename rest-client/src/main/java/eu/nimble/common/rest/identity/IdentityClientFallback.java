package eu.nimble.common.rest.identity;

import java.util.List;

import feign.Response;

public class IdentityClientFallback implements IdentityClient {

	@Override
	public Response getParty(String bearerToken, String storeId) {
		return null;
	}

	@Override
	public Response getParty(String bearerToken, String storeId, boolean includeRoles) {
		return null;
	}

	@Override
	public Response getParties(String bearerToken, String partyIds) {
		return null;
	}

	@Override
	public Response getPartyByPersonID(String personId) {
		return null;
	}

	@Override
	public Response getAllPartyIds(String bearerToken, List<String> exclude) {
		return null;
	}

	@Override
	public Response getPartyPartiesInUBL(String bearerToken, String page, String includeRoles, String size) {
		return null;
	}

	@Override
	public Response getPerson(String bearerToken, String personId) {
		return null;
	}

	@Override
	public Response getPerson(String bearerToken) {
		return null;
	}

	@Override
	public Response getUserInfo(String bearerToken) {
		return null;
	}

	@Override
	public Response getNegotiationSettings(String companyID) {
		return null;
	}
}
