package ru.apolyakov.video_calls.calls_service.rest_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-контроллер для служебных запросов Consul
 * ! не документировать в публичном REST API
 *
 * @author apolyakov
 * @since 27.07.2019
 */
@RestController
@RequestMapping("/consul")
public class HealthCheckController {
    /**
     * Заглушка для метода проверки состояния сервиса
     * На данный момент сервис считается рабочим пока запущен
     * @return HttpStatus
     */
    @GetMapping("/health-check")
    public ResponseEntity<String> myCustomCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
