package com.chunmiao.web.rest;

import com.chunmiao.service.GoodOrderService;
import com.chunmiao.web.rest.errors.BadRequestAlertException;
import com.chunmiao.service.dto.GoodOrderDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.chunmiao.domain.GoodOrder}.
 */
@RestController
@RequestMapping("/api")
public class GoodOrderResource {

    private final Logger log = LoggerFactory.getLogger(GoodOrderResource.class);

    private static final String ENTITY_NAME = "goodOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoodOrderService goodOrderService;

    public GoodOrderResource(GoodOrderService goodOrderService) {
        this.goodOrderService = goodOrderService;
    }

    /**
     * {@code POST  /good-orders} : Create a new goodOrder.
     *
     * @param goodOrderDTO the goodOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goodOrderDTO, or with status {@code 400 (Bad Request)} if the goodOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/good-orders")
    public ResponseEntity<GoodOrderDTO> createGoodOrder(@Valid @RequestBody GoodOrderDTO goodOrderDTO) throws URISyntaxException {
        log.debug("REST request to save GoodOrder : {}", goodOrderDTO);
        if (goodOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new goodOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoodOrderDTO result = goodOrderService.save(goodOrderDTO);
        return ResponseEntity.created(new URI("/api/good-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /good-orders} : Updates an existing goodOrder.
     *
     * @param goodOrderDTO the goodOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goodOrderDTO,
     * or with status {@code 400 (Bad Request)} if the goodOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goodOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/good-orders")
    public ResponseEntity<GoodOrderDTO> updateGoodOrder(@Valid @RequestBody GoodOrderDTO goodOrderDTO) throws URISyntaxException {
        log.debug("REST request to update GoodOrder : {}", goodOrderDTO);
        if (goodOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GoodOrderDTO result = goodOrderService.save(goodOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goodOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /good-orders} : get all the goodOrders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goodOrders in body.
     */
    @GetMapping("/good-orders")
    public List<GoodOrderDTO> getAllGoodOrders() {
        log.debug("REST request to get all GoodOrders");
        return goodOrderService.findAll();
    }

    /**
     * {@code GET  /good-orders/:id} : get the "id" goodOrder.
     *
     * @param id the id of the goodOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goodOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/good-orders/{id}")
    public ResponseEntity<GoodOrderDTO> getGoodOrder(@PathVariable Long id) {
        log.debug("REST request to get GoodOrder : {}", id);
        Optional<GoodOrderDTO> goodOrderDTO = goodOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goodOrderDTO);
    }

    /**
     * {@code DELETE  /good-orders/:id} : delete the "id" goodOrder.
     *
     * @param id the id of the goodOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/good-orders/{id}")
    public ResponseEntity<Void> deleteGoodOrder(@PathVariable Long id) {
        log.debug("REST request to delete GoodOrder : {}", id);
        goodOrderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
