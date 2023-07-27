package com.techvg.covid.care.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.techvg.covid.care.web.rest.TestUtil;

public class SecurityUserDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecurityUserDTO.class);
        SecurityUserDTO securityUserDTO1 = new SecurityUserDTO();
        securityUserDTO1.setId(1L);
        SecurityUserDTO securityUserDTO2 = new SecurityUserDTO();
        assertThat(securityUserDTO1).isNotEqualTo(securityUserDTO2);
        securityUserDTO2.setId(securityUserDTO1.getId());
        assertThat(securityUserDTO1).isEqualTo(securityUserDTO2);
        securityUserDTO2.setId(2L);
        assertThat(securityUserDTO1).isNotEqualTo(securityUserDTO2);
        securityUserDTO1.setId(null);
        assertThat(securityUserDTO1).isNotEqualTo(securityUserDTO2);
    }
}
