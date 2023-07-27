package com.techvg.covid.care.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.techvg.covid.care.web.rest.TestUtil;

public class InventoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryDTO.class);
        InventoryDTO inventoryDTO1 = new InventoryDTO();
        inventoryDTO1.setId(1L);
        InventoryDTO inventoryDTO2 = new InventoryDTO();
        assertThat(inventoryDTO1).isNotEqualTo(inventoryDTO2);
        inventoryDTO2.setId(inventoryDTO1.getId());
        assertThat(inventoryDTO1).isEqualTo(inventoryDTO2);
        inventoryDTO2.setId(2L);
        assertThat(inventoryDTO1).isNotEqualTo(inventoryDTO2);
        inventoryDTO1.setId(null);
        assertThat(inventoryDTO1).isNotEqualTo(inventoryDTO2);
    }
}
