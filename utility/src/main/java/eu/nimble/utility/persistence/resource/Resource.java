package eu.nimble.utility.persistence.resource;

/**
 * A POJO class to represent association of a resource with party and user.
 *
 * Created by suat on 04-Jan-19.
 */
public class Resource {
    private String repositoryName;
    private Long entityId;
    private String partyId;
    private String userId;

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
