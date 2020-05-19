package com.lefei.homework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

@Configuration
public class FpConfiguration {

    @Bean
    public Function<BigDecimal, BigDecimal> calculateFinalPriceForListingPrice(
            @Value("${homework.discount.rate}") String discountRateString,
            @Value("${homework.tax.rate}") String taxRateString,
            @Value("${homework.applyDiscountThreshold}") String applyDiscountThresholdString,
            BiPredicate<BigDecimal, BigDecimal> applyDiscountRuleFunction,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> applyDiscountFunction,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> applyTaxFunction,
            CalculateFinalPriceFunction calculateFinalPriceFunction
    ) {
        BigDecimal discountRate = new BigDecimal(discountRateString);
        BigDecimal taxRate = new BigDecimal(taxRateString);
        BigDecimal applyDiscountThreshold = new BigDecimal(applyDiscountThresholdString);

        return generateCurriedCalculateFinalPrice(applyDiscountThreshold, discountRate, taxRate, applyDiscountRuleFunction, applyDiscountFunction, applyTaxFunction, calculateFinalPriceFunction);
    }

    private Function<BigDecimal, BigDecimal> generateCurriedCalculateFinalPrice(
            BigDecimal applyDiscountThreshold,
            BigDecimal discountRate,
            BigDecimal taxRate,
            BiPredicate<BigDecimal, BigDecimal> applyDiscountRule,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> applyDiscount,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> applyTax,
            CalculateFinalPriceFunction calculateFinalPriceFunction
    ) {

        Predicate<BigDecimal> applyRule = curry(applyDiscountRule).apply(applyDiscountThreshold);
        Function<BigDecimal, BigDecimal> applyDiscountForAmount = curry(applyDiscount).apply(discountRate);
        Function<BigDecimal,BigDecimal> applyTaxForAmount = curry(applyTax).apply(taxRate);

        Function<BigDecimal, BigDecimal> calculateFinalPriceForListingPrice = curry(calculateFinalPriceFunction)
                .apply(applyRule)
                .apply(applyDiscountForAmount)
                .apply(applyTaxForAmount);
        return calculateFinalPriceForListingPrice;
    }

    private Function<BigDecimal, Function<BigDecimal, BigDecimal>>
    curry(BiFunction<BigDecimal, BigDecimal, BigDecimal> function) {
        return t -> u -> function.apply(t, u);
    }

    private Function<BigDecimal, Predicate<BigDecimal>>
    curry(BiPredicate<BigDecimal, BigDecimal> function){
        return applyDiscountThreshold -> listingPrice -> function.test(applyDiscountThreshold, listingPrice);
    }

    private Function<Predicate<BigDecimal>,
            Function<Function<BigDecimal, BigDecimal>,
            Function<Function<BigDecimal, BigDecimal>,
                    Function<BigDecimal, BigDecimal>>>>
    curry(CalculateFinalPriceFunction function) {
        return rule -> applyDiscountForAmount -> applyTaxForAmount -> listingPrice -> rule.test(listingPrice) ?
                function.apply(applyDiscountForAmount, applyTaxForAmount, listingPrice):
                function.apply(p -> p, applyTaxForAmount, listingPrice);
    }
}
