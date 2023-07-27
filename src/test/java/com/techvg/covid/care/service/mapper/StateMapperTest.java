package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StateMapperTest {

    private StateMapper stateMapper;

    @BeforeEach
    public void setUp() {
        stateMapper = new StateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(stateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stateMapper.fromId(null)).isNull();
    }
}
