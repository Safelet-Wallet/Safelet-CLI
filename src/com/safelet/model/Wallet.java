package com.safelet.model;

public class Wallet {

	private Long id;

	private String publicKey;

	private String privateKey;

	private User user;

	private Coin coin;

	private Double balance;

	public Coin getCoin() {
		return coin;
	}

	public Double getBalance() {
		return balance;
	}
}
