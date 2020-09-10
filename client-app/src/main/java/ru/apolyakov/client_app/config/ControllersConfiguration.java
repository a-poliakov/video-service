package ru.apolyakov.client_app.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.apolyakov.client_app.controller.CallSessionController;
import ru.apolyakov.client_app.controller.CreateCallController;
import ru.apolyakov.client_app.controller.LoginController;
import ru.apolyakov.client_app.controller.MainController;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class ControllersConfiguration {
    @Bean(name = "mainView")
    public ViewHolder mainView() throws IOException {
        return loadView("fxml/main.fxml");
    }

    @Bean(name = "authView")
    public ViewHolder authView() throws IOException {
        return loadView("fxml/auth.fxml");
    }

    @Bean(name = "callView")
    public ViewHolder callView() throws IOException {
        return loadView("fxml/create_call.fxml");
    }

    @Bean(name = "createCallView")
    public ViewHolder createCallView() throws IOException {
        return loadView("fxml/create_call.fxml");
    }
    /**
     * Именно благодаря этому методу мы добавили контроллер в контекст спринга,
     * и заставили его произвести все необходимые инъекции.
     */
    @Bean
    public MainController getMainController(@Qualifier("mainView") ViewHolder mainView) throws IOException {
        return (MainController) mainView.getController();
    }

    @Bean
    public LoginController getLoginController(@Qualifier("authView") ViewHolder authView) throws IOException {
        return (LoginController) authView.getController();
    }

//    @Bean
//    public CallSessionController callSessionController(@Qualifier("callView") ViewHolder callView) throws IOException {
//        return (CallSessionController) callView.getController();
//    }

    @Bean
    public CreateCallController createCallController(@Qualifier("createCallView") ViewHolder createCallView) throws IOException {
        return (CreateCallController) createCallView.getController();
    }
    /**
     * Самый обыкновенный способ использовать FXML загрузчик.
     * Как раз-таки на этом этапе будет создан объект-контроллер,
     * произведены все FXML инъекции и вызван метод инициализации контроллера.
     */
    protected ViewHolder loadView(String url) throws IOException {
        InputStream fxmlStream = null;
        try {
            fxmlStream = getClass().getClassLoader().getResourceAsStream(url);
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            return new ViewHolder(loader.getRoot(), loader.getController());
        } finally {
            if (fxmlStream != null) {
                fxmlStream.close();
            }
        }
    }

    /**
     * Класс - оболочка: контроллер мы обязаны указать в качестве бина,
     * а view - представление, нам предстоит использовать в точке входа {@link ru.apolyakov.client_app.ClientAppApplication}.
     */
    @Data
    @AllArgsConstructor
    public static class ViewHolder {
        private Parent parent;
        private Object controller;
    }
}
