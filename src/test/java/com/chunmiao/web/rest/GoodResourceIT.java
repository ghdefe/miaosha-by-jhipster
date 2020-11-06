package com.chunmiao.web.rest;

import com.chunmiao.RedisTestContainerExtension;
import com.chunmiao.MiaoshaByJhipsterApp;
import com.chunmiao.domain.Good;
import com.chunmiao.repository.GoodRepository;
import com.chunmiao.service.GoodService;
import com.chunmiao.service.dto.GoodDTO;
import com.chunmiao.service.mapper.GoodMapper;

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
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GoodResource} REST controller.
 */
@SpringBootTest(classes = MiaoshaByJhipsterApp.class)
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class GoodResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMG_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE = 0F;
    private static final Float UPDATED_PRICE = 1F;

    private static final Long DEFAULT_SELLER_ID = 1L;
    private static final Long UPDATED_SELLER_ID = 2L;

    private static final Long DEFAULT_STOCK = 0L;
    private static final Long UPDATED_STOCK = 1L;

    @Autowired
    private GoodRepository goodRepository;

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private GoodService goodService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGoodMockMvc;

    private Good good;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Good createEntity(EntityManager em) {
        Good good = new Good()
            .name(DEFAULT_NAME)
            .imgUrl(DEFAULT_IMG_URL)
            .detail(DEFAULT_DETAIL)
            .price(DEFAULT_PRICE)
            .sellerId(DEFAULT_SELLER_ID)
            .stock(DEFAULT_STOCK);
        return good;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Good createUpdatedEntity(EntityManager em) {
        Good good = new Good()
            .name(UPDATED_NAME)
            .imgUrl(UPDATED_IMG_URL)
            .detail(UPDATED_DETAIL)
            .price(UPDATED_PRICE)
            .sellerId(UPDATED_SELLER_ID)
            .stock(UPDATED_STOCK);
        return good;
    }

    @BeforeEach
    public void initTest() {
        good = createEntity(em);
    }

    @Test
    @Transactional
    public void createGood() throws Exception {
        int databaseSizeBeforeCreate = goodRepository.findAll().size();
        // Create the Good
        GoodDTO goodDTO = goodMapper.toDto(good);
        restGoodMockMvc.perform(post("/api/goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodDTO)))
            .andExpect(status().isCreated());

        // Validate the Good in the database
        List<Good> goodList = goodRepository.findAll();
        assertThat(goodList).hasSize(databaseSizeBeforeCreate + 1);
        Good testGood = goodList.get(goodList.size() - 1);
        assertThat(testGood.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGood.getImgUrl()).isEqualTo(DEFAULT_IMG_URL);
        assertThat(testGood.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testGood.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testGood.getSellerId()).isEqualTo(DEFAULT_SELLER_ID);
        assertThat(testGood.getStock()).isEqualTo(DEFAULT_STOCK);
    }

    @Test
    @Transactional
    public void createGoodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goodRepository.findAll().size();

        // Create the Good with an existing ID
        good.setId(1L);
        GoodDTO goodDTO = goodMapper.toDto(good);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoodMockMvc.perform(post("/api/goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Good in the database
        List<Good> goodList = goodRepository.findAll();
        assertThat(goodList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodRepository.findAll().size();
        // set the field null
        good.setName(null);

        // Create the Good, which fails.
        GoodDTO goodDTO = goodMapper.toDto(good);


        restGoodMockMvc.perform(post("/api/goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodDTO)))
            .andExpect(status().isBadRequest());

        List<Good> goodList = goodRepository.findAll();
        assertThat(goodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodRepository.findAll().size();
        // set the field null
        good.setPrice(null);

        // Create the Good, which fails.
        GoodDTO goodDTO = goodMapper.toDto(good);


        restGoodMockMvc.perform(post("/api/goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodDTO)))
            .andExpect(status().isBadRequest());

        List<Good> goodList = goodRepository.findAll();
        assertThat(goodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSellerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodRepository.findAll().size();
        // set the field null
        good.setSellerId(null);

        // Create the Good, which fails.
        GoodDTO goodDTO = goodMapper.toDto(good);


        restGoodMockMvc.perform(post("/api/goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodDTO)))
            .andExpect(status().isBadRequest());

        List<Good> goodList = goodRepository.findAll();
        assertThat(goodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodRepository.findAll().size();
        // set the field null
        good.setStock(null);

        // Create the Good, which fails.
        GoodDTO goodDTO = goodMapper.toDto(good);


        restGoodMockMvc.perform(post("/api/goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodDTO)))
            .andExpect(status().isBadRequest());

        List<Good> goodList = goodRepository.findAll();
        assertThat(goodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGoods() throws Exception {
        // Initialize the database
        goodRepository.saveAndFlush(good);

        // Get all the goodList
        restGoodMockMvc.perform(get("/api/goods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(good.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].imgUrl").value(hasItem(DEFAULT_IMG_URL)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].sellerId").value(hasItem(DEFAULT_SELLER_ID.intValue())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.intValue())));
    }
    
    @Test
    @Transactional
    public void getGood() throws Exception {
        // Initialize the database
        goodRepository.saveAndFlush(good);

        // Get the good
        restGoodMockMvc.perform(get("/api/goods/{id}", good.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(good.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.imgUrl").value(DEFAULT_IMG_URL))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.sellerId").value(DEFAULT_SELLER_ID.intValue()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGood() throws Exception {
        // Get the good
        restGoodMockMvc.perform(get("/api/goods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGood() throws Exception {
        // Initialize the database
        goodRepository.saveAndFlush(good);

        int databaseSizeBeforeUpdate = goodRepository.findAll().size();

        // Update the good
        Good updatedGood = goodRepository.findById(good.getId()).get();
        // Disconnect from session so that the updates on updatedGood are not directly saved in db
        em.detach(updatedGood);
        updatedGood
            .name(UPDATED_NAME)
            .imgUrl(UPDATED_IMG_URL)
            .detail(UPDATED_DETAIL)
            .price(UPDATED_PRICE)
            .sellerId(UPDATED_SELLER_ID)
            .stock(UPDATED_STOCK);
        GoodDTO goodDTO = goodMapper.toDto(updatedGood);

        restGoodMockMvc.perform(put("/api/goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodDTO)))
            .andExpect(status().isOk());

        // Validate the Good in the database
        List<Good> goodList = goodRepository.findAll();
        assertThat(goodList).hasSize(databaseSizeBeforeUpdate);
        Good testGood = goodList.get(goodList.size() - 1);
        assertThat(testGood.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGood.getImgUrl()).isEqualTo(UPDATED_IMG_URL);
        assertThat(testGood.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testGood.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testGood.getSellerId()).isEqualTo(UPDATED_SELLER_ID);
        assertThat(testGood.getStock()).isEqualTo(UPDATED_STOCK);
    }

    @Test
    @Transactional
    public void updateNonExistingGood() throws Exception {
        int databaseSizeBeforeUpdate = goodRepository.findAll().size();

        // Create the Good
        GoodDTO goodDTO = goodMapper.toDto(good);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoodMockMvc.perform(put("/api/goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Good in the database
        List<Good> goodList = goodRepository.findAll();
        assertThat(goodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGood() throws Exception {
        // Initialize the database
        goodRepository.saveAndFlush(good);

        int databaseSizeBeforeDelete = goodRepository.findAll().size();

        // Delete the good
        restGoodMockMvc.perform(delete("/api/goods/{id}", good.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Good> goodList = goodRepository.findAll();
        assertThat(goodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
