package eu.nimble.service.model.ubl.commonaggregatecomponents;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;

/**
 * HCDP-04-02: Activity Monitor Watchlist entry.
 * Stores a user's subscription to monitor a specific Business Process or Partner.
 * Used by the Monitor tab in the Dashboard to detect state changes lazily.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WatchlistEntryType", propOrder = {
    "userId",
    "watchType",
    "targetId",
    "targetLabel",
    "lastSeenStatus",
    "lastSeenBpCount",
    "lastAnomalyAlerted",
    "createdAt"
})
@Entity(name = "WatchlistEntryType")
@Table(name = "WATCHLIST_ENTRY_TYPE",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_watchlist_user_type_target",
        columnNames = {"USER_ID", "WATCH_TYPE", "TARGET_ID"}
    )
)
@Inheritance(strategy = InheritanceType.JOINED)
public class WatchlistEntryType implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "UserId", required = true)
    protected String userId;

    /** "BUSINESS_PROCESS" or "PARTNER" */
    @XmlElement(name = "WatchType", required = true)
    protected String watchType;

    /** processInstanceId (for BP watch) or partyId (for partner watch) */
    @XmlElement(name = "TargetId", required = true)
    protected String targetId;

    /** Denormalized human-readable label, e.g. "Order with BiomassEnergy Ltd" */
    @XmlElement(name = "TargetLabel")
    protected String targetLabel;

    /** Last known status string for BP watch (e.g. "WaitingResponse", "Approved") */
    @XmlElement(name = "LastSeenStatus")
    protected String lastSeenStatus;

    /** Last known BP count for Partner watch — new BPs detected when count increases */
    @XmlElement(name = "LastSeenBpCount")
    protected Integer lastSeenBpCount;

    /** True after an ANOMALY_DELAY notification was already created for this entry */
    @XmlElement(name = "LastAnomalyAlerted")
    protected Boolean lastAnomalyAlerted;

    @XmlElement(name = "CreatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;

    @XmlAttribute(name = "Hjid")
    protected Long hjid;

    // ---- Getters / Setters ----

    @Basic
    @Column(name = "USER_ID", length = 255, nullable = false)
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    @Basic
    @Column(name = "WATCH_TYPE", length = 30, nullable = false)
    public String getWatchType() { return watchType; }
    public void setWatchType(String watchType) { this.watchType = watchType; }

    @Basic
    @Column(name = "TARGET_ID", length = 255, nullable = false)
    public String getTargetId() { return targetId; }
    public void setTargetId(String targetId) { this.targetId = targetId; }

    @Basic
    @Column(name = "TARGET_LABEL", length = 512)
    public String getTargetLabel() { return targetLabel; }
    public void setTargetLabel(String targetLabel) { this.targetLabel = targetLabel; }

    @Basic
    @Column(name = "LAST_SEEN_STATUS", length = 64)
    public String getLastSeenStatus() { return lastSeenStatus; }
    public void setLastSeenStatus(String lastSeenStatus) { this.lastSeenStatus = lastSeenStatus; }

    @Basic
    @Column(name = "LAST_SEEN_BP_COUNT")
    public Integer getLastSeenBpCount() { return lastSeenBpCount; }
    public void setLastSeenBpCount(Integer lastSeenBpCount) { this.lastSeenBpCount = lastSeenBpCount; }

    @Basic
    @Column(name = "LAST_ANOMALY_ALERTED")
    public Boolean getLastAnomalyAlerted() { return lastAnomalyAlerted; }
    public void setLastAnomalyAlerted(Boolean lastAnomalyAlerted) { this.lastAnomalyAlerted = lastAnomalyAlerted; }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    @Id
    @Column(name = "HJID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getHjid() { return hjid; }
    public void setHjid(Long hjid) { this.hjid = hjid; }
}
