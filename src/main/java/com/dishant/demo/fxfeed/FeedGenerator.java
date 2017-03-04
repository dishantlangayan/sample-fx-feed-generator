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
package com.dishant.demo.fxfeed;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.dishant.demo.fxfeed.model.Quote;

/**
 * The Sample Feed Generator loads FX currency quotes from currency-data.json
 * file in JSON format. The quotes are then parsed and stored in the internal
 * cache for publishing. You can also copy the content from the following URL in
 * the currency-data.json file to update quote prices:
 * 
 * http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote?format=json
 * 
 * @author Dishant Langayan
 */
@Component
public class FeedGenerator {
    private static final Logger logger = LoggerFactory.getLogger(FeedGenerator.class);

    private static final String CURRENCY_DATA_FILE = "currency-data.json";

    // Some constants so we don't create string each iteration
    private static final String JSON_LIST_KEY = "list";
    private static final String JSON_RESOURCE_KEY = "resource";
    private static final String JSON_RESOURCES_KEY = "resources";
    private static final String JSON_FIELDS_KEY = "fields";
    private static final String JSON_SYMBOL_KEY = "symbol";
    private static final String JSON_NAME_KEY = "name";
    private static final String JSON_PRICE_KEY = "price";
    private static final String JSON_TIMESTAMP_KEY = "ts";
    private static final String JSON_TYPE_KEY = "type";
    private static final String JSON_UTCTIME_KEY = "utctime";
    private static final String JSON_VOLUME_KEY = "volume";

    @Autowired
    private FeedCache feedCache;

    private FxFeedConfigProperties config;

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    private ScheduledFuture<?> handle;

    public FeedGenerator(FxFeedConfigProperties config) {
        this.config = config;
    }

    @PostConstruct
    public void activate() throws Exception {
        logger.info("Starting feed generator...");

        logger.info("Loading FX currency data from file: {}", CURRENCY_DATA_FILE);

        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        handle = scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                // TODO: record metrics for monitoring

                try {
                    ClassPathResource classPathRes = new ClassPathResource(CURRENCY_DATA_FILE);
                    try (InputStream is = classPathRes.getInputStream(); JsonReader rdr = Json.createReader(is)) {
                        JsonObject rootObj = rdr.readObject();
                        JsonArray resources = rootObj.getJsonObject(JSON_LIST_KEY).getJsonArray(JSON_RESOURCES_KEY);
                        if (resources != null) {
                            for (JsonObject result : resources.getValuesAs(JsonObject.class)) {
                                JsonObject quoteObj = result.getJsonObject(JSON_RESOURCE_KEY)
                                        .getJsonObject(JSON_FIELDS_KEY);
                                String symbol = result.getJsonObject(JSON_RESOURCE_KEY).getJsonObject(JSON_FIELDS_KEY)
                                        .getString(JSON_SYMBOL_KEY);

                                Quote quote = new Quote(quoteObj.getString(JSON_NAME_KEY),
                                        new BigDecimal(quoteObj.getString(JSON_PRICE_KEY)),
                                        new BigDecimal(quoteObj.getString(JSON_PRICE_KEY)), symbol,
                                        Long.parseLong(quoteObj.getString(JSON_TIMESTAMP_KEY)),
                                        quoteObj.getString(JSON_TYPE_KEY), quoteObj.getString(JSON_UTCTIME_KEY),
                                        Long.parseLong(quoteObj.getString(JSON_VOLUME_KEY)));
                                // Add quote to cache
                                addOrUpdateQuote(symbol, quote);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 0, config.getFeedPollingInterval(), TimeUnit.SECONDS);

        logger.info("Feed generator has started");
    }

    private void addOrUpdateQuote(String symbol, Quote quote) {
        // Use the name field (USD/CAD) as the key for the cache
        feedCache.addOrUpdateQuote(quote.getName(), quote);
        // You can also use the symbol (CAD=X) as the key for the cache
        // feedCache.addOrUpdateQuote(symbol, quote);
    }

    @PreDestroy
    public void deactivate() throws Exception {
        logger.info("Feed generator is stopping...");

        // Stop publishing thread
        if (handle != null) {
            handle.cancel(true);
        }

        logger.info("Feed generator has stopped gracefully");
    }
}