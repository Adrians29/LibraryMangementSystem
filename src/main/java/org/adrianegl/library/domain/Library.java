package org.adrianegl.library.domain;

import java.util.*;

public class Library {
    private List<Item> items;
    private Map<String, User> users;
    private Queue<User> waitingQueue;
    private Stack<String> transactionHistory;
    private Set<String> uniqueTitles;
}
