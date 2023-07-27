package com.techvg.covid.care.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.techvg.covid.care.web.rest.TestUtil;

public class AuditTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditType.class);
        AuditType auditType1 = new AuditType();
        auditType1.setId(1L);
        AuditType auditType2 = new AuditType();
        auditType2.setId(auditType1.getId());
        assertThat(auditType1).isEqualTo(auditType2);
        auditType2.setId(2L);
        assertThat(auditType1).isNotEqualTo(auditType2);
        auditType1.setId(null);
        assertThat(auditType1).isNotEqualTo(auditType2);
    }
}
