package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class BmtPayRecordTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BmtPayRecord.class);
        BmtPayRecord bmtPayRecord1 = new BmtPayRecord();
        bmtPayRecord1.setId(1L);
        BmtPayRecord bmtPayRecord2 = new BmtPayRecord();
        bmtPayRecord2.setId(bmtPayRecord1.getId());
        assertThat(bmtPayRecord1).isEqualTo(bmtPayRecord2);
        bmtPayRecord2.setId(2L);
        assertThat(bmtPayRecord1).isNotEqualTo(bmtPayRecord2);
        bmtPayRecord1.setId(null);
        assertThat(bmtPayRecord1).isNotEqualTo(bmtPayRecord2);
    }
}
