package com.chunmiao.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GoodMapperTest {

    private GoodMapper goodMapper;

    @BeforeEach
    public void setUp() {
        goodMapper = new GoodMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(goodMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(goodMapper.fromId(null)).isNull();
    }
}
