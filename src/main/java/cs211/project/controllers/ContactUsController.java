package cs211.project.controllers;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class ContactUsController {
    @FXML
    private AnchorPane navbar;
    @FXML
    private AnchorPane footer;

    @FXML
    private Circle professor1;
    @FXML
    private Circle professor2;

    @FXML
    private Circle professor3;

    @FXML
    private Circle professor4;

    @FXML
    private Circle admin1;

    @FXML
    private Circle admin2;

    @FXML
    private Circle admin3;

    @FXML
    private Circle admin4;
    private HashMap<String, Object> data;
    @FXML
    private void initialize() {
        data = FXRouter.getData();
        data.remove("eventId");
        data.remove("teamId");
        setProfessorImage();
        setAdminImage();

        //Navbar
        FXMLLoader navbarComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/navbar.fxml"));
        //Footer
        FXMLLoader footerComponentLoader = new FXMLLoader(getClass().getResource("/cs211/project/views/footer.fxml"));
        try {
            //Navbar
            AnchorPane navbarComponent = navbarComponentLoader.load();
            NavbarController navbarController = navbarComponentLoader.getController();
            navbarController.setData(data);
            navbar.getChildren().add(navbarComponent);

            //Footer
            AnchorPane footerComponent = footerComponentLoader.load();
            footer.getChildren().add(footerComponent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setProfessorImage() {
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cs211/project/views/assets/professor/professor1.jpg")));
        this.professor1.setFill(new ImagePattern(image1));

        Image image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cs211/project/views/assets/professor/professor2.jpg")));
        this.professor2.setFill(new ImagePattern(image2));

        Image image3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cs211/project/views/assets/professor/professor3.jpg")));
        this.professor3.setFill(new ImagePattern(image3));

        Image image4 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cs211/project/views/assets/professor/professor4.jpg")));
        this.professor4.setFill(new ImagePattern(image4));
    }

    public void setAdminImage() {
        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cs211/project/views/assets/admin/admin1.jpg")));
        this.admin1.setFill(new ImagePattern(image1));

        Image image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cs211/project/views/assets/admin/admin2.jpg")));
        this.admin2.setFill(new ImagePattern(image2));

        Image image3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cs211/project/views/assets/admin/admin3.jpg")));
        this.admin3.setFill(new ImagePattern(image3));

        Image image4 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/cs211/project/views/assets/admin/admin4.jpg")));
        this.admin4.setFill(new ImagePattern(image4));
    }
}
