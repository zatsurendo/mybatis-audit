package org.ranc.mybatis_audit.mybatis.audit;

import java.time.Instant;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.ranc.mybatis_audit.model.Revinfo;
import org.ranc.mybatis_audit.repository.RevinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private RevinfoMapper revinfoMapper;

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {

        // トランザクションIDが既に設定されている場合は、ネストされたトランザクションなので何もしない
        if (AuditContextHolder.getTransactionId() != null) {
            return joinPoint.proceed();
        }

        try {
            // HttpRequestからユーザ情報等を取得してセット(ダミー)
            AuditContext auditContext = new AuditContext();
            auditContext.setRemoteHost("dummy host");
            auditContext.setRemoteUser("dummy User");
            // 一意のトランザクションIDを生成してセット
            Revinfo revinfo = new Revinfo();
            revinfo.setRevtstmp(Instant.now().toEpochMilli());
            revinfoMapper.newRev(revinfo);
            auditContext.setRevinfo(revinfo);
            AuditContextHolder.setTransactionId(auditContext);

            // 本来のメソッドを実行
            return joinPoint.proceed();
        } finally {
            // ThreadLocalの値をクリア
            AuditContextHolder.clear();
        }
    }
}
