package org.ranc.mybatis_audit.model;

import org.ranc.mybatis_audit.mybatis.audit.annotation.NoAudit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@NoAudit
public class ItemAudit extends Item {
    private Long rev;

    public ItemAudit(Long id, String name, Integer price,Long rev) {
        super(id, name, price);
        this.rev = rev;
    }
}
