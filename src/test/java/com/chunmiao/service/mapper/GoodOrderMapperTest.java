package com.chunmiao.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GoodOrderMapperTest {

    private GoodOrderMapper goodOrderMapper;

    @BeforeEach
    public void setUp() {
        goodOrderMapper = new GoodOrderMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(goodOrderMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(goodOrderMapper.fromId(null)).isNull();
    }
}
