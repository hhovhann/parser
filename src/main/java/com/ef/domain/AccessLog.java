package com.ef.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCESS_LOG")
public class AccessLog {
    @Id
    @Column(name = "LOG_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long logId;

    @Column(name = "IP_ADDRESS", nullable = false)
    private String ipAddress;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "REQUEST")
    private String request;

    @Column(name = "STATUS")
    private int status;

    @Column(name = "USER_AGENT")
    private String userAgent;

    public AccessLog(String ipAddress, LocalDateTime startDate, String request, int status, String userAgent) {
        this.ipAddress = ipAddress;
        this.startDate = startDate;
        this.request = request;
        this.status = status;
        this.userAgent = userAgent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        AccessLog rhs = (AccessLog) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(ipAddress, rhs.ipAddress)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(ipAddress).
                toHashCode();
    }
}