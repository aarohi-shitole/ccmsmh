package com.techvg.covid.care.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.techvg.covid.care.web.rest.TestUtil;

public class SecurityPermissionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecurityPermission.class);
        SecurityPermission securityPermission1 = new SecurityPermission();
        securityPermission1.setId(1L);
        SecurityPermission securityPermission2 = new SecurityPermission();
        securityPermission2.setId(securityPermission1.getId());
        assertThat(securityPermission1).isEqualTo(securityPermission2);
        securityPermission2.setId(2L);
        assertThat(securityPermission1).isNotEqualTo(securityPermission2);
        securityPermission1.setId(null);
        assertThat(securityPermission1).isNotEqualTo(securityPermission2);
    }
}
