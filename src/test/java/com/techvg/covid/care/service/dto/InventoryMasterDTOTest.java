package com.techvg.covid.care.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.techvg.covid.care.web.rest.TestUtil;

public class InventoryMasterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryMasterDTO.class);
        InventoryMasterDTO inventoryMasterDTO1 = new InventoryMasterDTO();
        inventoryMasterDTO1.setId(1L);
        InventoryMasterDTO inventoryMasterDTO2 = new InventoryMasterDTO();
        assertThat(inventoryMasterDTO1).isNotEqualTo(inventoryMasterDTO2);
        inventoryMasterDTO2.setId(inventoryMasterDTO1.getId());
        assertThat(inventoryMasterDTO1).isEqualTo(inventoryMasterDTO2);
        inventoryMasterDTO2.setId(2L);
        assertThat(inventoryMasterDTO1).isNotEqualTo(inventoryMasterDTO2);
        inventoryMasterDTO1.setId(null);
        assertThat(inventoryMasterDTO1).isNotEqualTo(inventoryMasterDTO2);
    }
}
