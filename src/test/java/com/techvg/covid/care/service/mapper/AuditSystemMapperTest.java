package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AuditSystemMapperTest {

    private AuditSystemMapper auditSystemMapper;

    @BeforeEach
    public void setUp() {
        auditSystemMapper = new AuditSystemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(auditSystemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(auditSystemMapper.fromId(null)).isNull();
    }
}
