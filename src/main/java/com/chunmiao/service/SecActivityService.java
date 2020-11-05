package com.chunmiao.service;

import com.chunmiao.domain.SecActivity;
import com.chunmiao.repository.SecActivityRepository;
import com.chunmiao.service.dto.SecActivityDTO;
import com.chunmiao.service.mapper.SecActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SecActivity}.
 */
@Service
@Transactional
public class SecActivityService {

    private final Logger log = LoggerFactory.getLogger(SecActivityService.class);

    private final SecActivityRepository secActivityRepository;

    private final SecActivityMapper secActivityMapper;

    public SecActivityService(SecActivityRepository secActivityRepository, SecActivityMapper secActivityMapper) {
        this.secActivityRepository = secActivityRepository;
        this.secActivityMapper = secActivityMapper;
    }

    /**
     * Save a secActivity.
     *
     * @param secActivityDTO the entity to save.
     * @return the persisted entity.
     */
    public SecActivityDTO save(SecActivityDTO secActivityDTO) {
        log.debug("Request to save SecActivity : {}", secActivityDTO);
        SecActivity secActivity = secActivityMapper.toEntity(secActivityDTO);
        secActivity = secActivityRepository.save(secActivity);
        return secActivityMapper.toDto(secActivity);
    }

    /**
     * Get all the secActivities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SecActivityDTO> findAll() {
        log.debug("Request to get all SecActivities");
        return secActivityRepository.findAll().stream()
            .map(secActivityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one secActivity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SecActivityDTO> findOne(Long id) {
        log.debug("Request to get SecActivity : {}", id);
        return secActivityRepository.findById(id)
            .map(secActivityMapper::toDto);
    }

    /**
     * Delete the secActivity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SecActivity : {}", id);
        secActivityRepository.deleteById(id);
    }
}
