package com.techvg.covid.care.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.techvg.covid.care.web.rest.TestUtil;

public class UserAccessDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAccessDTO.class);
        UserAccessDTO userAccessDTO1 = new UserAccessDTO();
        userAccessDTO1.setId(1L);
        UserAccessDTO userAccessDTO2 = new UserAccessDTO();
        assertThat(userAccessDTO1).isNotEqualTo(userAccessDTO2);
        userAccessDTO2.setId(userAccessDTO1.getId());
        assertThat(userAccessDTO1).isEqualTo(userAccessDTO2);
        userAccessDTO2.setId(2L);
        assertThat(userAccessDTO1).isNotEqualTo(userAccessDTO2);
        userAccessDTO1.setId(null);
        assertThat(userAccessDTO1).isNotEqualTo(userAccessDTO2);
    }
}
