package com.safelet.model;

import java.time.LocalDateTime;

public class Transaction {

    private Long id;

    private User source;

    private User destiny;

    private LocalDateTime date;

    private Double amount;

    private Coin coin;

    public User getSource() {
        return source;
    }

    public User getDestiny() {
        return destiny;
    }

    @Override
    public String toString() {
        return  "emisor: " + source +
                ", destinatario: " + destiny +
                ", Fecha de realización=" + date +
                ", Cantidad transferida=" + amount +
                ", Moneda transferida=" + coin;
    }
}
