
package com.server.commIt.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


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

    @Column(name = "del", nullable = false)
    private int del;

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

    public int getDel() {
        return del;
    }

    public void setDel(int del) {
        this.del = del;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "quoteName='" + quoteName + '\'' +
                ", price=" + price +
                ", items='" + items + '\'' +
                ", del=" + del +
                '}';
    }
}
