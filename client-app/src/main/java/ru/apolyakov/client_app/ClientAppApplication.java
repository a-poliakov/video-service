package ru.apolyakov.client_app;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.apolyakov.client_app.config.ControllersConfiguration;
import ru.apolyakov.client_app.config.UiConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ClientAppApplication extends AbstractJavaFxApplicationSupport {
    @Autowired
    private UiConfigProperties uiConfigProperties;

    @Autowired
    @Qualifier("authView")
    private ControllersConfiguration.ViewHolder view;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(uiConfigProperties.getTitle());
        Scene scene = new Scene(view.getParent());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        //primaryStage.setResizable(true);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launchApp(ClientAppApplication.class, args);
    }
}
