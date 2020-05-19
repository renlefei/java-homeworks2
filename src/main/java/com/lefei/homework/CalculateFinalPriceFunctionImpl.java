package com.lefei.homework;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Function;

@Component
public class CalculateFinalPriceFunctionImpl implements CalculateFinalPriceFunction{
    @Override
    public BigDecimal apply(
            Function<BigDecimal, BigDecimal> applyDiscount,
            Function<BigDecimal, BigDecimal> applyTax,
            BigDecimal listingPrice) {
        return applyTax.compose(applyDiscount).apply(listingPrice);
    }
}
