package com.oracle.sehub.appdev.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {"classpath:application.properties"})
@ConfigurationProperties
public class SDKProperties {
    private String userocid;
    private String fingerprint;
    private String key_file;
    private String tenancy;
    private String region;
    private int connectiontimeout;
    private int readtimeout;
    private String compartment;
    private String availableDomain;
    private String subnetId;
    private String shape;
    private String image;
    private String sshPublicKey;
    
    

    public String getUserocid() {
        return userocid;
    }

    public void setUserocid(String userocid) {
        this.userocid = userocid;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getKey_file() {
        return key_file;
    }

    public void setKey_file(String key_file) {
        this.key_file = key_file;
    }

    public String getTenancy() {
        return tenancy;
    }

    public void setTenancy(String tenancy) {
        this.tenancy = tenancy;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getConnectiontimeout() {
        return connectiontimeout;
    }

    public void setConnectiontimeout(int connectiontimeout) {
        this.connectiontimeout = connectiontimeout;
    }

    public int getReadtimeout() {
        return readtimeout;
    }

    public void setReadtimeout(int readtimeout) {
        this.readtimeout = readtimeout;
    }

    public String getCompartment() {
        return compartment;
    }

    public void setCompartment(String compartment) {
        this.compartment = compartment;
    }

	public String getAvailableDomain() {
		return availableDomain;
	}

	public void setAvailableDomain(String availableDomain) {
		this.availableDomain = availableDomain;
	}

	public String getSubnetId() {
		return subnetId;
	}

	public void setSubnetId(String subnetId) {
		this.subnetId = subnetId;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSshPublicKey() {
		return sshPublicKey;
	}

	public void setSshPublicKey(String sshPublicKey) {
		this.sshPublicKey = sshPublicKey;
	}
    
    
}
