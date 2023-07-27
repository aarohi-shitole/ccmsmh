package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HospitalTypeMapperTest {

    private HospitalTypeMapper hospitalTypeMapper;

    @BeforeEach
    public void setUp() {
        hospitalTypeMapper = new HospitalTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(hospitalTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(hospitalTypeMapper.fromId(null)).isNull();
    }
}
