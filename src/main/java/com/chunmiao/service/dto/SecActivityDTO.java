package com.chunmiao.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.chunmiao.domain.SecActivity} entity.
 */
public class SecActivityDTO implements Serializable {
    
    private Long id;

    private String name;

    @NotNull
    private Long goodId;

    private Long author;

    private Float secPrice;

    private ZonedDateTime start;

    private ZonedDateTime end;

    
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

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public Float getSecPrice() {
        return secPrice;
    }

    public void setSecPrice(Float secPrice) {
        this.secPrice = secPrice;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecActivityDTO)) {
            return false;
        }

        return id != null && id.equals(((SecActivityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecActivityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", goodId=" + getGoodId() +
            ", author=" + getAuthor() +
            ", secPrice=" + getSecPrice() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
