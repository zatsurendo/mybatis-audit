package org.ranc.mybatis_audit.mybatis.audit.model;

import java.time.OffsetDateTime;

public interface UseTimestamp {
    OffsetDateTime getCreatedAt();
    void setCreatedAt(OffsetDateTime createdAt);
    OffsetDateTime getUpdatedAt();
    void setUpdatedAt(OffsetDateTime updatedAt);
}
