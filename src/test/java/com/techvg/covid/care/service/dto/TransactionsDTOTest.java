package com.techvg.covid.care.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.techvg.covid.care.web.rest.TestUtil;

public class TransactionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionsDTO.class);
        TransactionsDTO transactionsDTO1 = new TransactionsDTO();
        transactionsDTO1.setId(1L);
        TransactionsDTO transactionsDTO2 = new TransactionsDTO();
        assertThat(transactionsDTO1).isNotEqualTo(transactionsDTO2);
        transactionsDTO2.setId(transactionsDTO1.getId());
        assertThat(transactionsDTO1).isEqualTo(transactionsDTO2);
        transactionsDTO2.setId(2L);
        assertThat(transactionsDTO1).isNotEqualTo(transactionsDTO2);
        transactionsDTO1.setId(null);
        assertThat(transactionsDTO1).isNotEqualTo(transactionsDTO2);
    }
}
