package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A PriceList.
 */
@Entity
@Table(name = "price_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PriceList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_till")
    private LocalDate validTill;

    @Column(name = "type")
    private String type;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "last_updated_at")
    private ZonedDateTime lastUpdatedAt;

    @OneToMany(mappedBy = "priceList")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ConditionDefinition> conditions = new HashSet<>();

    @OneToMany(mappedBy = "priceList")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Deduction> deductions = new HashSet<>();

    @OneToMany(mappedBy = "priceList")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PartSale> partSales = new HashSet<>();

    @OneToMany(mappedBy = "priceList")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Price> prices = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "priceLists", allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public PriceList validFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTill() {
        return validTill;
    }

    public PriceList validTill(LocalDate validTill) {
        this.validTill = validTill;
        return this;
    }

    public void setValidTill(LocalDate validTill) {
        this.validTill = validTill;
    }

    public String getType() {
        return type;
    }

    public PriceList type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PriceList createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public PriceList createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public PriceList lastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public ZonedDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public PriceList lastUpdatedAt(ZonedDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
        return this;
    }

    public void setLastUpdatedAt(ZonedDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Set<ConditionDefinition> getConditions() {
        return conditions;
    }

    public PriceList conditions(Set<ConditionDefinition> conditionDefinitions) {
        this.conditions = conditionDefinitions;
        return this;
    }

    public PriceList addCondition(ConditionDefinition conditionDefinition) {
        this.conditions.add(conditionDefinition);
        conditionDefinition.setPriceList(this);
        return this;
    }

    public PriceList removeCondition(ConditionDefinition conditionDefinition) {
        this.conditions.remove(conditionDefinition);
        conditionDefinition.setPriceList(null);
        return this;
    }

    public void setConditions(Set<ConditionDefinition> conditionDefinitions) {
        this.conditions = conditionDefinitions;
    }

    public Set<Deduction> getDeductions() {
        return deductions;
    }

    public PriceList deductions(Set<Deduction> deductions) {
        this.deductions = deductions;
        return this;
    }

    public PriceList addDeduction(Deduction deduction) {
        this.deductions.add(deduction);
        deduction.setPriceList(this);
        return this;
    }

    public PriceList removeDeduction(Deduction deduction) {
        this.deductions.remove(deduction);
        deduction.setPriceList(null);
        return this;
    }

    public void setDeductions(Set<Deduction> deductions) {
        this.deductions = deductions;
    }

    public Set<PartSale> getPartSales() {
        return partSales;
    }

    public PriceList partSales(Set<PartSale> partSales) {
        this.partSales = partSales;
        return this;
    }

    public PriceList addPartSale(PartSale partSale) {
        this.partSales.add(partSale);
        partSale.setPriceList(this);
        return this;
    }

    public PriceList removePartSale(PartSale partSale) {
        this.partSales.remove(partSale);
        partSale.setPriceList(null);
        return this;
    }

    public void setPartSales(Set<PartSale> partSales) {
        this.partSales = partSales;
    }

    public Set<Price> getPrices() {
        return prices;
    }

    public PriceList prices(Set<Price> prices) {
        this.prices = prices;
        return this;
    }

    public PriceList addPrice(Price price) {
        this.prices.add(price);
        price.setPriceList(this);
        return this;
    }

    public PriceList removePrice(Price price) {
        this.prices.remove(price);
        price.setPriceList(null);
        return this;
    }

    public void setPrices(Set<Price> prices) {
        this.prices = prices;
    }

    public Customer getCustomer() {
        return customer;
    }

    public PriceList customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PriceList)) {
            return false;
        }
        return id != null && id.equals(((PriceList) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PriceList{" +
            "id=" + getId() +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTill='" + getValidTill() + "'" +
            ", type='" + getType() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            ", lastUpdatedAt='" + getLastUpdatedAt() + "'" +
            "}";
    }
}
