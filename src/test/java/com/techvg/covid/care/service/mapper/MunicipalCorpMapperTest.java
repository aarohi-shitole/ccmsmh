package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MunicipalCorpMapperTest {

    private MunicipalCorpMapper municipalCorpMapper;

    @BeforeEach
    public void setUp() {
        municipalCorpMapper = new MunicipalCorpMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(municipalCorpMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(municipalCorpMapper.fromId(null)).isNull();
    }
}
