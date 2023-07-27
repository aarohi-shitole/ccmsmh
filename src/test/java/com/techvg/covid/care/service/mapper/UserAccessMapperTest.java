package com.techvg.covid.care.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserAccessMapperTest {

    private UserAccessMapper userAccessMapper;

    @BeforeEach
    public void setUp() {
        userAccessMapper = new UserAccessMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userAccessMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userAccessMapper.fromId(null)).isNull();
    }
}
