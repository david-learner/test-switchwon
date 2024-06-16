package com.switchwon.payments.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_MERCHANT")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userMerchantId;
    private String name;

    protected Merchant() {}

    public Merchant(Long id, String userMerchantId, String name) {
        this.id = id;
        this.userMerchantId = userMerchantId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getUserMerchantId() {
        return userMerchantId;
    }

    public String getName() {
        return name;
    }
}
