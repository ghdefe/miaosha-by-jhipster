package com.chunmiao.web.rest;

import com.chunmiao.service.SecActivityService;
import com.chunmiao.web.rest.errors.BadRequestAlertException;
import com.chunmiao.service.dto.SecActivityDTO;

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
 * REST controller for managing {@link com.chunmiao.domain.SecActivity}.
 */
@RestController
@RequestMapping("/api")
public class SecActivityResource {

    private final Logger log = LoggerFactory.getLogger(SecActivityResource.class);

    private static final String ENTITY_NAME = "secActivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SecActivityService secActivityService;

    public SecActivityResource(SecActivityService secActivityService) {
        this.secActivityService = secActivityService;
    }

    /**
     * {@code POST  /sec-activities} : Create a new secActivity.
     *
     * @param secActivityDTO the secActivityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new secActivityDTO, or with status {@code 400 (Bad Request)} if the secActivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sec-activities")
    public ResponseEntity<SecActivityDTO> createSecActivity(@Valid @RequestBody SecActivityDTO secActivityDTO) throws URISyntaxException {
        log.debug("REST request to save SecActivity : {}", secActivityDTO);
        if (secActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new secActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SecActivityDTO result = secActivityService.save(secActivityDTO);
        return ResponseEntity.created(new URI("/api/sec-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sec-activities} : Updates an existing secActivity.
     *
     * @param secActivityDTO the secActivityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated secActivityDTO,
     * or with status {@code 400 (Bad Request)} if the secActivityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the secActivityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sec-activities")
    public ResponseEntity<SecActivityDTO> updateSecActivity(@Valid @RequestBody SecActivityDTO secActivityDTO) throws URISyntaxException {
        log.debug("REST request to update SecActivity : {}", secActivityDTO);
        if (secActivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SecActivityDTO result = secActivityService.save(secActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, secActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sec-activities} : get all the secActivities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of secActivities in body.
     */
    @GetMapping("/sec-activities")
    public List<SecActivityDTO> getAllSecActivities() {
        log.debug("REST request to get all SecActivities");
        return secActivityService.findAll();
    }

    /**
     * {@code GET  /sec-activities/:id} : get the "id" secActivity.
     *
     * @param id the id of the secActivityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the secActivityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sec-activities/{id}")
    public ResponseEntity<SecActivityDTO> getSecActivity(@PathVariable Long id) {
        log.debug("REST request to get SecActivity : {}", id);
        Optional<SecActivityDTO> secActivityDTO = secActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(secActivityDTO);
    }

    /**
     * {@code DELETE  /sec-activities/:id} : delete the "id" secActivity.
     *
     * @param id the id of the secActivityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sec-activities/{id}")
    public ResponseEntity<Void> deleteSecActivity(@PathVariable Long id) {
        log.debug("REST request to delete SecActivity : {}", id);
        secActivityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
