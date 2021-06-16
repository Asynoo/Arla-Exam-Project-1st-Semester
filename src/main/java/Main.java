import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage loginStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/LoginView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        loginStage.setTitle("Arla Management Software");
        loginStage.setScene(scene);
        loginStage.initStyle(StageStyle.UNDECORATED);
        loginStage.setResizable(false);
        loginStage.show();
    }
}
