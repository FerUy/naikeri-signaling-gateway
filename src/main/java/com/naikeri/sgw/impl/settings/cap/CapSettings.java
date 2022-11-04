package com.naikeri.sgw.impl.settings.cap;

import com.naikeri.sgw.api.network.LayerType;
import com.naikeri.sgw.api.settings.LayerSettingsInterface;

public class CapSettings implements LayerSettingsInterface {

    private String name;
    private String tcap;
    private boolean enabled;

    public String getName() {
        return name;
    }

    public CapSettings(String name, boolean isEnabled) {
        this.name = name;
        this.enabled = isEnabled;
    }

    public CapSettings(String name, String tcap, boolean isEnabled) {
        this.name = name;
        this.tcap = tcap;
        this.enabled = isEnabled;
    }

    @Override
    public String getTransportName() {
        return tcap;
    }

    public boolean isEnabled(){
        return this.enabled;
    }

    @Override
    public LayerType getType() {
        return LayerType.Cap;
    }

    public String getTcapName() {
        return this.tcap;
    }

    @Override
    public String toString() {
        return String.format("[<%s>]: tcap = %s", name, tcap);
    }
}
