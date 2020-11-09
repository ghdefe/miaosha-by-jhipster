package com.chunmiao.web.rest;

import com.chunmiao.RedisTestContainerExtension;
import com.chunmiao.MiaoshaByJhipsterApp;
import com.chunmiao.domain.GoodOrder;
import com.chunmiao.repository.GoodOrderRepository;
import com.chunmiao.service.GoodOrderService;
import com.chunmiao.service.dto.GoodOrderDTO;
import com.chunmiao.service.mapper.GoodOrderMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.chunmiao.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GoodOrderResource} REST controller.
 */
@SpringBootTest(classes = MiaoshaByJhipsterApp.class)
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class GoodOrderResourceIT {

    private static final Long DEFAULT_GOOD_ID = 1L;
    private static final Long UPDATED_GOOD_ID = 2L;

    private static final Long DEFAULT_BUYER_ID = 1L;
    private static final Long UPDATED_BUYER_ID = 2L;

    private static final Float DEFAULT_PRICE = 0F;
    private static final Float UPDATED_PRICE = 1F;

    private static final Long DEFAULT_ACTIVITY_ID = 1L;
    private static final Long UPDATED_ACTIVITY_ID = 2L;

    private static final Boolean DEFAULT_IS_PAYED = false;
    private static final Boolean UPDATED_IS_PAYED = true;

    private static final Boolean DEFAULT_IS_DELIVERED = false;
    private static final Boolean UPDATED_IS_DELIVERED = true;

    private static final Boolean DEFAULT_IS_REFUND = false;
    private static final Boolean UPDATED_IS_REFUND = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private GoodOrderRepository goodOrderRepository;

    @Autowired
    private GoodOrderMapper goodOrderMapper;

    @Autowired
    private GoodOrderService goodOrderService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGoodOrderMockMvc;

    private GoodOrder goodOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoodOrder createEntity(EntityManager em) {
        GoodOrder goodOrder = new GoodOrder()
            .goodId(DEFAULT_GOOD_ID)
            .buyerId(DEFAULT_BUYER_ID)
            .price(DEFAULT_PRICE)
            .activityId(DEFAULT_ACTIVITY_ID)
            .isPayed(DEFAULT_IS_PAYED)
            .isDelivered(DEFAULT_IS_DELIVERED)
            .isRefund(DEFAULT_IS_REFUND)
            .createTime(DEFAULT_CREATE_TIME);
        return goodOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoodOrder createUpdatedEntity(EntityManager em) {
        GoodOrder goodOrder = new GoodOrder()
            .goodId(UPDATED_GOOD_ID)
            .buyerId(UPDATED_BUYER_ID)
            .price(UPDATED_PRICE)
            .activityId(UPDATED_ACTIVITY_ID)
            .isPayed(UPDATED_IS_PAYED)
            .isDelivered(UPDATED_IS_DELIVERED)
            .isRefund(UPDATED_IS_REFUND)
            .createTime(UPDATED_CREATE_TIME);
        return goodOrder;
    }

    @BeforeEach
    public void initTest() {
        goodOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoodOrder() throws Exception {
        int databaseSizeBeforeCreate = goodOrderRepository.findAll().size();
        // Create the GoodOrder
        GoodOrderDTO goodOrderDTO = goodOrderMapper.toDto(goodOrder);
        restGoodOrderMockMvc.perform(post("/api/good-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the GoodOrder in the database
        List<GoodOrder> goodOrderList = goodOrderRepository.findAll();
        assertThat(goodOrderList).hasSize(databaseSizeBeforeCreate + 1);
        GoodOrder testGoodOrder = goodOrderList.get(goodOrderList.size() - 1);
        assertThat(testGoodOrder.getGoodId()).isEqualTo(DEFAULT_GOOD_ID);
        assertThat(testGoodOrder.getBuyerId()).isEqualTo(DEFAULT_BUYER_ID);
        assertThat(testGoodOrder.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testGoodOrder.getActivityId()).isEqualTo(DEFAULT_ACTIVITY_ID);
        assertThat(testGoodOrder.isIsPayed()).isEqualTo(DEFAULT_IS_PAYED);
        assertThat(testGoodOrder.isIsDelivered()).isEqualTo(DEFAULT_IS_DELIVERED);
        assertThat(testGoodOrder.isIsRefund()).isEqualTo(DEFAULT_IS_REFUND);
        assertThat(testGoodOrder.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createGoodOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goodOrderRepository.findAll().size();

        // Create the GoodOrder with an existing ID
        goodOrder.setId(1L);
        GoodOrderDTO goodOrderDTO = goodOrderMapper.toDto(goodOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoodOrderMockMvc.perform(post("/api/good-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoodOrder in the database
        List<GoodOrder> goodOrderList = goodOrderRepository.findAll();
        assertThat(goodOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGoodIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodOrderRepository.findAll().size();
        // set the field null
        goodOrder.setGoodId(null);

        // Create the GoodOrder, which fails.
        GoodOrderDTO goodOrderDTO = goodOrderMapper.toDto(goodOrder);


        restGoodOrderMockMvc.perform(post("/api/good-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodOrderDTO)))
            .andExpect(status().isBadRequest());

        List<GoodOrder> goodOrderList = goodOrderRepository.findAll();
        assertThat(goodOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBuyerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodOrderRepository.findAll().size();
        // set the field null
        goodOrder.setBuyerId(null);

        // Create the GoodOrder, which fails.
        GoodOrderDTO goodOrderDTO = goodOrderMapper.toDto(goodOrder);


        restGoodOrderMockMvc.perform(post("/api/good-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodOrderDTO)))
            .andExpect(status().isBadRequest());

        List<GoodOrder> goodOrderList = goodOrderRepository.findAll();
        assertThat(goodOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodOrderRepository.findAll().size();
        // set the field null
        goodOrder.setPrice(null);

        // Create the GoodOrder, which fails.
        GoodOrderDTO goodOrderDTO = goodOrderMapper.toDto(goodOrder);


        restGoodOrderMockMvc.perform(post("/api/good-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodOrderDTO)))
            .andExpect(status().isBadRequest());

        List<GoodOrder> goodOrderList = goodOrderRepository.findAll();
        assertThat(goodOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGoodOrders() throws Exception {
        // Initialize the database
        goodOrderRepository.saveAndFlush(goodOrder);

        // Get all the goodOrderList
        restGoodOrderMockMvc.perform(get("/api/good-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goodOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].goodId").value(hasItem(DEFAULT_GOOD_ID.intValue())))
            .andExpect(jsonPath("$.[*].buyerId").value(hasItem(DEFAULT_BUYER_ID.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].activityId").value(hasItem(DEFAULT_ACTIVITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].isPayed").value(hasItem(DEFAULT_IS_PAYED.booleanValue())))
            .andExpect(jsonPath("$.[*].isDelivered").value(hasItem(DEFAULT_IS_DELIVERED.booleanValue())))
            .andExpect(jsonPath("$.[*].isRefund").value(hasItem(DEFAULT_IS_REFUND.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))));
    }
    
    @Test
    @Transactional
    public void getGoodOrder() throws Exception {
        // Initialize the database
        goodOrderRepository.saveAndFlush(goodOrder);

        // Get the goodOrder
        restGoodOrderMockMvc.perform(get("/api/good-orders/{id}", goodOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(goodOrder.getId().intValue()))
            .andExpect(jsonPath("$.goodId").value(DEFAULT_GOOD_ID.intValue()))
            .andExpect(jsonPath("$.buyerId").value(DEFAULT_BUYER_ID.intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.activityId").value(DEFAULT_ACTIVITY_ID.intValue()))
            .andExpect(jsonPath("$.isPayed").value(DEFAULT_IS_PAYED.booleanValue()))
            .andExpect(jsonPath("$.isDelivered").value(DEFAULT_IS_DELIVERED.booleanValue()))
            .andExpect(jsonPath("$.isRefund").value(DEFAULT_IS_REFUND.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)));
    }
    @Test
    @Transactional
    public void getNonExistingGoodOrder() throws Exception {
        // Get the goodOrder
        restGoodOrderMockMvc.perform(get("/api/good-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoodOrder() throws Exception {
        // Initialize the database
        goodOrderRepository.saveAndFlush(goodOrder);

        int databaseSizeBeforeUpdate = goodOrderRepository.findAll().size();

        // Update the goodOrder
        GoodOrder updatedGoodOrder = goodOrderRepository.findById(goodOrder.getId()).get();
        // Disconnect from session so that the updates on updatedGoodOrder are not directly saved in db
        em.detach(updatedGoodOrder);
        updatedGoodOrder
            .goodId(UPDATED_GOOD_ID)
            .buyerId(UPDATED_BUYER_ID)
            .price(UPDATED_PRICE)
            .activityId(UPDATED_ACTIVITY_ID)
            .isPayed(UPDATED_IS_PAYED)
            .isDelivered(UPDATED_IS_DELIVERED)
            .isRefund(UPDATED_IS_REFUND)
            .createTime(UPDATED_CREATE_TIME);
        GoodOrderDTO goodOrderDTO = goodOrderMapper.toDto(updatedGoodOrder);

        restGoodOrderMockMvc.perform(put("/api/good-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodOrderDTO)))
            .andExpect(status().isOk());

        // Validate the GoodOrder in the database
        List<GoodOrder> goodOrderList = goodOrderRepository.findAll();
        assertThat(goodOrderList).hasSize(databaseSizeBeforeUpdate);
        GoodOrder testGoodOrder = goodOrderList.get(goodOrderList.size() - 1);
        assertThat(testGoodOrder.getGoodId()).isEqualTo(UPDATED_GOOD_ID);
        assertThat(testGoodOrder.getBuyerId()).isEqualTo(UPDATED_BUYER_ID);
        assertThat(testGoodOrder.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testGoodOrder.getActivityId()).isEqualTo(UPDATED_ACTIVITY_ID);
        assertThat(testGoodOrder.isIsPayed()).isEqualTo(UPDATED_IS_PAYED);
        assertThat(testGoodOrder.isIsDelivered()).isEqualTo(UPDATED_IS_DELIVERED);
        assertThat(testGoodOrder.isIsRefund()).isEqualTo(UPDATED_IS_REFUND);
        assertThat(testGoodOrder.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingGoodOrder() throws Exception {
        int databaseSizeBeforeUpdate = goodOrderRepository.findAll().size();

        // Create the GoodOrder
        GoodOrderDTO goodOrderDTO = goodOrderMapper.toDto(goodOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoodOrderMockMvc.perform(put("/api/good-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoodOrder in the database
        List<GoodOrder> goodOrderList = goodOrderRepository.findAll();
        assertThat(goodOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGoodOrder() throws Exception {
        // Initialize the database
        goodOrderRepository.saveAndFlush(goodOrder);

        int databaseSizeBeforeDelete = goodOrderRepository.findAll().size();

        // Delete the goodOrder
        restGoodOrderMockMvc.perform(delete("/api/good-orders/{id}", goodOrder.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GoodOrder> goodOrderList = goodOrderRepository.findAll();
        assertThat(goodOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
