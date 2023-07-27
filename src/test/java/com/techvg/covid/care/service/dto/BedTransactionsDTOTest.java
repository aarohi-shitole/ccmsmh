package com.techvg.covid.care.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.techvg.covid.care.web.rest.TestUtil;

public class BedTransactionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BedTransactionsDTO.class);
        BedTransactionsDTO bedTransactionsDTO1 = new BedTransactionsDTO();
        bedTransactionsDTO1.setId(1L);
        BedTransactionsDTO bedTransactionsDTO2 = new BedTransactionsDTO();
        assertThat(bedTransactionsDTO1).isNotEqualTo(bedTransactionsDTO2);
        bedTransactionsDTO2.setId(bedTransactionsDTO1.getId());
        assertThat(bedTransactionsDTO1).isEqualTo(bedTransactionsDTO2);
        bedTransactionsDTO2.setId(2L);
        assertThat(bedTransactionsDTO1).isNotEqualTo(bedTransactionsDTO2);
        bedTransactionsDTO1.setId(null);
        assertThat(bedTransactionsDTO1).isNotEqualTo(bedTransactionsDTO2);
    }
}
