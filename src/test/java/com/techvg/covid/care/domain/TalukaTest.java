package com.techvg.covid.care.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.techvg.covid.care.web.rest.TestUtil;

public class TalukaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Taluka.class);
        Taluka taluka1 = new Taluka();
        taluka1.setId(1L);
        Taluka taluka2 = new Taluka();
        taluka2.setId(taluka1.getId());
        assertThat(taluka1).isEqualTo(taluka2);
        taluka2.setId(2L);
        assertThat(taluka1).isNotEqualTo(taluka2);
        taluka1.setId(null);
        assertThat(taluka1).isNotEqualTo(taluka2);
    }
}
