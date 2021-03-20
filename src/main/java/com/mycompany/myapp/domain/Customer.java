package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer extends AbstractAuditingEntity {
    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PriceList> priceLists = new HashSet<>();

    public String getName() {
        return name;
    }

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PriceList> getPriceLists() {
        return priceLists;
    }

    public Customer priceLists(Set<PriceList> priceLists) {
        this.priceLists = priceLists;
        return this;
    }

    public Customer addPriceList(PriceList priceList) {
        this.priceLists.add(priceList);
        priceList.setCustomer(this);
        return this;
    }

    public Customer removePriceList(PriceList priceList) {
        this.priceLists.remove(priceList);
        priceList.setCustomer(null);
        return this;
    }

    public void setPriceLists(Set<PriceList> priceLists) {
        this.priceLists = priceLists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
