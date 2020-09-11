package ru.apolyakov.client_app.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.apolyakov.client_app.config.ControllersConfiguration;
import ru.apolyakov.client_app.service.VideoCaptureService;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;

public class CallSessionController extends AbstractWindowController {
    @Autowired
    private VideoCaptureService videoCaptureService;

    @Autowired
    @Qualifier("mainView")
    private ControllersConfiguration.ViewHolder mainView;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Button start_btn;
    @FXML
    private ImageView currentFrame;

    private Timer timer;

    @FXML
    public void initialize() {
    }

    @PostConstruct
    public void init() {
    }

    public void startCameraCapture() {
        // get the ImageView object for showing the video stream
        final ImageView frameView = currentFrame;
        // check if the capture stream is opened
        if (!videoCaptureService.isOpened())
        {
            videoCaptureService.startCapture();
            // grab a frame every 33 ms (30 frames/sec)
            TimerTask frameGrabber = new TimerTask() {
                @Override
                public void run() {
                    Image tmp = videoCaptureService.grabFrame();
                    Platform.runLater(() -> currentFrame.setImage(tmp));

                }
            };
            this.timer = new Timer();
            //set the timer scheduling, this allow you to perform frameGrabber every 33ms;
            this.timer.schedule(frameGrabber, 0, 33);
            this.start_btn.setText("Stop Camera");
        }
    }

    public void stopCameraCapture() {
        // get the ImageView object for showing the video stream
        final ImageView frameView = currentFrame;
        // check if the capture stream is opened
        if (videoCaptureService.isOpened()) {
            this.start_btn.setText("Start Camera");
            // stop the timer
            if (this.timer != null) {
                this.timer.cancel();
                this.timer = null;
            }
            videoCaptureService.stopCapture();
            // clear the image container
            frameView.setImage(null);
        }
    }

    @FXML
    protected void startCamera(ActionEvent event)
    {
            // get the ImageView object for showing the video stream
            final ImageView frameView = currentFrame;
            // check if the capture stream is opened
            if (!videoCaptureService.isOpened())
            {
                videoCaptureService.startCapture();
                // grab a frame every 33 ms (30 frames/sec)
                TimerTask frameGrabber = new TimerTask() {
                    @Override
                    public void run() {
                        Image tmp = videoCaptureService.grabFrame();
                        Platform.runLater(() -> currentFrame.setImage(tmp));

                    }
                };
                this.timer = new Timer();
                //set the timer scheduling, this allow you to perform frameGrabber every 33ms;
                this.timer.schedule(frameGrabber, 0, 33);
                this.start_btn.setText("Stop Camera");
            }
            else
            {
                this.start_btn.setText("Start Camera");
                // stop the timer
                if (this.timer != null)
                {
                    this.timer.cancel();
                    this.timer = null;
                }
                videoCaptureService.stopCapture();
                // clear the image container
                frameView.setImage(null);
            }
    }

    public void stopCall(MouseEvent mouseEvent) {
        stopCameraCapture();

        Parent mainViewParent = mainView.getParent();

        //Parent mainViewParent = FXMLLoader.load(getClass().getResource("fxml/pin.fxml"));
        Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        mainViewParent.setOnMousePressed(event1 -> {
            xOffset = event1.getSceneX();
            yOffset = event1.getSceneY();
        });
        mainViewParent.setOnMouseDragged(event12 -> {
            appStage.setX(event12.getScreenX() - xOffset);
            appStage.setY(event12.getScreenY() - yOffset);
        });

        Scene scene;
        if (mainViewParent.getScene() == null) {
            scene = new Scene(mainViewParent);
        } else {
            scene = mainViewParent.getScene();
        }
        appStage.setScene(scene);
        appStage.show();
    }
}
