package com.techvg.covid.care.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.techvg.covid.care.web.rest.TestUtil;

public class InventoryTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryType.class);
        InventoryType inventoryType1 = new InventoryType();
        inventoryType1.setId(1L);
        InventoryType inventoryType2 = new InventoryType();
        inventoryType2.setId(inventoryType1.getId());
        assertThat(inventoryType1).isEqualTo(inventoryType2);
        inventoryType2.setId(2L);
        assertThat(inventoryType1).isNotEqualTo(inventoryType2);
        inventoryType1.setId(null);
        assertThat(inventoryType1).isNotEqualTo(inventoryType2);
    }
}
