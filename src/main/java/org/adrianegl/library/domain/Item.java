package org.adrianegl.library.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.adrianegl.library.interfaces.Reportable;
import org.adrianegl.library.util.Constants;

@Getter
@ToString
public abstract class Item implements Reportable {
    protected String id;
    protected String title;
    @Setter protected ItemStatus status;



    public enum ItemStatus {
        BORROWED, IN_STORE, LOST;
    }
    public Item(String title, ItemStatus status) {
        this.id = String.format("%04d", Constants.nextId++);
        this.title = title;
            this.status = status;
    }

    @Override
    public String getReportDetails() {
        return "ID: "
                    + id
                    + "| Title: "
                    + title
                    + "| Status: "
                    + status;
    }
}
