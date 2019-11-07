package com.oracle.sehub.appdev.autoconfigure;

import com.oracle.bmc.ClientConfiguration;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.identity.Identity;
import com.oracle.bmc.identity.IdentityClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Identity.class)
@EnableConfigurationProperties(SDKProperties.class)
public class IdentityAutoConfigure {
    private SimpleAuthenticationDetailsProvider provider;
    private SDKProperties sdkProperties;

    @Autowired
    public IdentityAutoConfigure(SimpleAuthenticationDetailsProvider provider, SDKProperties sdkProperties) {
        this.provider = provider;
        this.sdkProperties = sdkProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public Identity identity() {
        Identity identity = new IdentityClient(provider, ClientConfiguration.builder().connectionTimeoutMillis(sdkProperties.getConnectiontimeout()).readTimeoutMillis(sdkProperties.getReadtimeout()).build());
        identity.setRegion(Region.fromRegionId(sdkProperties.getRegion()));
        return identity;
    }
}


