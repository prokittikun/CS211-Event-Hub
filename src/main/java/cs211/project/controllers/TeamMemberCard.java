package cs211.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class TeamMemberCard {
    @FXML
    private Circle image;

    @FXML
    private String imagePath;

    @FXML
    private Label name;

    @FXML
    private Label role;

    @FXML
    private Pane menuDropdown;

    private Boolean showMenuDropdown = false;


    public TeamMemberCard(){
        this.image = new Circle();
        this.name = new Label();
        this.role = new Label();
    }

    @FXML
    private void initialize() {
        menuDropdown.setVisible(false);
    }
    @FXML
    void onHandleMenuDropdownClick(MouseEvent event) {
        showMenuDropdown = !showMenuDropdown;
        menuDropdown.setVisible(showMenuDropdown);
    }
    public void setData(){
        Image randomImage = new Image("https://picsum.photos/200");
        image.setFill(new ImagePattern(randomImage));
        name.setText("John Doe");
        role.setText("Member");
    }

    public String getImage() {
        return imagePath;
    }

    public String getName() {
        return name.getText();
    }

    public String getRole() {
        return role.getText();
    }

    public void setImage(String image) {
        Image randomImage = new Image(image);
        imagePath = image;
        this.image.setFill(new ImagePattern(randomImage));
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setRole(String role) {
        this.role.setText(role);
    }
}
