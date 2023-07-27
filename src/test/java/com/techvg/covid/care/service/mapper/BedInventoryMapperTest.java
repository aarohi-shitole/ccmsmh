package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BedInventoryMapperTest {

    private BedInventoryMapper bedInventoryMapper;

    @BeforeEach
    public void setUp() {
        bedInventoryMapper = new BedInventoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bedInventoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bedInventoryMapper.fromId(null)).isNull();
    }
}
