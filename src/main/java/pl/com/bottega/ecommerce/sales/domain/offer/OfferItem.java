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
        this.quantity = quantity;

        BigDecimal discountValue = new BigDecimal(0);
        if (discount != null) {
            discountValue = discountValue.add(discount);
        }

        totalCost = new Money(productPrice.multiply(new BigDecimal(quantity)).subtract(discountValue), currency);

        product = new Product(productId, productName, productSnapshotDate, productType, totalCost.getValue(), currency);

        this.discount = new Discount(new Money(discount, currency), discountCause);
    }

    public Discount getDiscount() { return discount; }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() { return product; }

    @Override
    public int hashCode() {
        return Objects.hash(totalCost.getCurrency(), discount, discount.getCause(), product.getProductId(), product.getProductName(), product.getPrice().getValue(), product.getProductSnapshotDate(), product.getProductType(),
                quantity, totalCost.getValue());
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
        return Objects.equals(totalCost.getCurrency(), other.totalCost.getCurrency())
               && Objects.equals(discount, other.discount)
               && Objects.equals(discount.getCause(), other.discount.getCause())
               && Objects.equals(product.getProductId(), other.product.getProductId())
               && Objects.equals(product.getProductName(), other.product.getProductName())
               && Objects.equals(product.getPrice(), other.product.getPrice())
               && Objects.equals(product.getProductSnapshotDate(), other.product.getProductSnapshotDate())
               && Objects.equals(product.getProductType(), other.product.getProductType())
               && quantity == other.quantity
               && Objects.equals(totalCost, other.totalCost);
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
        if (product.getProductName() == null) {
            if (other.product.getProductName() != null) {
                return false;
            }
        } else if (!product.getProductName().equals(other.product.getProductName())) {
            return false;
        }

        if (product.getProductId() == null) {
            if (other.product.getProductId() != null) {
                return false;
            }
        } else if (!product.getProductId().equals(other.product.getProductId())) {
            return false;
        }
        if (product.getProductType() == null) {
            if (other.product.getProductType() != null) {
                return false;
            }
        } else if (!product.getProductType().equals(other.product.getProductType())) {
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
