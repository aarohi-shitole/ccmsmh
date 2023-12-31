package com.techvg.covid.care.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.techvg.covid.care.web.rest.TestUtil;

public class SecurityRoleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecurityRoleDTO.class);
        SecurityRoleDTO securityRoleDTO1 = new SecurityRoleDTO();
        securityRoleDTO1.setId(1L);
        SecurityRoleDTO securityRoleDTO2 = new SecurityRoleDTO();
        assertThat(securityRoleDTO1).isNotEqualTo(securityRoleDTO2);
        securityRoleDTO2.setId(securityRoleDTO1.getId());
        assertThat(securityRoleDTO1).isEqualTo(securityRoleDTO2);
        securityRoleDTO2.setId(2L);
        assertThat(securityRoleDTO1).isNotEqualTo(securityRoleDTO2);
        securityRoleDTO1.setId(null);
        assertThat(securityRoleDTO1).isNotEqualTo(securityRoleDTO2);
    }
}
