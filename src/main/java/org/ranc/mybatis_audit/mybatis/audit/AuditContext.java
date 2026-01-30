package org.ranc.mybatis_audit.mybatis.audit;

import org.ranc.mybatis_audit.model.Revinfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AuditContext {
    private Revinfo revinfo;
    private String remoteUser;
    private String remoteHost;
}
