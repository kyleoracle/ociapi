package com.oracle.sehub.appdev.autoconfigure;


import com.google.common.base.Supplier;

import java.beans.ConstructorProperties;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClasspathKeySupplier implements Supplier<InputStream> {
    private final String pemFilePath;

    public InputStream get() {
        try {
            InputStream is = getClass().getResourceAsStream(pemFilePath);
            return is;
        } catch (Exception var2) {
            throw new IllegalArgumentException("Could not find private key: " + this.pemFilePath, var2);
        }
    }

    @ConstructorProperties({"pemFilePath"})
    public ClasspathKeySupplier(String pemFilePath) {
        this.pemFilePath = pemFilePath;
    }

    public String toString() {
        return "SimplePrivateKeySupplier(pemFilePath=" + this.pemFilePath + ")";
    }
}

