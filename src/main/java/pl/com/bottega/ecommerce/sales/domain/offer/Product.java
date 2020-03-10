package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
    private String id;
    private String name;
    private Date snapshotDate;
    private String type;

    private Money price;

    public Product(String id, String name, Date snapshotDate, String type,
            BigDecimal cost, String currency) {
        this.id = id;
        this.name = name;
        this.snapshotDate = snapshotDate;
        this.type = type;
        this.price = new Money(cost, currency);
    }

    public String getProductId() {
        return id;
    }

    public String getProductName() {
        return name;
    }

    public Date getProductSnapshotDate() {
        return snapshotDate;
    }

    public String getProductType() {
        return type;
    }

    public Money getProductPrice() {
        return price;
    }
}
