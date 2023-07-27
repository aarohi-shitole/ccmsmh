package com.techvg.covid.care.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.techvg.covid.care.web.rest.TestUtil;

public class SecurityRoleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecurityRole.class);
        SecurityRole securityRole1 = new SecurityRole();
        securityRole1.setId(1L);
        SecurityRole securityRole2 = new SecurityRole();
        securityRole2.setId(securityRole1.getId());
        assertThat(securityRole1).isEqualTo(securityRole2);
        securityRole2.setId(2L);
        assertThat(securityRole1).isNotEqualTo(securityRole2);
        securityRole1.setId(null);
        assertThat(securityRole1).isNotEqualTo(securityRole2);
    }
}
