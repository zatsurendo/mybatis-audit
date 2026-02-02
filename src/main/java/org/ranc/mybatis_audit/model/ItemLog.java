package org.ranc.mybatis_audit.model;

import org.ranc.mybatis_audit.mybatis.audit.annotation.NoAudit;
import org.ranc.mybatis_audit.mybatis.audit.model.HistoryAware;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@NoAudit
public class ItemLog extends Item implements HistoryAware {

    private Long rev;
    private Integer revtype;

    public ItemLog(Long id, String name, Integer price,Long rev) {
        super(id, name, price);
        this.rev = rev;
    }
}
