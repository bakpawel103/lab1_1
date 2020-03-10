package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;

public class Discount {
    private Money value;
    private String cause;

    public Discount(Money discount, String cause) {
        this.value = discount;
        this.cause = cause;
    }

    public Money getDiscount() {
        return value;
    }

    public void setDiscount(Money discount) {
        this.value = discount;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
