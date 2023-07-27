package com.techvg.covid.care.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.techvg.covid.care.web.rest.TestUtil;

public class SecurityUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecurityUser.class);
        SecurityUser securityUser1 = new SecurityUser();
        securityUser1.setId(1L);
        SecurityUser securityUser2 = new SecurityUser();
        securityUser2.setId(securityUser1.getId());
        assertThat(securityUser1).isEqualTo(securityUser2);
        securityUser2.setId(2L);
        assertThat(securityUser1).isNotEqualTo(securityUser2);
        securityUser1.setId(null);
        assertThat(securityUser1).isNotEqualTo(securityUser2);
    }
}
