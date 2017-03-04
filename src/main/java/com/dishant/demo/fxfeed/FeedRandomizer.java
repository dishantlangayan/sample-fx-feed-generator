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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dishant.demo.fxfeed.model.Quote;
import com.dishant.demo.fxfeed.utils.FxFeedUtils;

/**
 * Changes the price for all quotes in the cache by a small factor. It will
 * randomly either add or subtract this change to simulate changing market data.
 * <p/>
 * The reason we do this here is so the dashboards on demo using this feed can
 * see some fluctuation in the price rather than a constant price.
 * <p/>
 * The randomization of all quotes is scheduled at a fixed rate. Usually this
 * should be = or > than the publish interval.
 * 
 * @author Dishant Langayan
 *
 */
@Component
public class FeedRandomizer {
    @Autowired
    private FeedCache feedCache;

    private static final BigDecimal CHANGE_FACTOR = new BigDecimal("0.009");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public FeedRandomizer() {
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Scheduled(fixedRate = 3000)
    public void randomizePrice() {
        Map<String, Quote> quotes = feedCache.getQuotes();

        if (quotes != null && quotes.size() > 0) {
            for (Map.Entry<String, Quote> entry : quotes.entrySet()) {
                Quote quote = entry.getValue();
                BigDecimal price = quote.getPrice();
                BigDecimal priceChange = FxFeedUtils.calcPercentage(price, CHANGE_FACTOR);

                // Random add/substract
                if (Math.random() < 0.5) {
                    price = price.add(priceChange);
                } else {
                    price = price.subtract(priceChange);
                }
                quote.setPrice(price);

                // Update Change & ChangePercent
                quote.setChange(FxFeedUtils.calcChange(quote.getOpen(), price));
                quote.setChangePercent(FxFeedUtils.calcChangePercentage(quote.getOpen(), quote.getChange()));

                // Update timestamps
                quote.setTimestamp(System.currentTimeMillis());
                quote.setUtcTime(dateFormat.format(new Date(quote.getTimestamp())));
            }
        }
    }
}
