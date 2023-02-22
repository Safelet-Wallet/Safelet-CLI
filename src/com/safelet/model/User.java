package com.safelet.model;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

public class User {

	private Long id;

	private String username;

	private String password;

	private String name;

	private String surnames;

	private LocalDateTime registyDate;

	private Set<Transaction> received = new LinkedHashSet<>();

	private Set<Transaction> sent = new LinkedHashSet<>();

	private Set<User> contacts = new LinkedHashSet<>();

	private Set<Wallet> wallets = new LinkedHashSet<>();

}
