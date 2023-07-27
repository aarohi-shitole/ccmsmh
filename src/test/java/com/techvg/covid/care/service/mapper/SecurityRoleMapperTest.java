package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SecurityRoleMapperTest {

    private SecurityRoleMapper securityRoleMapper;

    @BeforeEach
    public void setUp() {
        securityRoleMapper = new SecurityRoleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(securityRoleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(securityRoleMapper.fromId(null)).isNull();
    }
}
