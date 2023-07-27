package com.techvg.covid.care.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.techvg.covid.care.web.rest.TestUtil;

public class MunicipalCorpDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MunicipalCorpDTO.class);
        MunicipalCorpDTO municipalCorpDTO1 = new MunicipalCorpDTO();
        municipalCorpDTO1.setId(1L);
        MunicipalCorpDTO municipalCorpDTO2 = new MunicipalCorpDTO();
        assertThat(municipalCorpDTO1).isNotEqualTo(municipalCorpDTO2);
        municipalCorpDTO2.setId(municipalCorpDTO1.getId());
        assertThat(municipalCorpDTO1).isEqualTo(municipalCorpDTO2);
        municipalCorpDTO2.setId(2L);
        assertThat(municipalCorpDTO1).isNotEqualTo(municipalCorpDTO2);
        municipalCorpDTO1.setId(null);
        assertThat(municipalCorpDTO1).isNotEqualTo(municipalCorpDTO2);
    }
}
