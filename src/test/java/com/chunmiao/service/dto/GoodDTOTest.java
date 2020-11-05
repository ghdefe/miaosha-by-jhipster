package com.chunmiao.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.chunmiao.web.rest.TestUtil;

public class GoodDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoodDTO.class);
        GoodDTO goodDTO1 = new GoodDTO();
        goodDTO1.setId(1L);
        GoodDTO goodDTO2 = new GoodDTO();
        assertThat(goodDTO1).isNotEqualTo(goodDTO2);
        goodDTO2.setId(goodDTO1.getId());
        assertThat(goodDTO1).isEqualTo(goodDTO2);
        goodDTO2.setId(2L);
        assertThat(goodDTO1).isNotEqualTo(goodDTO2);
        goodDTO1.setId(null);
        assertThat(goodDTO1).isNotEqualTo(goodDTO2);
    }
}
