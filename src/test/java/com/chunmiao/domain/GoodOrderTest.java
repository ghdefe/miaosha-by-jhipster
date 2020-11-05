package com.chunmiao.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.chunmiao.web.rest.TestUtil;

public class GoodOrderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoodOrder.class);
        GoodOrder goodOrder1 = new GoodOrder();
        goodOrder1.setId(1L);
        GoodOrder goodOrder2 = new GoodOrder();
        goodOrder2.setId(goodOrder1.getId());
        assertThat(goodOrder1).isEqualTo(goodOrder2);
        goodOrder2.setId(2L);
        assertThat(goodOrder1).isNotEqualTo(goodOrder2);
        goodOrder1.setId(null);
        assertThat(goodOrder1).isNotEqualTo(goodOrder2);
    }
}
