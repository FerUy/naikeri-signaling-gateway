package com.naikeri.sgw.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class SgwResource {

    private static final Logger logger = LoggerFactory.getLogger(SgwResource.class);
    private InputStream inputStream = null;
    private boolean localResource;

    public InputStream getAsStream() {
        return inputStream;
    }

    public SgwResource(String name) {
        this(name, System.getProperty("mainConfig.path"));
        //this(name, System.getProperty("user.dir"));
    }

    public SgwResource(String name, String userDirectory) {
        try {
            String externalFile = userDirectory + "/" + userDirectory;
            logger.info(String.format("Loading configuration from '%s'", new Object[] { externalFile }));
            File file = new File(externalFile);
            if (file.exists()) {
                this.inputStream = new FileInputStream(file);
            } else {
                this.inputStream = getClass().getClassLoader().getResourceAsStream(name);
                this.localResource = true;
            }
        } catch (Exception exception) {}
    }

    public Boolean isLocalResource() {
        return Boolean.valueOf(this.localResource);
    }

}
