package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PatientInfoMapperTest {

    private PatientInfoMapper patientInfoMapper;

    @BeforeEach
    public void setUp() {
        patientInfoMapper = new PatientInfoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(patientInfoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(patientInfoMapper.fromId(null)).isNull();
    }
}
