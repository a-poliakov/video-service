package ru.apolyakov.video_calls.video_processor.service.recognize;

import org.opencv.core.Mat;

public interface RecognizeFaceService {
    public Mat recognize(Mat input);
}
