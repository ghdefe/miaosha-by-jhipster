package com.chunmiao.service;

import com.chunmiao.domain.Good;
import com.chunmiao.repository.GoodRepository;
import com.chunmiao.service.dto.GoodDTO;
import com.chunmiao.service.mapper.GoodMapper;
import com.chunmiao.web.rest.errors.StockShortageException;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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
     * 使用悲观锁 扣减库存
     *
     * @param goodId the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public GoodDTO decreaseStockPessimistic(Long goodId) {
        log.debug("悲观锁扣减" + goodId + "数据库库存");
        Good goodOnDb = goodRepository.getStockByIdPessimistic(goodId);
        goodOnDb.setStock(goodOnDb.getStock() - 1);
        goodRepository.save(goodOnDb);
        return goodMapper.toDto(goodOnDb);
    }

    /**
     * 使用乐观锁 扣减库存
     *
     * @param goodId the entity to save.
     * @return the persisted entity.
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public GoodDTO decreaseStockOptimistic(Long goodId) throws Exception {
        log.debug("乐观锁扣减" + goodId + "数据库库存");
        Good good = goodRepository.getOne(goodId);
        Long stock = good.getStock();
        int col = 0;
        for (int i = 0; i < 5; i++) {
            if (stock <= 0) throw new StockShortageException();
            col = goodRepository.decreseStockOptimistic(goodId, stock);
            if (col > 0) break;
            stock = goodRepository.getStockById(goodId);
        }
        if (col <= 0) throw new RuntimeException("扣减库存失败");
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
