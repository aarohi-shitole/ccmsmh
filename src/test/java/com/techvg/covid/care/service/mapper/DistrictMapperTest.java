package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DistrictMapperTest {

    private DistrictMapper districtMapper;

    @BeforeEach
    public void setUp() {
        districtMapper = new DistrictMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(districtMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(districtMapper.fromId(null)).isNull();
    }
}
