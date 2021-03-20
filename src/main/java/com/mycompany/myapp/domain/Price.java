package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Price.
 */
@Entity
@Table(name = "price")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Price extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "model")
    private String model;

    @Column(name = "condition")
    private String condition;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JsonIgnoreProperties(value = "prices", allowSetters = true)
    private PriceList priceList;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getModel() {
        return model;
    }

    public Price model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCondition() {
        return condition;
    }

    public Price condition(String condition) {
        this.condition = condition;
        return this;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Price price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public Price priceList(PriceList priceList) {
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
        if (!(o instanceof Price)) {
            return false;
        }
        return id != null && id.equals(((Price) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Price{" +
            "id=" + getId() +
            ", model='" + getModel() + "'" +
            ", condition='" + getCondition() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
