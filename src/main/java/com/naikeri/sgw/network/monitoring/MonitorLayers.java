package com.naikeri.sgw.network.monitoring;

import java.io.FileNotFoundException;
import java.util.Timer;

import com.naikeri.sgw.impl.settings.MonitoringSettings;
import com.naikeri.sgw.network.layers.M3uaLayer;
import com.naikeri.sgw.network.layers.SctpLayer;
import org.restcomm.protocols.ss7.m3ua.M3UACounterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.restcomm.protocols.ss7.m3ua.As;
import org.restcomm.protocols.ss7.m3ua.Asp;
import org.restcomm.protocols.ss7.m3ua.AspFactory;
import org.restcomm.protocols.ss7.m3ua.M3UAManagementEventListener;
import org.restcomm.protocols.ss7.m3ua.State;
import org.restcomm.protocols.ss7.m3ua.impl.M3UAManagementImpl;
import org.restcomm.protocols.ss7.mtp.Mtp3EndCongestionPrimitive;
import org.restcomm.protocols.ss7.mtp.Mtp3PausePrimitive;
import org.restcomm.protocols.ss7.mtp.Mtp3ResumePrimitive;
import org.restcomm.protocols.ss7.mtp.Mtp3StatusPrimitive;
import org.restcomm.protocols.ss7.mtp.Mtp3TransferPrimitive;
import org.restcomm.protocols.ss7.mtp.Mtp3UserPartListener;
import org.mobicents.protocols.api.Association;
import org.mobicents.protocols.api.Management;
import org.mobicents.protocols.api.ManagementEventListener;
import org.mobicents.protocols.api.Server;


public class MonitorLayers implements M3UAManagementEventListener, Mtp3UserPartListener, ManagementEventListener {

  private M3UAManagementImpl m3uaMgmt;

  private Management sctpMgmt;

  public String endpoint;

  public String job;

  public String instance;

  public Logger logger = LoggerFactory.getLogger(MonitorLayers.class);

  public long m3uaIn = 0L;

  public long m3uaOut = 0L;

  public long mtp3Bytes = 0L;

  public boolean custom = false;

  private MonitoringSettings monitoringObj;

  private Timer monitoringTimer;

  public MonitorLayers(M3uaLayer m3uaLayer, SctpLayer sctpLayer, MonitoringSettings monitoringLayerObject) throws FileNotFoundException {
    this.m3uaMgmt = m3uaLayer.getM3uaManagement();
    this.sctpMgmt = sctpLayer.getSctpManagement();
    this.monitoringObj = monitoringLayerObject;
    this.m3uaMgmt.addM3UAManagementEventListener(this);
    this.m3uaMgmt.addMtp3UserPartListener(this);
    this.sctpMgmt.addManagementEventListener(this);
  }

  public void start() throws IllegalStateException {
    this.logger.info(String.format("starting Monitoring Layer for '%s' ...", new Object[] { this.monitoringObj.getM3uaName() }));
    try {
      this.m3uaMgmt.setStatisticsEnabled(true);
    } catch (Exception e) {
      this.logger.error("Cannot enable stats! " + e.getMessage());
    }
    this.monitoringTimer = new Timer();
    MonitoringTask task = new MonitoringTask(this, this.m3uaMgmt.getCounterProviderImpl(), this.m3uaMgmt, this.sctpMgmt, this.monitoringObj.getFileName());
    this.monitoringTimer.scheduleAtFixedRate(task, this.monitoringObj.getRefreshInterval().intValue(), this.monitoringObj.getRefreshInterval().intValue());
  }

  public void stop() {
    this.monitoringTimer.cancel();
  }

  public void onServerAdded(Server server) {}

  public void onServerRemoved(Server server) {}

  public void onAssociationAdded(Association association) {}

  public void onAssociationRemoved(Association association) {}

  public void onAssociationStarted(Association association) {}

  public void onAssociationStopped(Association association) {}

  public void onAssociationUp(Association association) {}

  public void onAssociationDown(Association association) {}

  public void onServerModified(Server server) {}

  public void onAssociationModified(Association association) {}

  public void onServiceStarted() {}

  public void onServiceStopped() {}

  public void onRemoveAllResources() {}

  public void onAsCreated(As as) {}

  public void onAsDestroyed(As as) {}

  public void onAspFactoryCreated(AspFactory aspFactory) {}

  public void onAspFactoryDestroyed(AspFactory aspFactory) {}

  public void onAspAssignedToAs(As as, Asp asp) {}

  public void onAspUnassignedFromAs(As as, Asp asp) {}

  public void onAspFactoryStarted(AspFactory aspFactory) {}

  public void onAspFactoryStopped(AspFactory aspFactory) {}

  public void onAspActive(Asp asp, State state) {}

  public void onAspInactive(Asp asp, State state) {}

  public void onAspDown(Asp asp, State state) {}

  public void onAsActive(As as, State state) {}

  public void onAsPending(As as, State state) {}

  public void onAsInactive(As as, State state) {}

  public void onAsDown(As as, State state) {}

  public void onMtp3TransferMessage(Mtp3TransferPrimitive mtp3TransferPrimitive) {}

  public void onMtp3PauseMessage(Mtp3PausePrimitive mtp3PausePrimitive) {}

  public void onMtp3ResumeMessage(Mtp3ResumePrimitive mtp3ResumePrimitive) {}

  public void onMtp3StatusMessage(Mtp3StatusPrimitive mtp3StatusPrimitive) {}

  public void onMtp3EndCongestionMessage(Mtp3EndCongestionPrimitive mtp3EndCongestionPrimitive) {}
}
