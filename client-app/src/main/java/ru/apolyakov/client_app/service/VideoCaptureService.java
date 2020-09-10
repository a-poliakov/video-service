package ru.apolyakov.client_app.service;

import javafx.scene.image.Image;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;

@Component
public class VideoCaptureService {
    private VideoCapture capture;

    @PostConstruct
    public void init() {
        OpenCV.loadShared();
        capture = new VideoCapture();
    }

    public boolean isOpened() {
        return capture.isOpened();
    }

    public void startCapture() {
        //todo: start capture when user signed in successfully
        if (!isOpened()) {
            // start the video capture
            this.capture.open(0);
        }
    }

    public void stopCapture() {
        // release the camera
        this.capture.release();
    }

    public Image grabFrame()
    {
        //init
        Image imageToShow = null;
        Mat frame = new Mat();
        // check if the capture is open
        if (this.capture.isOpened())
        {
            try
            {
                // read the current frame
                this.capture.read(frame);
                // if the frame is not empty, process it
                if (!frame.empty())
                {
                    // convert the image to gray scale
                    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
                    // convert the Mat object (OpenCV) to Image (JavaFX)
                    imageToShow = mat2Image(frame);
                }
            }
            catch (Exception e)
            {
                // log the error
                System.err.println("ERROR: " + e.getMessage());
            }
        }
        return imageToShow;
    }

    private Image mat2Image(Mat frame)
    {
        // create a temporary buffer
        MatOfByte buffer = new MatOfByte();
        // encode the frame in the buffer
        Imgcodecs.imencode(".png", frame, buffer);
        // build and return an Image created from the image encoded in the buffer
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }
}
