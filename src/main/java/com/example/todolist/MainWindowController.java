package com.example.todolist;

import com.example.todolist.datamodel.ToDoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MainWindowController {
    private List<ToDoItem> todoItems;
    @FXML
    private ListView<ToDoItem> todoListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadlineLabel;

    public void initialize() {
        ToDoItem item1 = new ToDoItem("Mail birthday card", "Buy a 30th birthday card for jhon",
                LocalDate.of(2016, Month.APRIL, 25));
        ToDoItem item2 = new ToDoItem("Doctor's Appointment", "See Dr. Smith at 123 Main Street. Bring paperwork",
                LocalDate.of(2016, Month.MAY, 25));
        ToDoItem item3 = new ToDoItem("Finish design proposal for client", "I promised Mike I'd email website mockups by Friday 22 of April",
                LocalDate.of(2016, Month.APRIL, 22));
        ToDoItem item4 = new ToDoItem("Pickup doug at the train station", "Doug's arriving on March 23 on the 5:00 train",
                LocalDate.of(2016, Month.MARCH, 23));
        ToDoItem item5 = new ToDoItem("Pickup dry cleaning", "The clothes should be ready by wednesday",
                LocalDate.of(2016, Month.APRIL, 20));

        todoItems = new ArrayList<ToDoItem>();
        todoItems.add(item1);
        todoItems.add(item2);
        todoItems.add(item3);
        todoItems.add(item4);
        todoItems.add(item5);

        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItem>() {
            @Override
            public void changed(ObservableValue<? extends ToDoItem> observableValue, ToDoItem oldValue, ToDoItem newValue) {
                if(newValue != null) {
                    ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.getDetails());
                }
            }
        });

        todoListView.getItems().setAll(todoItems);
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();
    }
    @FXML
    public void handleClickListView() {
        ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
        deadlineLabel.setText(item.getDeadLine().toString());
//        System.out.println("The selected item is " + item);
//        StringBuilder sb = new StringBuilder(item.getDetails());
//        sb.append("\n\n\n\n");
//        sb.append("Due: ");
//        sb.append(item.getDeadLine().toString());
//        itemDetailsTextArea.setText(sb.toString());
    }
}