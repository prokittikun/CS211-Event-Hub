package cs211.project.controllers;

import cs211.project.models.User;
import cs211.project.models.collections.UserCollection;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.UserListFileDatasource;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class NavbarController {
    @FXML
    private Circle navImageProfile;

    @FXML
    private Label name;

    @FXML
    private Label role;

    @FXML
    private Pane manageDropdown;
    private Boolean showManageDropdown = false;
    private Boolean showProfileDropdown = false;
    private HashMap<String, Object> data;
    @FXML
    private Pane profileDropdown;

    private Datasource<UserCollection> userDatasource;
    private User user;

    @FXML
    private Pane currentTheme;

    @FXML
    private ComboBox<String> fontSizeComboBox;

    @FXML
    private ComboBox<String> fontStyleComboBox;


    private static Boolean isDarkTheme = false;

    @FXML
    private VBox managementOption;


    @FXML
    private void initialize() {
        userDatasource = new UserListFileDatasource("data", "user.csv");
        data = new HashMap<String, Object>();
        manageDropdown.setVisible(showManageDropdown);
        profileDropdown.setVisible(showProfileDropdown);

        isDarkTheme = FXRouter.isDarkTheme;
        if(isDarkTheme){
            currentTheme.setTranslateX(48);
        }
        else{
            currentTheme.setTranslateX(2);
        }
        setFontSizeComboBox();
        setFontStyleClass();
    }

    //Setter
    public void setNavbarImage(String imageName) {
        Image image = new Image("file:data" + File.separator + "image" + File.separator + "avatar" + File.separator + imageName);
        navImageProfile.setFill(new ImagePattern(image));
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
        user = userDatasource.query("id = " + data.get("userId").toString()).getAllUsers().get(0);
        setNavbarImage(user.getAvatar());
        name.setText(user.getFirstName());
        role.setText(user.getRole().equals("user") ? "ผู้ใช้งาน" : "ผู้ดูแลระบบ");
        if (!user.getRole().equals("admin")) {
            managementOption.getChildren().remove(0);
        }
    }

    public void setName(Label name) {
        this.name = name;
    }

    public void setRole(Label role) {
        this.role = role;
    }

    void setFontSizeComboBox() {
        fontSizeComboBox.getItems().addAll("Normal", "Medium", "Large");
        fontSizeComboBox.setValue(FXRouter.currentFontSize);
        fontSizeComboBox.setOnAction(event -> {
            String selectedFontSize = fontSizeComboBox.getValue();
            switch (selectedFontSize) {
                case "Normal":
                    FXRouter.setCurrentFontSizeStylesheet("/cs211/project/views/style/font-small.css", "Normal");
                    break;
                case "Medium":
                    FXRouter.setCurrentFontSizeStylesheet("/cs211/project/views/style/font-medium.css", "Medium");
                    break;
                case "Large":
                    FXRouter.setCurrentFontSizeStylesheet("/cs211/project/views/style/font-large.css", "Large");
                    break;
                default:
                    break;
            }
            try {
                FXRouter.reloadCurrentRoute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    void setFontStyleClass() {
        fontStyleComboBox.getItems().addAll("Kanit", "Prompt", "Sarabun");
        fontStyleComboBox.setValue(FXRouter.currentFontStyleClass);
        fontStyleComboBox.setOnAction(event -> {
            String selectedFontClass = fontStyleComboBox.getValue();
            switch (selectedFontClass) {
                case "Kanit":
                    FXRouter.setCurrentFontStyleClass("kanit");
                    break;
                case "Prompt":
                    FXRouter.setCurrentFontStyleClass("prompt");
                    break;
                case "Sarabun":
                    FXRouter.setCurrentFontStyleClass("sarabun");
                    break;
                default:
                    break;
            }
            try {
                FXRouter.reloadCurrentRoute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    //Method
    @FXML
    void onHandleGoToContactUs(ActionEvent event) {
        try {
            FXRouter.goTo("contactUs", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleGoToHomePage(ActionEvent event) {
        try {
            FXRouter.goTo("index", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleGoToMyEvent(MouseEvent event) {
        try {
            FXRouter.goTo("myEvent", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleGoToMyTeam(MouseEvent  event) {
        try {
            FXRouter.goTo("myTeam", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleGoToMyProfile(MouseEvent event) {
        try {
            FXRouter.goTo("profile", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleGoToHistory(MouseEvent event) {
        try {
            FXRouter.goTo("eventHistory", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleGoToDashboard(MouseEvent event) {
        try {
            FXRouter.goTo("dashboard", data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHandleManageDropdown(MouseEvent event) {
        manageDropdown.setVisible(!showManageDropdown);
        showManageDropdown = !showManageDropdown;

    }

    @FXML
    void onHandleProfileDropdown(MouseEvent event) {
        profileDropdown.setVisible(!showProfileDropdown);
        showProfileDropdown = !showProfileDropdown;
    }

    @FXML
    void onChangeTheme(MouseEvent event) {
        animationThemeChange();
    }


    @FXML
    void onHandleLogout(ActionEvent event) {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void animationThemeChange() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.3), currentTheme);
        if (isDarkTheme) {
            transition.setToX(2);
        } else {
            transition.setToX(48);
        }
        transition.play();
        isDarkTheme = !isDarkTheme;
        FXRouter.setGlobalStylesheet(isDarkTheme ? "/cs211/project/views/style/globalDark.css" : "/cs211/project/views/style/global.css");
        FXRouter.isDarkTheme = isDarkTheme;
        try {
            FXRouter.reloadCurrentRoute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
