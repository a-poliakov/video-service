package ru.apolyakov.video_calls.api_gateway.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Обработчик ошибок авторизации, возникающих в процессе цепочки фильтров.
 * Используется ExceptionTranslationFilter при обработке полученной ошибки вместо defaultEntryPointMappings.
 *
 * Therefore this class isn't actually responsible for the commencement of authentication,
 * as it is in the case of other providers. It will be called if the user is rejected by
 * the AbstractPreAuthenticatedProcessingFilter, resulting in a null authentication.
 * <p>
 * The <code>commence</code> method will always return an
 * <code>HttpServletResponse.SC_FORBIDDEN</code> (403 error).
 *
 * @see org.springframework.security.web.access.ExceptionTranslationFilter
 *
 * @author apolyakov
 * @since 06.01.2019
 */
@Component
public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
