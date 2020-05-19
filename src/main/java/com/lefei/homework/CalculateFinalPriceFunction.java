package com.lefei.homework;

import java.math.BigDecimal;
import java.util.function.Function;

public interface CalculateFinalPriceFunction {
    BigDecimal apply(
            Function<BigDecimal, BigDecimal> applyDiscount,
            Function<BigDecimal, BigDecimal> applyTax,
            BigDecimal listingPrice);
}
