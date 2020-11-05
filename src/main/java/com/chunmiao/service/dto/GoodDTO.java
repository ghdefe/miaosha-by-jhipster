package com.chunmiao.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.chunmiao.domain.Good} entity.
 */
public class GoodDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 20)
    private String name;

    private String imgUrl;

    @Lob
    private String detail;

    @NotNull
    @DecimalMin(value = "0")
    private Float price;

    @NotNull
    private Long sellerId;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoodDTO)) {
            return false;
        }

        return id != null && id.equals(((GoodDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoodDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", imgUrl='" + getImgUrl() + "'" +
            ", detail='" + getDetail() + "'" +
            ", price=" + getPrice() +
            ", sellerId=" + getSellerId() +
            "}";
    }
}
