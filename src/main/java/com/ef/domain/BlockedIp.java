package com.ef.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BLOCKED_IP")
public class BlockedIp {
    @Id
    @Column(name = "IP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ipId;

    @Column(name = "IP_ADDRESS", nullable = false)
    private String ipAddress;

    @Column(name = "COMMENTS", nullable = false)
    private String comments;

    public BlockedIp(String ipAddress, String comments) {
        this.ipAddress = ipAddress;
        this.comments = comments;
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
        BlockedIp rhs = (BlockedIp) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(ipId, rhs.ipId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(ipId).
                toHashCode();
    }
}
