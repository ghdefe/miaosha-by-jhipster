package com.chunmiao.web.rest;

import com.chunmiao.RedisTestContainerExtension;
import com.chunmiao.MiaoshaByJhipsterApp;
import com.chunmiao.domain.SecActivity;
import com.chunmiao.repository.SecActivityRepository;
import com.chunmiao.service.SecActivityService;
import com.chunmiao.service.dto.SecActivityDTO;
import com.chunmiao.service.mapper.SecActivityMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SecActivityResource} REST controller.
 */
@SpringBootTest(classes = MiaoshaByJhipsterApp.class)
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class SecActivityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_GOOD_ID = 1L;
    private static final Long UPDATED_GOOD_ID = 2L;

    private static final Long DEFAULT_AUTHOR = 1L;
    private static final Long UPDATED_AUTHOR = 2L;

    private static final LocalDate DEFAULT_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SecActivityRepository secActivityRepository;

    @Autowired
    private SecActivityMapper secActivityMapper;

    @Autowired
    private SecActivityService secActivityService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSecActivityMockMvc;

    private SecActivity secActivity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecActivity createEntity(EntityManager em) {
        SecActivity secActivity = new SecActivity()
            .name(DEFAULT_NAME)
            .goodId(DEFAULT_GOOD_ID)
            .author(DEFAULT_AUTHOR)
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return secActivity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecActivity createUpdatedEntity(EntityManager em) {
        SecActivity secActivity = new SecActivity()
            .name(UPDATED_NAME)
            .goodId(UPDATED_GOOD_ID)
            .author(UPDATED_AUTHOR)
            .start(UPDATED_START)
            .end(UPDATED_END);
        return secActivity;
    }

    @BeforeEach
    public void initTest() {
        secActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createSecActivity() throws Exception {
        int databaseSizeBeforeCreate = secActivityRepository.findAll().size();
        // Create the SecActivity
        SecActivityDTO secActivityDTO = secActivityMapper.toDto(secActivity);
        restSecActivityMockMvc.perform(post("/api/sec-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(secActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the SecActivity in the database
        List<SecActivity> secActivityList = secActivityRepository.findAll();
        assertThat(secActivityList).hasSize(databaseSizeBeforeCreate + 1);
        SecActivity testSecActivity = secActivityList.get(secActivityList.size() - 1);
        assertThat(testSecActivity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSecActivity.getGoodId()).isEqualTo(DEFAULT_GOOD_ID);
        assertThat(testSecActivity.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testSecActivity.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testSecActivity.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createSecActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = secActivityRepository.findAll().size();

        // Create the SecActivity with an existing ID
        secActivity.setId(1L);
        SecActivityDTO secActivityDTO = secActivityMapper.toDto(secActivity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecActivityMockMvc.perform(post("/api/sec-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(secActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SecActivity in the database
        List<SecActivity> secActivityList = secActivityRepository.findAll();
        assertThat(secActivityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGoodIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = secActivityRepository.findAll().size();
        // set the field null
        secActivity.setGoodId(null);

        // Create the SecActivity, which fails.
        SecActivityDTO secActivityDTO = secActivityMapper.toDto(secActivity);


        restSecActivityMockMvc.perform(post("/api/sec-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(secActivityDTO)))
            .andExpect(status().isBadRequest());

        List<SecActivity> secActivityList = secActivityRepository.findAll();
        assertThat(secActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = secActivityRepository.findAll().size();
        // set the field null
        secActivity.setStart(null);

        // Create the SecActivity, which fails.
        SecActivityDTO secActivityDTO = secActivityMapper.toDto(secActivity);


        restSecActivityMockMvc.perform(post("/api/sec-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(secActivityDTO)))
            .andExpect(status().isBadRequest());

        List<SecActivity> secActivityList = secActivityRepository.findAll();
        assertThat(secActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = secActivityRepository.findAll().size();
        // set the field null
        secActivity.setEnd(null);

        // Create the SecActivity, which fails.
        SecActivityDTO secActivityDTO = secActivityMapper.toDto(secActivity);


        restSecActivityMockMvc.perform(post("/api/sec-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(secActivityDTO)))
            .andExpect(status().isBadRequest());

        List<SecActivity> secActivityList = secActivityRepository.findAll();
        assertThat(secActivityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSecActivities() throws Exception {
        // Initialize the database
        secActivityRepository.saveAndFlush(secActivity);

        // Get all the secActivityList
        restSecActivityMockMvc.perform(get("/api/sec-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(secActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].goodId").value(hasItem(DEFAULT_GOOD_ID.intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }
    
    @Test
    @Transactional
    public void getSecActivity() throws Exception {
        // Initialize the database
        secActivityRepository.saveAndFlush(secActivity);

        // Get the secActivity
        restSecActivityMockMvc.perform(get("/api/sec-activities/{id}", secActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(secActivity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.goodId").value(DEFAULT_GOOD_ID.intValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.intValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSecActivity() throws Exception {
        // Get the secActivity
        restSecActivityMockMvc.perform(get("/api/sec-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSecActivity() throws Exception {
        // Initialize the database
        secActivityRepository.saveAndFlush(secActivity);

        int databaseSizeBeforeUpdate = secActivityRepository.findAll().size();

        // Update the secActivity
        SecActivity updatedSecActivity = secActivityRepository.findById(secActivity.getId()).get();
        // Disconnect from session so that the updates on updatedSecActivity are not directly saved in db
        em.detach(updatedSecActivity);
        updatedSecActivity
            .name(UPDATED_NAME)
            .goodId(UPDATED_GOOD_ID)
            .author(UPDATED_AUTHOR)
            .start(UPDATED_START)
            .end(UPDATED_END);
        SecActivityDTO secActivityDTO = secActivityMapper.toDto(updatedSecActivity);

        restSecActivityMockMvc.perform(put("/api/sec-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(secActivityDTO)))
            .andExpect(status().isOk());

        // Validate the SecActivity in the database
        List<SecActivity> secActivityList = secActivityRepository.findAll();
        assertThat(secActivityList).hasSize(databaseSizeBeforeUpdate);
        SecActivity testSecActivity = secActivityList.get(secActivityList.size() - 1);
        assertThat(testSecActivity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSecActivity.getGoodId()).isEqualTo(UPDATED_GOOD_ID);
        assertThat(testSecActivity.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testSecActivity.getStart()).isEqualTo(UPDATED_START);
        assertThat(testSecActivity.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingSecActivity() throws Exception {
        int databaseSizeBeforeUpdate = secActivityRepository.findAll().size();

        // Create the SecActivity
        SecActivityDTO secActivityDTO = secActivityMapper.toDto(secActivity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecActivityMockMvc.perform(put("/api/sec-activities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(secActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SecActivity in the database
        List<SecActivity> secActivityList = secActivityRepository.findAll();
        assertThat(secActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSecActivity() throws Exception {
        // Initialize the database
        secActivityRepository.saveAndFlush(secActivity);

        int databaseSizeBeforeDelete = secActivityRepository.findAll().size();

        // Delete the secActivity
        restSecActivityMockMvc.perform(delete("/api/sec-activities/{id}", secActivity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SecActivity> secActivityList = secActivityRepository.findAll();
        assertThat(secActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
