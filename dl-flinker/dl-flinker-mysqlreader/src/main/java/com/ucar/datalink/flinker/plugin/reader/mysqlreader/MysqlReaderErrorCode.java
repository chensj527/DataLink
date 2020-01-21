package com.ucar.datalink.flinker.plugin.reader.mysqlreader;

import com.ucar.datalink.flinker.api.spi.ErrorCode;

public enum MysqlReaderErrorCode implements ErrorCode {
    ;

    private final String code;
    private final String description;

    private MysqlReaderErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return String.format("Code:[%s], Description:[%s]. ", this.code,
                this.description);
    }
}
