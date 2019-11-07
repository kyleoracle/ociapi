package com.oracle.sehub.appdev.service;

import com.oracle.bmc.identity.Identity;
import com.oracle.bmc.identity.requests.ListAvailabilityDomainsRequest;
import com.oracle.bmc.identity.responses.ListAvailabilityDomainsResponse;
import com.oracle.sehub.appdev.autoconfigure.SDKProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(SDKProperties.class)
public class IdentityService {
    private SDKProperties _sdkProperties;
    private Identity _identity;

    @Autowired
    public IdentityService(Identity identity, SDKProperties sdkProperties) {
        this._identity = identity;
        this._sdkProperties = sdkProperties;
    }

    public ListAvailabilityDomainsResponse listAvailabilityDomainsResponse() {
    	ListAvailabilityDomainsRequest req = ListAvailabilityDomainsRequest.builder()
				.compartmentId(_sdkProperties.getCompartment()).build();
        return _identity.listAvailabilityDomains(req);
    }
}
