package com.chunmiao.service.mapper;


import com.chunmiao.domain.*;
import com.chunmiao.service.dto.GoodOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GoodOrder} and its DTO {@link GoodOrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GoodOrderMapper extends EntityMapper<GoodOrderDTO, GoodOrder> {



    default GoodOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        GoodOrder goodOrder = new GoodOrder();
        goodOrder.setId(id);
        return goodOrder;
    }
}
