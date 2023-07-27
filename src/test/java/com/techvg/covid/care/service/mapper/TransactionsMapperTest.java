package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionsMapperTest {

    private TransactionsMapper transactionsMapper;

    @BeforeEach
    public void setUp() {
        transactionsMapper = new TransactionsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(transactionsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(transactionsMapper.fromId(null)).isNull();
    }
}
