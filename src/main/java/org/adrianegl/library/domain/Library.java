package org.adrianegl.library.domain;

import lombok.Getter;
import org.adrianegl.library.interfaces.Reportable;
import org.adrianegl.library.util.Constants;

import java.io.File;
import java.io.FileNotFoundException;
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

    /**
     * Search an item/items by a keyword
     * @param keyword the keyword of the item
     * @return the item
     */
    public List<Item> searchStream(String keyword) {
        Set<String> seenTitles = new HashSet<>();
        return  items.stream()
                .filter(item -> item.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .filter(item -> seenTitles.add(item.getTitle()))
                .collect(Collectors.toList());
    }

    /**
     * Search an item with a keyword and index
     * @param keyword the keyword of the item
     * @param index the index of the item
     * @return the item of the keyword, index
     */
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

    /**
     * Search a book by its author
     * @param author the author of the book
     * @return the books of the author
     */
    public List<Book> searchByAuthor(String author) {
        return items.stream()
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item)
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Generates a report of the items of the library
     */
    public void generateItemsReport() {
        System.out.println("LIBRARY-REPORT");
        for (Item item : items) {
            System.out.println(item.getReportDetails());
        }
    }

    /**
     * Sorts the items by their titles
     */
    public void sortItemsByTitle() {
        items.sort(Comparator.comparing(Item::getTitle));
    }

    /**
     * Sorts the users by their names
     */
    public void sortUsersByName() {
        List<User> sortedUsers = new ArrayList<>(users.values());

        sortedUsers.sort(Comparator.comparing(User::getName));
        for (User user : sortedUsers) {
            System.out.println(user.getName());
        }
    }


    /**
     * Load the users from the CSV file
     */
    public void loadUsers() {
        File file = new File(Constants.USERS_CSV_PATH);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] elements = line.split(",");

                String type = elements[0];
                String name = elements[2];

                User user = switch (type) {
                    case "Student" -> new Student(name);
                    case "Teacher" -> new Teacher(name);
                    case "Admin" -> new Admin(name);
                    default -> throw new RuntimeException("Invalid user type");
                };
                registerUser(user);
            }
        } catch (FileNotFoundException e) {
            System.out.println("User CSV file not found");
        }
    }

    /**
     * Load the items from the CSV file
     */
    public void loadItems() {
        File file = new File(Constants.ITEMS_CSV_PATH);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] elements = line.split(",");
                String type = elements[0];
                Item item = switch (type) {
                    case "Book" -> new Book(elements[1], Item.ItemStatus.valueOf(elements[2]), elements[3], elements[4], elements[5]);
                    case "DVD" -> new DVD(elements[1], Item.ItemStatus.valueOf(elements[2]),elements[3], Integer.parseInt(elements[4]));
                    case "Magazine" -> new Magazine(elements[1], Item.ItemStatus.valueOf(elements[2]), elements[3], Integer.parseInt(elements[4]));
                    default -> throw new RuntimeException("Invalid item type");
                };
                addItem(item);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Item CSV file not found");
        }
    }

}
