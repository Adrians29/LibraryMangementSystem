package org.adrianegl.library.domain;

import lombok.Getter;
import org.adrianegl.library.util.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Library {
    @Getter private List<Item> items;
    @Getter private Map<String, User> users;
    private Queue<User> waitingQueue;
    private Stack<String> transactionHistory;

    public Library() {
        items = new ArrayList<>();
        users = new HashMap<>();
        waitingQueue = new LinkedList<>();
        transactionHistory = new Stack<>();
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

        if (item.getStatus() != Item.ItemStatus.IN_STORE) {
            waitingQueue.offer(user);

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
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elements = line.split(",");

                String type = elements[0];
                String id = elements[1];
                String name = elements[2];

                User user = switch (type) {
                    case "Student" -> {
                      int number = Integer.parseInt(id.substring(1));
                      if (number >= Constants.nextStudentId) {
                          Constants.nextStudentId = number + 1;
                      }

                      yield new Student(name);
                    }
                    case "Teacher" -> {
                        int number = Integer.parseInt(id.substring(1));
                        if (number >= Constants.nextTeacherId) {
                            Constants.nextTeacherId = number + 1;
                        }

                        yield new Teacher(name);
                    }
                    case "Admin" -> {
                        int number = Integer.parseInt(id.substring(1));
                        if (number >= Constants.nextAdminId) {
                            Constants.nextAdminId = number + 1;
                        }

                        yield new Admin(name);
                    }
                    default -> throw new RuntimeException("Invalid user type");
                };
                user.id = id;
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
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elements = line.split(",");
                String type = elements[0];
                String id = elements[1];
                Item item = switch (type) {
                    case "Book" -> {
                        int number = Integer.parseInt(id.substring(1));

                        if (number >= Constants.nextItemId) {
                            Constants.nextItemId = number + 1;
                        }

                        Book book = new Book(elements[2], Item.ItemStatus.valueOf(elements[3]), elements[4], elements[5], elements[6]);
                        book.id = id;
                        yield book;
                    }
                    case "DVD" -> {
                        int number = Integer.parseInt(id.substring(1));

                        if (number >= Constants.nextItemId) {
                            Constants.nextItemId = number + 1;
                        }

                        DVD dvd = new DVD(elements[2], Item.ItemStatus.valueOf(elements[3]), elements[4], Integer.parseInt(elements[5]));
                        dvd.id = id;
                        yield dvd;
                    }
                    case "Magazine" -> {
                        int number = Integer.parseInt(id.substring(1));

                        if (number >= Constants.nextItemId) {
                            Constants.nextItemId = number + 1;
                        }

                        Magazine magazine = new Magazine(elements[2], Item.ItemStatus.valueOf(elements[3]), elements[4], Integer.parseInt(elements[5]));
                        magazine.id = id;
                        yield magazine;
                    }
                    default -> throw new RuntimeException("Invalid item type");
                };
                addItem(item);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Item CSV file not found");
        }
    }

    public void exportUsers() {
        File file = new File(Constants.USERS_CSV_PATH);

        try (FileWriter fileWriter = new FileWriter(file)){
            for (User user : users.values()) {
                fileWriter.write(String.format("%s,%s,%s\n", user.getClass().getSimpleName(), user.getId(), user.getName()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportItems() {
        File file = new File(Constants.ITEMS_CSV_PATH);

        try (FileWriter fileWriter = new FileWriter(file)) {
            for (Item item : items) {
                if (item instanceof Book book) {
                    fileWriter.write(String.format("%s,%s,%s,%s,%s,%s\n", book.getId(), book.getTitle(), book.getStatus(), book.getIsbn(), book.getAuthor(), book.getGenre()));
                } else if (item instanceof DVD dvd) {
                    fileWriter.write(String.format("%s,%s,%s,%s,%d\n", dvd.getId(), dvd.getTitle(), dvd.getStatus(), dvd.getDirector(), dvd.getDuration()));
                } else if (item instanceof Magazine magazine) {
                    fileWriter.write(String.format("%s,%s,%s,%s,%d\n", magazine.getId(), magazine.getTitle(), magazine.getStatus(), magazine.getPublisher(), magazine.getIssueNumber()));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printTransactionHistory() {
        System.out.println("=====HISTORY=====");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    public void printWaitingQueue() {
        System.out.println("=====WAITING-QUEUE=====");
        for (User user : waitingQueue) {
            System.out.println(user.getName());
        }
    }

}
