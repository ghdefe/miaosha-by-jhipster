package com.chunmiao.service;

import com.chunmiao.domain.GoodOrder;
import com.chunmiao.repository.GoodOrderRepository;
import com.chunmiao.service.dto.GoodOrderDTO;
import com.chunmiao.service.mapper.GoodOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link GoodOrder}.
 */
@Service
@Transactional
public class GoodOrderService {

    private final Logger log = LoggerFactory.getLogger(GoodOrderService.class);

    private final GoodOrderRepository goodOrderRepository;

    private final GoodOrderMapper goodOrderMapper;

    public GoodOrderService(GoodOrderRepository goodOrderRepository, GoodOrderMapper goodOrderMapper) {
        this.goodOrderRepository = goodOrderRepository;
        this.goodOrderMapper = goodOrderMapper;
    }

    /**
     * Save a goodOrder.
     *
     * @param goodOrderDTO the entity to save.
     * @return the persisted entity.
     */
    public GoodOrderDTO save(GoodOrderDTO goodOrderDTO) {
        log.debug("Request to save GoodOrder : {}", goodOrderDTO);
        GoodOrder goodOrder = goodOrderMapper.toEntity(goodOrderDTO);
        goodOrder = goodOrderRepository.save(goodOrder);
        return goodOrderMapper.toDto(goodOrder);
    }

    /**
     * Get all the goodOrders.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GoodOrderDTO> findAll() {
        log.debug("Request to get all GoodOrders");
        return goodOrderRepository.findAll().stream()
            .map(goodOrderMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one goodOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GoodOrderDTO> findOne(Long id) {
        log.debug("Request to get GoodOrder : {}", id);
        return goodOrderRepository.findById(id)
            .map(goodOrderMapper::toDto);
    }

    /**
     * Delete the goodOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GoodOrder : {}", id);
        goodOrderRepository.deleteById(id);
    }
}
