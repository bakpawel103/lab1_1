/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class OfferItem {
    // product
    private Product product;
    private int quantity;

    // money
    private Money totalCost;

    // discount
    private Discount discount;

    public OfferItem(String currency, String productId, BigDecimal productPrice, String productName, Date productSnapshotDate, String productType,
            int quantity) {
        this(currency, productId, productPrice, productName, productSnapshotDate, productType, quantity, null, null);
    }

    public OfferItem(String currency, String productId, BigDecimal productPrice, String productName, Date productSnapshotDate, String productType,
            int quantity, BigDecimal discount, String discountCause) {
        product = new Product(productId, productName, productSnapshotDate, productType, productPrice);
        this.quantity = quantity;

        this.discount = new Discount(new Money(discount), discountCause);

        totalCost = new Money(productPrice).multiply(quantity).subtract(this.discount.getValue());
    }

    public String getProductId() { return product.getId(); }

    public BigDecimal getProductPrice() { return product.getPrice(); }

    public String getProductName() { return product.getName(); }

    public Date getProductSnapshotDate() { return product.getSnapshotDate(); }

    public String getProductType() { return product.getType(); }

    public BigDecimal getTotalCost() { return totalCost.getValue(); }

    public String getTotalCostCurrency() { return totalCost.getCurrency(); }

    public BigDecimal getDiscount() { return discount.getValue().getValue(); }

    public String getDiscountCause() { return discount.getCause(); }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCost.hashCode(), discount.hashCode(), product.hashCode(),  quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OfferItem other = (OfferItem) obj;
        return Objects.equals(totalCost, other.totalCost)
                && Objects.equals(discount, other.discount)
                && Objects.equals(product, other.product)
                && quantity == other.quantity;
    }

    /**
     *
     * @param other
     * @param delta
     *            acceptable percentage difference
     * @return
     */
    public boolean sameAs(OfferItem other, double delta) {
        if (product.getPrice() == null) {
            if (other.product.getPrice() != null) {
                return false;
            }
        } else if (!product.getPrice().equals(other.product.getPrice())) {
            return false;
        }
        if (product.getName() == null) {
            if (other.product.getName() != null) {
                return false;
            }
        } else if (!product.getName().equals(other.product.getName())) {
            return false;
        }

        if (product.getId() == null) {
            if (other.product.getId() != null) {
                return false;
            }
        } else if (!product.getId().equals(other.product.getId())) {
            return false;
        }
        if (product.getType() == null) {
            if (other.product.getType() != null) {
                return false;
            }
        } else if (!product.getType().equals(other.product.getType())) {
            return false;
        }

        if (quantity != other.quantity) {
            return false;
        }

        BigDecimal max;
        BigDecimal min;
        if (totalCost.getValue().compareTo(other.totalCost.getValue()) > 0) {
            max = totalCost.getValue();
            min = other.totalCost.getValue();
        } else {
            max = other.totalCost.getValue();
            min = totalCost.getValue();
        }

        BigDecimal difference = max.subtract(min);
        BigDecimal acceptableDelta = max.multiply(BigDecimal.valueOf(delta / 100));

        return acceptableDelta.compareTo(difference) > 0;
    }
}
