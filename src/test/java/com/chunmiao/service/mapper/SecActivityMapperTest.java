package com.chunmiao.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SecActivityMapperTest {

    private SecActivityMapper secActivityMapper;

    @BeforeEach
    public void setUp() {
        secActivityMapper = new SecActivityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(secActivityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(secActivityMapper.fromId(null)).isNull();
    }
}
