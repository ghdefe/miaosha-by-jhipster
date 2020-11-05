package com.chunmiao.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.chunmiao.web.rest.TestUtil;

public class GoodOrderDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoodOrderDTO.class);
        GoodOrderDTO goodOrderDTO1 = new GoodOrderDTO();
        goodOrderDTO1.setId(1L);
        GoodOrderDTO goodOrderDTO2 = new GoodOrderDTO();
        assertThat(goodOrderDTO1).isNotEqualTo(goodOrderDTO2);
        goodOrderDTO2.setId(goodOrderDTO1.getId());
        assertThat(goodOrderDTO1).isEqualTo(goodOrderDTO2);
        goodOrderDTO2.setId(2L);
        assertThat(goodOrderDTO1).isNotEqualTo(goodOrderDTO2);
        goodOrderDTO1.setId(null);
        assertThat(goodOrderDTO1).isNotEqualTo(goodOrderDTO2);
    }
}
