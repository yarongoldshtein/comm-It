package com.server.commIt.model;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "quote_log")
@EntityListeners(AuditingEntityListener.class)
public class QuoteLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "quote_id", nullable = false)
    private String quoteName;

    @Column(name = "operation", nullable = false)
    private Operation operation;

    @Column(name = "error_code", nullable = false)
    private int errorCode;

    @Column(name = "message", nullable = false)
    private String message;

    public QuoteLog(Date createdDate, String quoteName, Operation operation, int errorCode, String message) {
        this.createdDate = createdDate;
        this.quoteName = quoteName;
        this.operation = operation;
        this.errorCode = errorCode;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getQuoteName() {
        return quoteName;
    }

    public void setQuoteName(String quoteName) {
        this.quoteName = quoteName;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "QuoteLog{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", quoteName='" + quoteName + '\'' +
                ", operation=" + operation +
                ", errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }

    public enum Operation {
        CREATE,
        UPDATE,
        DELETE
    }
}
