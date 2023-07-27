package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TalukaMapperTest {

    private TalukaMapper talukaMapper;

    @BeforeEach
    public void setUp() {
        talukaMapper = new TalukaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(talukaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(talukaMapper.fromId(null)).isNull();
    }
}
