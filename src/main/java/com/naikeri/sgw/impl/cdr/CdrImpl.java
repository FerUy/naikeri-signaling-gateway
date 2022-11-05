package com.naikeri.sgw.impl.cdr;

import com.naikeri.sgw.impl.settings.cdr.CdrSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CdrImpl {

  private final Logger logger = LoggerFactory.getLogger(CdrImpl.class);

  private List<CdrSettings> cdrSettings = null;

  private static CdrImpl instance = null;

  public static CdrImpl getInstance() {
    if (instance == null) {
      instance = new CdrImpl();
    }
    return instance;
  }

  public void setCdrSettings(List<CdrSettings> cdrSettings) {
    this.cdrSettings = cdrSettings;
  }

  public void write(Cdr cdr) {
    if (this.cdrSettings != null) {
      CdrSettings settings = null;
      for (CdrSettings lookUpSettings : this.cdrSettings) {
        if (lookUpSettings.getName().equalsIgnoreCase(cdr.getName())) {
          settings = lookUpSettings;
        }
      }

      if (settings != null) {
        Logger cdrLogger = LoggerFactory.getLogger(settings.getLogger());
        if (!settings.isDisplayName()) {
          cdrLogger.info(cdr.stringifyValues(settings.getSeparator(), settings.getFields()));
        } else {
          cdrLogger.info(cdr.stringifyAll(settings.getSeparator(), settings.getFields()));
        }
      } else {
        this.logger.error("Could not print CDR, CDR name '" + cdr.getName() + "' not found in any CDR Setting name. Not printed CDR: '" +
            cdr.stringifyAll(",", null) + "'");
      }
    } else {
      this.logger.error("Trying to use CDR Impl without loading up the configurations");
    }
  }
}
