package com.chunmiao.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.chunmiao.web.rest.TestUtil;

public class SecActivityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecActivityDTO.class);
        SecActivityDTO secActivityDTO1 = new SecActivityDTO();
        secActivityDTO1.setId(1L);
        SecActivityDTO secActivityDTO2 = new SecActivityDTO();
        assertThat(secActivityDTO1).isNotEqualTo(secActivityDTO2);
        secActivityDTO2.setId(secActivityDTO1.getId());
        assertThat(secActivityDTO1).isEqualTo(secActivityDTO2);
        secActivityDTO2.setId(2L);
        assertThat(secActivityDTO1).isNotEqualTo(secActivityDTO2);
        secActivityDTO1.setId(null);
        assertThat(secActivityDTO1).isNotEqualTo(secActivityDTO2);
    }
}
