package ru.apolyakov.video_calls.api_gateway.service.discovery;

import java.net.URI;
import java.util.Optional;

/**
 * Сервис для работы с механизмом обнаружения сервисов на основе Consul.
 *
 * @author apolyakov
 * @since 27.07.2019
 */
public interface DiscoveryService {
    /**
     * Находит первый из доступных инстансов сервиса по его имени
     * @param serviceName имя сервиса
     * @return URI сервиса для доступа к нему по http
     */
    Optional<URI> serviceUrl(String serviceName);
}
