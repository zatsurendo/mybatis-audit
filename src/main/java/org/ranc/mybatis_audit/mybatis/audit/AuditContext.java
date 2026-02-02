package org.ranc.mybatis_audit.mybatis.audit;

import org.ranc.mybatis_audit.model.Revinfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Audit context holding revision information and user/host details.
 * @author ranc
 * @date 2026-02-02 10:17:20
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AuditContext {

    private Revinfo revinfo;
    private String remoteUser;
    private String remoteHost;

}
