/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.spring.cloud.config.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microsoft.azure.spring.cloud.config.AppConfigurationRefresh;
import com.microsoft.azure.spring.cloud.config.properties.AppConfigurationProperties;

@Configuration
@EnableConfigurationProperties(AppConfigurationProperties.class)
public class AppConfigurationWebAutoConfiguration {

    @Configuration
    static class AppConfiguraitonRefreshConfiguration {

        @Bean
        @ConditionalOnClass(name = "org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent")
        public BusPublisher busPublisher(ApplicationEventPublisher context) {
            return new BusPublisher(context);
        }

        @Bean
        @ConditionalOnClass(name = "org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties")
        public AppConfigurationRefreshEndpoint appConfigurationRefreshEndpoint(ContextRefresher contextRefresher,
                AppConfigurationProperties appConfiguration) {
            return new AppConfigurationRefreshEndpoint(contextRefresher, appConfiguration);
        }

        @Bean
        @ConditionalOnClass(name = {
                "org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties",
                "org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent" })
        public AppConfigurationRefreshBusEndpoint appConfigurationRefreshBusEndpoint(BusPublisher busPublisher,
                AppConfigurationProperties appConfiguration) {
            return new AppConfigurationRefreshBusEndpoint(busPublisher, appConfiguration);
        }

    }

    @Bean
    @ConditionalOnClass(RefreshEndpoint.class)
    public ConfigListener configListener(AppConfigurationRefresh appConfigurationRefresh,
            ApplicationContext context, AppConfigurationProperties properties) {
        return new ConfigListener(appConfigurationRefresh, true);

    }
}
