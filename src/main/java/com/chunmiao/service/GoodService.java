package com.chunmiao.service;

import com.chunmiao.domain.Good;
import com.chunmiao.repository.GoodRepository;
import com.chunmiao.service.dto.GoodDTO;
import com.chunmiao.service.mapper.GoodMapper;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Good}.
 */
@Service
@Transactional
public class GoodService {

    private final Logger log = LoggerFactory.getLogger(GoodService.class);

    private final GoodRepository goodRepository;

    private final GoodMapper goodMapper;

    public GoodService(GoodRepository goodRepository, GoodMapper goodMapper) {
        this.goodRepository = goodRepository;
        this.goodMapper = goodMapper;
    }

    /**
     * Save a good.
     *
     * @param goodDTO the entity to save.
     * @return the persisted entity.
     */
    public GoodDTO save(GoodDTO goodDTO) {
        log.debug("Request to save Good : {}", goodDTO);
        Good good = goodMapper.toEntity(goodDTO);
        good = goodRepository.save(good);
        // 删除redis缓存
        RedissonClient redissonClient = Redisson.create();
        RAtomicLong atomicLong = redissonClient.getAtomicLong("stock_" + good.getId());
        atomicLong.delete();
        return goodMapper.toDto(good);
    }

    /**
     * 扣减库存
     *
     * @param goodDTO the entity to save.
     * @return the persisted entity.
     */
    public GoodDTO decreaseStock(GoodDTO goodDTO) {
        log.debug("扣减" + goodDTO.getName() + "数据库库存");
        Good good = goodMapper.toEntity(goodDTO);
        good = goodRepository.save(good);
        return goodMapper.toDto(good);
    }

    /**
     * Get all the goods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GoodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Goods");
        return goodRepository.findAll(pageable)
            .map(goodMapper::toDto);
    }


    /**
     * Get one good by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GoodDTO> findOne(Long id) {
        log.debug("Request to get Good : {}", id);
        return goodRepository.findById(id)
            .map(goodMapper::toDto);
    }

    /**
     * Delete the good by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Good : {}", id);
        goodRepository.deleteById(id);
    }
}
