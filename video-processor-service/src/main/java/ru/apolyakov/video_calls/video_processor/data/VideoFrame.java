package ru.apolyakov.video_calls.video_processor.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.opencv.core.Mat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class VideoFrame implements Serializable {
    private String sid;
    private String userLogin;
    private String frameJson;
    private Date timestamp;
}
