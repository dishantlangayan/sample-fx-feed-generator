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
package com.dishant.demo.fxfeed.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Helper utility methods.
 * 
 * @author Dishant Langayan
 */
public final class FxFeedUtils {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    public static final BigDecimal ZERO = new BigDecimal("0").setScale(6, RoundingMode.UP);

    public static BigDecimal calcPercentage(BigDecimal base, BigDecimal pct) {
        return base.multiply(pct).divide(ONE_HUNDRED, 6, RoundingMode.UP);
    }

    public static BigDecimal calcChange(BigDecimal oldValue, BigDecimal newValue) {
        return newValue.subtract(oldValue).setScale(6, RoundingMode.UP);
    }

    public static BigDecimal calcChangePercentage(BigDecimal oldValue, BigDecimal change) {
        if (change.compareTo(ZERO) == 0) {
            return ZERO;
        }
        return change.divide(oldValue, 6, RoundingMode.UP).multiply(ONE_HUNDRED).setScale(6, RoundingMode.UP);
    }
}
