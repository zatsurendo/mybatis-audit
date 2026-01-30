package org.ranc.mybatis_audit.model;

import java.time.OffsetDateTime;

import org.ranc.mybatis_audit.mybatis.audit.model.AbstractHistoryAware;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Item extends AbstractHistoryAware {

    private Long id;
    private String name;
    private Integer price;

    public Item(Long id, String name, Integer price) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Item(Long id, String name, Integer price, Long version, Integer revtype, String createdBy, String updatedBy,
            OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        super(version, revtype, createdBy, updatedBy, createdAt, updatedAt);
        this.id = id;
        this.name = name;
        this.price = price;
    }

}
