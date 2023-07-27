package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BedTransactionsMapperTest {

    private BedTransactionsMapper bedTransactionsMapper;

    @BeforeEach
    public void setUp() {
        bedTransactionsMapper = new BedTransactionsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bedTransactionsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bedTransactionsMapper.fromId(null)).isNull();
    }
}
