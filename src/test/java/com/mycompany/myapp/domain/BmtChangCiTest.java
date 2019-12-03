package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class BmtChangCiTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BmtChangCi.class);
        BmtChangCi bmtChangCi1 = new BmtChangCi();
        bmtChangCi1.setId(1L);
        BmtChangCi bmtChangCi2 = new BmtChangCi();
        bmtChangCi2.setId(bmtChangCi1.getId());
        assertThat(bmtChangCi1).isEqualTo(bmtChangCi2);
        bmtChangCi2.setId(2L);
        assertThat(bmtChangCi1).isNotEqualTo(bmtChangCi2);
        bmtChangCi1.setId(null);
        assertThat(bmtChangCi1).isNotEqualTo(bmtChangCi2);
    }
}
