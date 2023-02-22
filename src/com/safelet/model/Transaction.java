package com.safelet.model;

import java.time.LocalDateTime;

public class Transaction {

    private Long id;

    private User source;

    private User destiny;

    private LocalDateTime date;

    private Double amount;

    private Coin coin;
}
