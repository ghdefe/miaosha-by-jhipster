package com.chunmiao.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Good.
 */
@Entity
@Table(name = "good")
public class Good implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "img_url")
    private String imgUrl;

    @Lob
    @Column(name = "detail")
    private String detail;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", nullable = false)
    private Float price;

    @NotNull
    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @NotNull
    @Min(value = 0L)
    @Column(name = "stock", nullable = false)
    private Long stock;

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

    public Good name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Good imgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDetail() {
        return detail;
    }

    public Good detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Float getPrice() {
        return price;
    }

    public Good price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public Good sellerId(Long sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getStock() {
        return stock;
    }

    public Good stock(Long stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Good)) {
            return false;
        }
        return id != null && id.equals(((Good) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Good{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", imgUrl='" + getImgUrl() + "'" +
            ", detail='" + getDetail() + "'" +
            ", price=" + getPrice() +
            ", sellerId=" + getSellerId() +
            ", stock=" + getStock() +
            "}";
    }
}
