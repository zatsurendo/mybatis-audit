package org.ranc.mybatis_audit.model;

import java.time.OffsetDateTime;

import org.ranc.mybatis_audit.mybatis.audit.annotation.HistoryPersistable;
import org.ranc.mybatis_audit.mybatis.audit.model.AbstractAuditAware;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@HistoryPersistable
public class Item extends AbstractAuditAware {

    private Long id;
    private String name;
    private Integer price;

    public Item(Long id, String name, Integer price) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Item(Long id, String name, Integer price, Long version, String createdBy, String updatedBy,
            OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        super(version, createdBy, updatedBy, createdAt, updatedAt);
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
