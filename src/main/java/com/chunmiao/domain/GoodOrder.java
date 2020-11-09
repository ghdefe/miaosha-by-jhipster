package com.chunmiao.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A GoodOrder.
 */
@Entity
@Table(name = "good_order")
public class GoodOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "good_id", nullable = false)
    private Long goodId;

    @NotNull
    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "activity_id")
    private Long activityId;

    @Column(name = "is_payed")
    private Boolean isPayed;

    @Column(name = "is_delivered")
    private Boolean isDelivered;

    @Column(name = "is_refund")
    private Boolean isRefund;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodId() {
        return goodId;
    }

    public GoodOrder goodId(Long goodId) {
        this.goodId = goodId;
        return this;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public GoodOrder buyerId(Long buyerId) {
        this.buyerId = buyerId;
        return this;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Float getPrice() {
        return price;
    }

    public GoodOrder price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getActivityId() {
        return activityId;
    }

    public GoodOrder activityId(Long activityId) {
        this.activityId = activityId;
        return this;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Boolean isIsPayed() {
        return isPayed;
    }

    public GoodOrder isPayed(Boolean isPayed) {
        this.isPayed = isPayed;
        return this;
    }

    public void setIsPayed(Boolean isPayed) {
        this.isPayed = isPayed;
    }

    public Boolean isIsDelivered() {
        return isDelivered;
    }

    public GoodOrder isDelivered(Boolean isDelivered) {
        this.isDelivered = isDelivered;
        return this;
    }

    public void setIsDelivered(Boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public Boolean isIsRefund() {
        return isRefund;
    }

    public GoodOrder isRefund(Boolean isRefund) {
        this.isRefund = isRefund;
        return this;
    }

    public void setIsRefund(Boolean isRefund) {
        this.isRefund = isRefund;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public GoodOrder createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoodOrder)) {
            return false;
        }
        return id != null && id.equals(((GoodOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoodOrder{" +
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
