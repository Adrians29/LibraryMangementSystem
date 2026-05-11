package org.adrianegl.library.domain;

import lombok.Getter;

import java.util.*;

public class Library {
    @Getter private List<Item> items;
    @Getter private Map<String, User> users;
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

    public void borrowItem(String userId, String itemId) {

    }

    public void returnItem(String userId, String itemId) {
        User user = users.get(userId);
    }

}
