package com.chunmiao.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A SecActivity.
 */
@Entity
@Table(name = "sec_activity")
public class SecActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "good_id", nullable = false)
    private Long goodId;

    @Column(name = "author")
    private Long author;

    @NotNull
    @Column(name = "start", nullable = false)
    private LocalDate start;

    @NotNull
    @Column(name = "end", nullable = false)
    private LocalDate end;

    @Column(name = "sec_price")
    private Float secPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SecActivity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGoodId() {
        return goodId;
    }

    public SecActivity goodId(Long goodId) {
        this.goodId = goodId;
        return this;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getAuthor() {
        return author;
    }

    public SecActivity author(Long author) {
        this.author = author;
        return this;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public LocalDate getStart() {
        return start;
    }

    public SecActivity start(LocalDate start) {
        this.start = start;
        return this;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public SecActivity end(LocalDate end) {
        this.end = end;
        return this;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Float getSecPrice() {
        return secPrice;
    }

    public SecActivity secPrice(Float secPrice) {
        this.secPrice = secPrice;
        return this;
    }

    public void setSecPrice(Float secPrice) {
        this.secPrice = secPrice;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecActivity)) {
            return false;
        }
        return id != null && id.equals(((SecActivity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecActivity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", goodId=" + getGoodId() +
            ", author=" + getAuthor() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", secPrice=" + getSecPrice() +
            "}";
    }
}
