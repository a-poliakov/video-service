package ru.apolyakov.client_app.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import ru.apolyakov.client_app.service.VideoCaptureService;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;

public class CallSessionController extends AbstractWindowController {
    @Autowired
    private VideoCaptureService videoCaptureService;

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

    public void startCall(ActionEvent actionEvent) {
    }

    public void openSettings(ActionEvent actionEvent) {
    }
}
