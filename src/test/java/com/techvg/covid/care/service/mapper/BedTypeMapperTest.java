package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BedTypeMapperTest {

    private BedTypeMapper bedTypeMapper;

    @BeforeEach
    public void setUp() {
        bedTypeMapper = new BedTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bedTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bedTypeMapper.fromId(null)).isNull();
    }
}
