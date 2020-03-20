package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;
import java.util.Objects;

public class Discount {
    private Money value;
    private String cause;

    public Discount(Money discount, String cause) {
        this.value = discount;
        this.cause = cause;
    }

    public Money getValue() {
        return value;
    }

    public String getCause() {
        return cause;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Objects.equals(cause, discount.cause) &&
                Objects.equals(value, discount.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cause, value);
    }
}
