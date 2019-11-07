package com.oracle.sehub.appdev.autoconfigure;

import com.google.common.base.Supplier;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimplePrivateKeySupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
@ConditionalOnClass({SimpleAuthenticationDetailsProvider.class, SimplePrivateKeySupplier.class})
@EnableConfigurationProperties(SDKProperties.class)
public class OCIAuthProviderAutoConfigure {

    private SDKProperties sdkProperties;

    @Autowired
    public OCIAuthProviderAutoConfigure(SDKProperties sdkProperties) {
        this.sdkProperties = sdkProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public SimpleAuthenticationDetailsProvider simpleCredentialsProvider() {
        //Supplier<InputStream> ks = new SimplePrivateKeySupplier(sdkProperties.getKey_file());
        Supplier<InputStream> ks = new ClasspathKeySupplier(sdkProperties.getKey_file());
        System.out.println(sdkProperties.toString());
        return SimpleAuthenticationDetailsProvider.builder()
                .tenantId(sdkProperties.getTenancy())
                .userId(sdkProperties.getUserocid())
                .fingerprint(sdkProperties.getFingerprint())
                .privateKeySupplier(ks).build();
    }
}
