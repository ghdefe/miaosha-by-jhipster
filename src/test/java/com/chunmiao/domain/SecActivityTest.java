package com.chunmiao.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.chunmiao.web.rest.TestUtil;

public class SecActivityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecActivity.class);
        SecActivity secActivity1 = new SecActivity();
        secActivity1.setId(1L);
        SecActivity secActivity2 = new SecActivity();
        secActivity2.setId(secActivity1.getId());
        assertThat(secActivity1).isEqualTo(secActivity2);
        secActivity2.setId(2L);
        assertThat(secActivity1).isNotEqualTo(secActivity2);
        secActivity1.setId(null);
        assertThat(secActivity1).isNotEqualTo(secActivity2);
    }
}
