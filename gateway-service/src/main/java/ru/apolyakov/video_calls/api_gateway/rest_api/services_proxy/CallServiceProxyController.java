package ru.apolyakov.video_calls.api_gateway.rest_api.services_proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.apolyakov.video_calls.api_gateway.Constants;
import ru.apolyakov.video_calls.api_gateway.dto.CallDto;
import ru.apolyakov.video_calls.api_gateway.service.discovery.DiscoveryService;

import javax.naming.ServiceUnavailableException;
import java.net.URI;

@Component
public class CallServiceProxyController {
    private final DiscoveryService consulService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public CallServiceProxyController(DiscoveryService consulService)
    {
        this.consulService = consulService;
    }

    /**
     * Найти объявления, содержащие изображения похожие на данное
     * @param callDto новая сессия звонка
     * @return JSON с созданной сессией звонка
     * @throws ServiceUnavailableException микросервис не доступен
     */
    public String createCall(CallDto callDto) throws ServiceUnavailableException
    {
        URI service = consulService.serviceUrl(Constants.ServicesNames.CALLS_SERVICE)
                .map(s -> s.resolve(Constants.API_ENDPOINT + "/createCall"))
                .orElseThrow(ServiceUnavailableException::new);
        RequestEntity<CallDto> newCallSessionDto = new RequestEntity<>(callDto, HttpMethod.POST, service);
        return restTemplate.postForEntity(service, newCallSessionDto, String.class)
                .getBody();
    }

}
