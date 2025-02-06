import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Program extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carregar o FXML da tela principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/FXMLTelaPrincipal.fxml"));
            Parent root = loader.load();

            // Obter o tamanho da tela do usuário
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double screenWidth = screenBounds.getWidth();
            double screenHeight = screenBounds.getHeight();

            // Criar a cena
            Scene scene = new Scene(root, screenWidth, screenHeight);

            // Configurar o palco (Stage)
            primaryStage.setTitle("Tela Principal");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // Impede redimensionamento
            primaryStage.setX(screenBounds.getMinX()); // Garante que inicie no canto superior esquerdo
            primaryStage.setY(screenBounds.getMinY());
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
