package com.lefei.homework;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.BiPredicate;

@Component
public class ApplyDiscountRule implements BiPredicate<BigDecimal, BigDecimal> {
    @Override
    public boolean test(BigDecimal applyDiscountThreshold , BigDecimal listingPrice) {
        return applyDiscountThreshold.compareTo(listingPrice) <= 0;
    }
}
