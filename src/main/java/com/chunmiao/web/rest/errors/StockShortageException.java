package com.chunmiao.web.rest.errors;

public class StockShortageException extends BadRequestAlertException {

    public StockShortageException() {
        super("库存不足!", "货品管理", "没有货品");
    }
}
