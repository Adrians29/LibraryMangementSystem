package org.adrianegl.library.domain;

public class Magazine extends Item {
    private String publisher;
    private int issueNumber;

    public Magazine(String title, ItemStatus status, String publisher, int issueNumber) {
        super(title, status);

        this.publisher = publisher;
        this.issueNumber = issueNumber;
    }
}
