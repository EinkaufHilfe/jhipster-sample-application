package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PartSaleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartSale.class);
        PartSale partSale1 = new PartSale();
        partSale1.setId(1L);
        PartSale partSale2 = new PartSale();
        partSale2.setId(partSale1.getId());
        assertThat(partSale1).isEqualTo(partSale2);
        partSale2.setId(2L);
        assertThat(partSale1).isNotEqualTo(partSale2);
        partSale1.setId(null);
        assertThat(partSale1).isNotEqualTo(partSale2);
    }
}
