package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Deduction.
 */
@Entity
@Table(name = "deduction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Deduction extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "reason")
    private String reason;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JsonIgnoreProperties(value = "deductions", allowSetters = true)
    private PriceList priceList;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getReason() {
        return reason;
    }

    public Deduction reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Deduction price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public Deduction priceList(PriceList priceList) {
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
        if (!(o instanceof Deduction)) {
            return false;
        }
        return id != null && id.equals(((Deduction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Deduction{" +
            "id=" + getId() +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
