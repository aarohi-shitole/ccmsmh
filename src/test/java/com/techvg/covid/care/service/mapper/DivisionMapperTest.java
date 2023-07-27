package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DivisionMapperTest {

    private DivisionMapper divisionMapper;

    @BeforeEach
    public void setUp() {
        divisionMapper = new DivisionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(divisionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(divisionMapper.fromId(null)).isNull();
    }
}
