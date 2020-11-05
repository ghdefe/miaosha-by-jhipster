package com.chunmiao.web.rest;

import com.chunmiao.service.GoodService;
import com.chunmiao.web.rest.errors.BadRequestAlertException;
import com.chunmiao.service.dto.GoodDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.chunmiao.domain.Good}.
 */
@RestController
@RequestMapping("/api")
public class GoodResource {

    private final Logger log = LoggerFactory.getLogger(GoodResource.class);

    private static final String ENTITY_NAME = "good";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoodService goodService;

    public GoodResource(GoodService goodService) {
        this.goodService = goodService;
    }

    /**
     * {@code POST  /goods} : Create a new good.
     *
     * @param goodDTO the goodDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goodDTO, or with status {@code 400 (Bad Request)} if the good has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/goods")
    public ResponseEntity<GoodDTO> createGood(@Valid @RequestBody GoodDTO goodDTO) throws URISyntaxException {
        log.debug("REST request to save Good : {}", goodDTO);
        if (goodDTO.getId() != null) {
            throw new BadRequestAlertException("A new good cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoodDTO result = goodService.save(goodDTO);
        return ResponseEntity.created(new URI("/api/goods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /goods} : Updates an existing good.
     *
     * @param goodDTO the goodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goodDTO,
     * or with status {@code 400 (Bad Request)} if the goodDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/goods")
    public ResponseEntity<GoodDTO> updateGood(@Valid @RequestBody GoodDTO goodDTO) throws URISyntaxException {
        log.debug("REST request to update Good : {}", goodDTO);
        if (goodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GoodDTO result = goodService.save(goodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goodDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /goods} : get all the goods.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goods in body.
     */
    @GetMapping("/goods")
    public ResponseEntity<List<GoodDTO>> getAllGoods(Pageable pageable) {
        log.debug("REST request to get a page of Goods");
        Page<GoodDTO> page = goodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /goods/:id} : get the "id" good.
     *
     * @param id the id of the goodDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goodDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/goods/{id}")
    public ResponseEntity<GoodDTO> getGood(@PathVariable Long id) {
        log.debug("REST request to get Good : {}", id);
        Optional<GoodDTO> goodDTO = goodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goodDTO);
    }

    /**
     * {@code DELETE  /goods/:id} : delete the "id" good.
     *
     * @param id the id of the goodDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/goods/{id}")
    public ResponseEntity<Void> deleteGood(@PathVariable Long id) {
        log.debug("REST request to delete Good : {}", id);
        goodService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
