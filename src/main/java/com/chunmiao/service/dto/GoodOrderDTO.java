package com.chunmiao.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.chunmiao.domain.GoodOrder} entity.
 */
public class GoodOrderDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Long goodId;

    @NotNull
    private Long buyerId;

    @NotNull
    @DecimalMin(value = "0")
    private Float price;

    private Long activityId;

    private Boolean isPayed;

    private Boolean isDelivered;

    private Boolean isRefund;

    @NotNull
    private LocalDate createTime;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Boolean isIsPayed() {
        return isPayed;
    }

    public void setIsPayed(Boolean isPayed) {
        this.isPayed = isPayed;
    }

    public Boolean isIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(Boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public Boolean isIsRefund() {
        return isRefund;
    }

    public void setIsRefund(Boolean isRefund) {
        this.isRefund = isRefund;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoodOrderDTO)) {
            return false;
        }

        return id != null && id.equals(((GoodOrderDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoodOrderDTO{" +
            "id=" + getId() +
            ", goodId=" + getGoodId() +
            ", buyerId=" + getBuyerId() +
            ", price=" + getPrice() +
            ", activityId=" + getActivityId() +
            ", isPayed='" + isIsPayed() + "'" +
            ", isDelivered='" + isIsDelivered() + "'" +
            ", isRefund='" + isIsRefund() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
