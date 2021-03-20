package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ConditionDefinition.
 */
@Entity
@Table(name = "condition_definition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConditionDefinition extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "definition")
    private String definition;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = "conditions", allowSetters = true)
    private PriceList priceList;

    public String getDefinition() {
        return definition;
    }

    public ConditionDefinition definition(String definition) {
        this.definition = definition;
        return this;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getDescription() {
        return description;
    }

    public ConditionDefinition description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public ConditionDefinition priceList(PriceList priceList) {
        this.priceList = priceList;
        return this;
    }

    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConditionDefinition)) {
            return false;
        }
        return id != null && id.equals(((ConditionDefinition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConditionDefinition{" +
            "id=" + getId() +
            ", definition='" + getDefinition() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
