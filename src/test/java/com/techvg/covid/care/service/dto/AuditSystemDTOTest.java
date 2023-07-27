package com.techvg.covid.care.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.techvg.covid.care.web.rest.TestUtil;

public class AuditSystemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditSystemDTO.class);
        AuditSystemDTO auditSystemDTO1 = new AuditSystemDTO();
        auditSystemDTO1.setId(1L);
        AuditSystemDTO auditSystemDTO2 = new AuditSystemDTO();
        assertThat(auditSystemDTO1).isNotEqualTo(auditSystemDTO2);
        auditSystemDTO2.setId(auditSystemDTO1.getId());
        assertThat(auditSystemDTO1).isEqualTo(auditSystemDTO2);
        auditSystemDTO2.setId(2L);
        assertThat(auditSystemDTO1).isNotEqualTo(auditSystemDTO2);
        auditSystemDTO1.setId(null);
        assertThat(auditSystemDTO1).isNotEqualTo(auditSystemDTO2);
    }
}
