/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.azure.spring.cloud.autoconfigure.servicebus;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Warren Zhu
 */
@Getter
@Setter
@ConfigurationProperties("spring.cloud.azure.servicebus")
public class AzureServiceBusProperties {
    private String namespace;
}
