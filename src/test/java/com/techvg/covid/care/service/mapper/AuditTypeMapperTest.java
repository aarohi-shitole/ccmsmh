package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AuditTypeMapperTest {

    private AuditTypeMapper auditTypeMapper;

    @BeforeEach
    public void setUp() {
        auditTypeMapper = new AuditTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(auditTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(auditTypeMapper.fromId(null)).isNull();
    }
}
