package com.switchwon.payments.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * 결제 서비스 내 결제수단
 * 모든 결제는 포인트로 처리된다.
 * 포인트는 원화와 1대1로 페깅된다. 예) 1포인트 = 1원
 */
@Entity
@Table(name = "TB_POINT")
public class Point {

    // 포인트가 고정된 화폐 단위
    public static Currency PEGGED_CURRENCY = Currency.getInstance("KRW");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    private BigDecimal amount;

    protected Point() {
    }

    public Point(Long id, User user, BigDecimal amount) {
        this.id = id;
        this.user = user;
        this.amount = amount;
    }

    public Money toMoney() {
        return new Money(PEGGED_CURRENCY, amount);
    }

    public void add(Money money) {
        final var moneyFromPoint = toMoney();
        final var addedMoney = moneyFromPoint.add(money);
        this.amount = addedMoney.getAmount();
    }

    public void subtract(Money money) {
        final var moneyFromPoint = toMoney();
        final var subtractedMoney = moneyFromPoint.subtract(money);
        this.amount = subtractedMoney.getAmount();
    }

    public boolean hasSufficientPointToPay(Money moneyToPay) {
        return this.toMoney().isEqualOrBiggerThan(moneyToPay);
    }

    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", user=" + user +
                ", amount=" + amount +
                '}';
    }
}
