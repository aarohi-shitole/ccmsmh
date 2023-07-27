package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryTypeMapperTest {

    private InventoryTypeMapper inventoryTypeMapper;

    @BeforeEach
    public void setUp() {
        inventoryTypeMapper = new InventoryTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(inventoryTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(inventoryTypeMapper.fromId(null)).isNull();
    }
}
