package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;

public class Discount {
    private Money discount;
    private String cause;

    public Discount(Money discount, String cause) {
        this.discount = discount;
        this.cause = cause;
    }

    public Money getDiscount() {
        return discount;
    }

    public void setDiscount(Money discount) {
        this.discount = discount;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
