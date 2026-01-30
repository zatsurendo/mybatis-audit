package org.ranc.mybatis_audit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Revinfo {
    private Long rev;
    private Long revtstmp;
}
