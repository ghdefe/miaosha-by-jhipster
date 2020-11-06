package com.chunmiao.web.rest;

import com.chunmiao.MiaoshaByJhipsterApp;
import com.chunmiao.RedisTestContainerExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the ShopResource REST controller.
 *
 * @see ShopResource
 */
@SpringBootTest(classes = MiaoshaByJhipsterApp.class)
@ExtendWith(RedisTestContainerExtension.class)
public class ShopResourceIT {

    private MockMvc restMockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ShopResource shopResource = new ShopResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(shopResource)
            .build();
    }

    /**
     * Test good
     */
    @Test
    public void testGood() throws Exception {
        restMockMvc.perform(post("/api/shop/good"))
            .andExpect(status().isOk());
    }

    /**
     * Test goodWithActivity
     */
    @Test
    public void testGoodWithActivity() throws Exception {
        restMockMvc.perform(post("/api/shop/good-with-activity"))
            .andExpect(status().isOk());
    }

    /**
     * Test good
     */
    @Test
    public void testGood1() throws Exception {
        restMockMvc.perform(delete("/api/shop/good"))
            .andExpect(status().isOk());
    }
}
