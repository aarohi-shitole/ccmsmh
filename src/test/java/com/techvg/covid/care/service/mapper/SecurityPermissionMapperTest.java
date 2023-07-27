package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SecurityPermissionMapperTest {

    private SecurityPermissionMapper securityPermissionMapper;

    @BeforeEach
    public void setUp() {
        securityPermissionMapper = new SecurityPermissionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(securityPermissionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(securityPermissionMapper.fromId(null)).isNull();
    }
}
