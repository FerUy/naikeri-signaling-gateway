package com.naikeri.sgw.impl.settings.diameter;

import com.naikeri.sgw.api.settings.LayerSettingsInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class XmlDiameterConfiguration {

    DiameterSettings diameter;
    private static final Logger logger = LoggerFactory.getLogger(XmlDiameterConfiguration.class);

    public LayerSettingsInterface getSettings() {
        return diameter;
    }

    public XmlDiameterConfiguration(Element element) {
        Boolean isEnabled = true; // default value

        String name = element.getAttribute("name");
        if (name == null || name.isEmpty()){
            logger.warn("The MAP name not found");
        }

        Boolean server = Boolean.parseBoolean(element.getAttribute("server"));
        if (server == null){
            logger.warn("The TCAP name is not found");
        }

        String config = element.getAttribute("config");
        if (config == null || config.isEmpty()) {
            logger.warn("The TCAP name is not found");
        }

        String dictionary = element.getAttribute("dictionary");
        if (dictionary == null || dictionary.isEmpty()) {
            logger.warn("The TCAP name is not found");
        }

        if (element.hasAttribute("enabled")) {
            isEnabled = element.getAttribute("enable").equalsIgnoreCase("true");
        }

        diameter = new DiameterSettings(name, server, config, dictionary, isEnabled);
    }
}
