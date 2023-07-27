package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SecurityUserMapperTest {

    private SecurityUserMapper securityUserMapper;

    @BeforeEach
    public void setUp() {
        securityUserMapper = new SecurityUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(securityUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(securityUserMapper.fromId(null)).isNull();
    }
}
