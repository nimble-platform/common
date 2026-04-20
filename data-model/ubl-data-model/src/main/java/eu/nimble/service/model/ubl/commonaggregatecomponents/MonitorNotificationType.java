package eu.nimble.service.model.ubl.commonaggregatecomponents;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;

/**
 * HCDP-04-02: Activity Monitor persistent notification (inbox entry).
 * Created by the Angular MonitorService when a state change or anomaly is detected.
 * Persists in the DB so the inbox survives page refreshes. Dismissed by user manually.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MonitorNotificationType", propOrder = {
    "userId",
    "watchlistEntryHjid",
    "notificationType",
    "severity",
    "title",
    "message",
    "relatedProcessId",
    "createdAt",
    "readAt",
    "dismissedAt"
})
@Entity(name = "MonitorNotificationType")
@Table(name = "MONITOR_NOTIFICATION_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public class MonitorNotificationType implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "UserId", required = true)
    protected String userId;

    /** FK to WatchlistEntryType.hjid — which watch triggered this notification */
    @XmlElement(name = "WatchlistEntryHjid")
    protected Long watchlistEntryHjid;

    /** "STATUS_CHANGE" | "NEW_PROCESS" | "ANOMALY_DELAY" */
    @XmlElement(name = "NotificationType", required = true)
    protected String notificationType;

    /** "INFO" | "WARNING" | "CRITICAL" */
    @XmlElement(name = "Severity", required = true)
    protected String severity;

    @XmlElement(name = "Title", required = true)
    protected String title;

    @XmlElement(name = "Message")
    protected String message;

    /** processInstanceId for click-through navigation to the BP */
    @XmlElement(name = "RelatedProcessId")
    protected String relatedProcessId;

    @XmlElement(name = "CreatedAt")
    protected Date createdAt;

    /** null = unread; set when user opens inbox or clicks "Mark all read" */
    @XmlElement(name = "ReadAt")
    protected Date readAt;

    /** null = visible; set when user dismisses the notification */
    @XmlElement(name = "DismissedAt")
    protected Date dismissedAt;

    @XmlAttribute(name = "Hjid")
    protected Long hjid;

    // ---- Getters / Setters ----

    @Basic
    @Column(name = "USER_ID", length = 255, nullable = false)
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    @Basic
    @Column(name = "WATCHLIST_ENTRY_HJID")
    public Long getWatchlistEntryHjid() { return watchlistEntryHjid; }
    public void setWatchlistEntryHjid(Long watchlistEntryHjid) { this.watchlistEntryHjid = watchlistEntryHjid; }

    @Basic
    @Column(name = "NOTIFICATION_TYPE", length = 30, nullable = false)
    public String getNotificationType() { return notificationType; }
    public void setNotificationType(String notificationType) { this.notificationType = notificationType; }

    @Basic
    @Column(name = "SEVERITY", length = 10, nullable = false)
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    @Basic
    @Column(name = "TITLE", length = 255, nullable = false)
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @Basic
    @Column(name = "MESSAGE", length = 1000)
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Basic
    @Column(name = "RELATED_PROCESS_ID", length = 255)
    public String getRelatedProcessId() { return relatedProcessId; }
    public void setRelatedProcessId(String relatedProcessId) { this.relatedProcessId = relatedProcessId; }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "READ_AT")
    public Date getReadAt() { return readAt; }
    public void setReadAt(Date readAt) { this.readAt = readAt; }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DISMISSED_AT")
    public Date getDismissedAt() { return dismissedAt; }
    public void setDismissedAt(Date dismissedAt) { this.dismissedAt = dismissedAt; }

    @Id
    @Column(name = "HJID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getHjid() { return hjid; }
    public void setHjid(Long hjid) { this.hjid = hjid; }
}
