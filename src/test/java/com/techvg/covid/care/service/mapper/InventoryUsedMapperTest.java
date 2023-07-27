package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryUsedMapperTest {

    private InventoryUsedMapper inventoryUsedMapper;

    @BeforeEach
    public void setUp() {
        inventoryUsedMapper = new InventoryUsedMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(inventoryUsedMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(inventoryUsedMapper.fromId(null)).isNull();
    }
}
