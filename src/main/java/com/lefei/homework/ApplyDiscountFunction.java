package com.lefei.homework;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.BiFunction;

@Component
public class ApplyDiscountFunction implements BiFunction<BigDecimal, BigDecimal, BigDecimal> {

    @Override
    public BigDecimal apply(BigDecimal rate, BigDecimal amount) {
        BigDecimal discount = amount.multiply(rate);
        return amount.add(discount.negate());
    }
}
