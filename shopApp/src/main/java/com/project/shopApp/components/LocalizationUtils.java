package com.project.shopApp.components;

import com.project.shopApp.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;
@Component
@RequiredArgsConstructor
public class LocalizationUtils {
    private final WebUtils webUtils;
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;
    public String getLocalizeMessage(String messageKey, Object... params) {
        HttpServletRequest request = WebUtils.getRequest();
        Locale locale= localeResolver.resolveLocale(request);
        return messageSource.getMessage(messageKey, params, locale);
    }
}
