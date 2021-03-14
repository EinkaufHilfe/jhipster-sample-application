package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ConditionDefinitionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConditionDefinition.class);
        ConditionDefinition conditionDefinition1 = new ConditionDefinition();
        conditionDefinition1.setId(1L);
        ConditionDefinition conditionDefinition2 = new ConditionDefinition();
        conditionDefinition2.setId(conditionDefinition1.getId());
        assertThat(conditionDefinition1).isEqualTo(conditionDefinition2);
        conditionDefinition2.setId(2L);
        assertThat(conditionDefinition1).isNotEqualTo(conditionDefinition2);
        conditionDefinition1.setId(null);
        assertThat(conditionDefinition1).isNotEqualTo(conditionDefinition2);
    }
}
