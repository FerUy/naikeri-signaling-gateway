package com.naikeri.sgw.helpers;

import java.io.File;

/**
 * Where jSS7's SCTP, M3UA, SCCP and TCAP stacks keep the state they persist: the directory named by
 * -Dsgw.state.dir, or the working directory when it isn't set, as before. An application that wants every
 * start to follow its XML configuration alone empties this directory before starting.
 */
public final class StateDirectory {

  public static final String PROPERTY = "sgw.state.dir";

  private StateDirectory() {
  }

  public static String get() {
    String dir = System.getProperty(PROPERTY, System.getProperty("user.dir"));
    new File(dir).mkdirs();
    return dir;
  }
}
