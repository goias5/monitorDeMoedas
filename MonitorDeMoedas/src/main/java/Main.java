import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;

public class Main extends Application {
    public static void main(String args[]) throws IOException {
        launch(args);
    }

    public static void deleteTree(File inFile) {
        if (inFile.isFile()) {
            inFile.delete();
        } else {
            File files[] = inFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteTree(files[i]);
            }
        }
    }

    public static String pegaCaminhoBase(){
        String filePath = new File("").getAbsolutePath();
        String fullFilePath = filePath.concat("\\tempData\\");
        return fullFilePath;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Main.class.getResource("telaMonitorMoedas.fxml"));
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        primaryStage.setTitle("Monitor de Moedas");
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        double width = screenSize.getWidth();
//        double height = screenSize.getHeight();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();
    }
}
