package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TripDetailsMapperTest {

    private TripDetailsMapper tripDetailsMapper;

    @BeforeEach
    public void setUp() {
        tripDetailsMapper = new TripDetailsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tripDetailsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tripDetailsMapper.fromId(null)).isNull();
    }
}
