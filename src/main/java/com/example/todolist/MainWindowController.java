package com.example.todolist;

import com.example.todolist.datamodel.ToDoData;
import com.example.todolist.datamodel.ToDoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class MainWindowController {
    private List<ToDoItem> todoItems;
    @FXML
    private ListView<ToDoItem> todoListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadlineLabel;
    @FXML
    public BorderPane mainBorderPane;

    public void initialize() {
//        ToDoItem item1 = new ToDoItem("Mail birthday card", "Buy a 30th birthday card for jhon",
//                LocalDate.of(2016, Month.APRIL, 25));
//        ToDoItem item2 = new ToDoItem("Doctor's Appointment", "See Dr. Smith at 123 Main Street. Bring paperwork",
//                LocalDate.of(2016, Month.MAY, 25));
//        ToDoItem item3 = new ToDoItem("Finish design proposal for client", "I promised Mike I'd email website mockups by Friday 22 of April",
//                LocalDate.of(2016, Month.APRIL, 22));
//        ToDoItem item4 = new ToDoItem("Pickup doug at the train station", "Doug's arriving on March 23 on the 5:00 train",
//                LocalDate.of(2016, Month.MARCH, 23));
//        ToDoItem item5 = new ToDoItem("Pickup dry cleaning", "The clothes should be ready by wednesday",
//                LocalDate.of(2016, Month.APRIL, 20));
//
//        todoItems = new ArrayList<ToDoItem>();
//        todoItems.add(item1);
//        todoItems.add(item2);
//        todoItems.add(item3);
//        todoItems.add(item4);
//        todoItems.add(item5);
//
//        ToDoData.getInstance().setTodoItems(todoItems);

        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItem>() {
            @Override
            public void changed(ObservableValue<? extends ToDoItem> observableValue, ToDoItem oldValue, ToDoItem newValue) {
                if(newValue != null) {
                    ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.getDetails());
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");   //"d M yy");
                    deadlineLabel.setText(df.format(item.getDeadLine()));
                }
            }
        });

        todoListView.getItems().setAll(ToDoData.getInstance().getTodoItems());
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void showNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add new ToDo Item");
        dialog.setHeaderText("Use this dialog to create a new todao item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            ToDoItem newItem = controller.processResults();
            todoListView.getItems().setAll(ToDoData.getInstance().getTodoItems());
            todoListView.getSelectionModel().select(newItem);
            System.out.println("OK pressed");
        } else {
            System.out.println("Cancel pressed");
        }
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