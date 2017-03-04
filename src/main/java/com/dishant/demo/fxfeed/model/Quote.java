/**
 * Copyright 2017 Dishant Langayan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dishant.demo.fxfeed.model;

import java.math.BigDecimal;

/**
 * Contains market data for a single stock/currency symbol.
 * 
 * @author Dishant Langayan
 *
 */
public class Quote {

    private String name;
    private BigDecimal open;
    private BigDecimal price;
    private String symbol;
    private long timestamp;
    private String type;
    private String utcTime;
    private long volume;
    private BigDecimal change;
    private BigDecimal changePercent;

    public Quote() {
    }

    public Quote(String name, BigDecimal open, BigDecimal price, String symbol, long timestamp, String type,
            String utcTime, long volume) {
        this.name = name;
        this.open = open;
        this.price = price;
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.type = type;
        this.utcTime = utcTime;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUtcTime() {
        return utcTime;
    }

    public void setUtcTime(String utcTime) {
        this.utcTime = utcTime;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(BigDecimal changePercent) {
        this.changePercent = changePercent;
    }

    public String toJsonString() {
        String json = String.format(
                "{\"name\": \"%s\",\"price\": \"%s\",\"open\": \"%s\","
                        + "\"change\": \"%s\",\"chg_percent\": \"%s\",\"symbol\": \"%s\","
                        + "\"ts\": \"%s\",\"type\": \"%s\",\"utctime\": \"%s\",\"volume\": \"%s\"}",
                name, price, open, change, changePercent, symbol, timestamp, type, utcTime, volume);
        return json;
    }
}
