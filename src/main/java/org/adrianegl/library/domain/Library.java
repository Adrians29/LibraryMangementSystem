package org.adrianegl.library.domain;

import java.util.*;

public class Library {
    private List<Item> items;
    private Map<String, User> users;
    private Queue<User> waitingQueue;
    private Stack<String> transactionHistory;
    private Set<String> uniqueTitles;

    public Library() {
        items = new ArrayList<>();
        users = new HashMap<>();
        waitingQueue = new LinkedList<>();
        transactionHistory = new Stack<>();
        uniqueTitles = new HashSet<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void registerUser(User user) {
        users.put(user.getId(), user);
    }
}
