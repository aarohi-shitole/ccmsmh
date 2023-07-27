package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryMapperTest {

    private InventoryMapper inventoryMapper;

    @BeforeEach
    public void setUp() {
        inventoryMapper = new InventoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(inventoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(inventoryMapper.fromId(null)).isNull();
    }
}
