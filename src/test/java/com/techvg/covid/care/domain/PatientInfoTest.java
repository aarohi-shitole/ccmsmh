package com.techvg.covid.care.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.techvg.covid.care.web.rest.TestUtil;

public class PatientInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientInfo.class);
        PatientInfo patientInfo1 = new PatientInfo();
        patientInfo1.setId(1L);
        PatientInfo patientInfo2 = new PatientInfo();
        patientInfo2.setId(patientInfo1.getId());
        assertThat(patientInfo1).isEqualTo(patientInfo2);
        patientInfo2.setId(2L);
        assertThat(patientInfo1).isNotEqualTo(patientInfo2);
        patientInfo1.setId(null);
        assertThat(patientInfo1).isNotEqualTo(patientInfo2);
    }
}
