
package com.server.commIt.model;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "quotes")
@EntityListeners(AuditingEntityListener.class)

public class Quote {
        @Id
        @Column(name = "quote_name", nullable = false)
        private String quoteName;

        @Column(name = "price", nullable = false)
        private int price;

        @Column(name = "items", nullable = false)
        private String items;

    public String getQuoteName() {
        return quoteName;
    }

    public void setQuoteName(String quoteName) {
        this.quoteName = quoteName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "quote{" +
                "quoteName='" + quoteName + '\'' +
                ", price=" + price +
//                ", items=" + Arrays.toString(items) +
                ", items=" + items +
                '}';
    }
}
