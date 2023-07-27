package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryMasterMapperTest {

    private InventoryMasterMapper inventoryMasterMapper;

    @BeforeEach
    public void setUp() {
        inventoryMasterMapper = new InventoryMasterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(inventoryMasterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(inventoryMasterMapper.fromId(null)).isNull();
    }
}
