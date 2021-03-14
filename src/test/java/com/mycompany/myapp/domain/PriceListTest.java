package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PriceListTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceList.class);
        PriceList priceList1 = new PriceList();
        priceList1.setId(1L);
        PriceList priceList2 = new PriceList();
        priceList2.setId(priceList1.getId());
        assertThat(priceList1).isEqualTo(priceList2);
        priceList2.setId(2L);
        assertThat(priceList1).isNotEqualTo(priceList2);
        priceList1.setId(null);
        assertThat(priceList1).isNotEqualTo(priceList2);
    }
}
