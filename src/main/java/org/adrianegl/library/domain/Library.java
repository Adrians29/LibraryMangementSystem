package org.adrianegl.library.domain;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

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

    private Item findItemById(String itemId) {
        for (Item item : items) {
            if (item.getId().equals(itemId)) {
                return item;
            }
        }
        return null;
    }

    public void borrowItem(String userId, String itemId) {
        User user = users.get(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Item item = findItemById(itemId);

        if (item == null) {
            throw new IllegalArgumentException("Item not found");
        }

        if (!user.canBorrow(item)) {
            throw new IllegalStateException("Item unavailable, User added to queue");
        }

        if (!user.canBorrow(item)) {
            throw new IllegalStateException("Borrow limit exceeded");
        }

        user.borrowItem(item);

        item.setStatus(Item.ItemStatus.BORROWED);
        transactionHistory.push(user.getName() + " borrowed " + item.getTitle());

    }

    public void returnItem(String userId, String itemId) {
        User user = users.get(userId);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        Item item = findItemById(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Item not found");
        }
        user.returnItem(item);
        item.setStatus(Item.ItemStatus.IN_STORE);
        transactionHistory.push(user.getName() + " returned " + item.getTitle());

    }

    //Load User

    public void loadUsersFromCSV(String path) {

    }

    // Search by streams
    public List<Item> searchStream(String keyword) {
        Set<String> seenTitles = new HashSet<>();
        return  items.stream()
                .filter(item -> item.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .filter(item -> seenTitles.add(item.getTitle()))
                .collect(Collectors.toList());
    }

    // Recursive search
    public Item searchRecursive(String keyword, int index) {
        if (index >= items.size()) {
            return null;
        }

        Item item = items.get(index);

        if (item.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
            return item;
        }

        return searchRecursive(keyword, index + 1);
    }

}
