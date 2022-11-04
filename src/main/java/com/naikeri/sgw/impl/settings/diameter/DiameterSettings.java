package com.naikeri.sgw.impl.settings.diameter;

import com.naikeri.sgw.api.network.LayerType;
import com.naikeri.sgw.api.settings.LayerSettingsInterface;

public class DiameterSettings implements LayerSettingsInterface {

    private String name;
    private Boolean server;
    private String config;
    private String dictionary;
    private Boolean enabled;

    @Override
    public String getTransportName() {
        return null;
    }

    @Override
    public LayerType getType() {
        return LayerType.Diameter;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Boolean isServer() {
        return server;
    }

    public String getConfig() {
        return config;
    }

    public String getDictionary() {
        return dictionary;
    }

    public DiameterSettings(String name, Boolean server, String config, String dictionary, Boolean isEnabled) {
        this.name = name;
        this.server = server;
        this.config = config;
        this.dictionary = dictionary;
        this.enabled = isEnabled;
    }
}
