package com.naikeri.sgw.network.layers;

import com.naikeri.sgw.helpers.SgwResource;
import com.naikeri.sgw.impl.chn.ChannelHandler;
import com.naikeri.sgw.impl.settings.diameter.DiameterSettings;
import com.naikeri.sgw.api.chn.ChannelMessage;
import com.naikeri.sgw.api.network.LayerInterface;
import com.naikeri.sgw.api.settings.LayerSettingsInterface;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.jdiameter.api.Answer;
import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Avp;
import org.jdiameter.api.AvpDataException;
import org.jdiameter.api.Configuration;
import org.jdiameter.api.EventListener;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.LocalAction;
import org.jdiameter.api.Message;
import org.jdiameter.api.MutablePeerTable;
import org.jdiameter.api.Network;
import org.jdiameter.api.NetworkMsgListener;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Peer;
import org.jdiameter.api.Realm;
import org.jdiameter.api.Request;
import org.jdiameter.api.RouteException;
import org.jdiameter.api.Session;
import org.jdiameter.api.URI;
import org.jdiameter.client.api.IMessage;
import org.jdiameter.client.api.controller.IRealm;
import org.jdiameter.client.api.controller.IRealmTable;
import org.jdiameter.client.impl.controller.RealmImpl;
import org.jdiameter.client.impl.helpers.Parameters;
import org.jdiameter.server.impl.NetworkImpl;
import org.jdiameter.server.impl.StackImpl;
import org.jdiameter.server.impl.helpers.XMLConfiguration;
import org.mobicents.diameter.dictionary.AvpDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiameterLayer implements LayerInterface, NetworkMsgListener, NetworkReqListener, EventListener<Request, Answer> {
    private static final Logger logger = LoggerFactory.getLogger(DiameterLayer.class);

    private LayerSettingsInterface diameterSettings;

    private StackImpl diameterStack;

    private ChannelHandler channelHandler;

    public Answer processRequest(Request request) {
        logger.info(String.format("<< Diameter received request for sessionId [%s]", new Object[] { request.getSessionId() }));
        ChannelMessage channelMessage = new ChannelMessage("DMR");
        channelMessage.setParameter("REQUEST", request);
        this.channelHandler.receiveMessageRequest(channelMessage);
        return null;
    }

    public Message processMessage(Message message) {
        IMessage iMessage = (IMessage)message;
        if (iMessage.isRequest()) {
            String destRealm;
            IMessage iMessage1 = iMessage;
            Avp destRealmAvp = iMessage1.getAvps().getAvp(283);
            if (destRealmAvp == null)
                return message;
            try {
                destRealm = destRealmAvp.getDiameterIdentity();
            } catch (AvpDataException ade) {
                return message;
            }
            try {
                IRealmTable realmTable = ((NetworkImpl)this.diameterStack.unwrap(Network.class)).getRealmTable();
                if (!realmTable.realmExists(destRealm)) {
                    RealmImpl realmImpl = new RealmImpl(destRealm, iMessage.getSingleApplicationId(), LocalAction.LOCAL, null, null, true, 1L, new String[0]);
                    addRealms(new IRealm[] { (IRealm)realmImpl });
                    this.channelHandler.onReceiveUnknownRealm((IRealm)realmImpl);
                } else {
                    Collection<Realm> realms = realmTable.getRealms(destRealm);
                    if (!realms.stream().anyMatch(realm -> realm.getApplicationId().equals(iMessage.getSingleApplicationId()))) {
                        RealmImpl realmImpl = new RealmImpl(destRealm, iMessage.getSingleApplicationId(), LocalAction.LOCAL, null, null, true, 1L, new String[0]);
                        addRealms(new IRealm[] { (IRealm)realmImpl });
                        this.channelHandler.onReceiveUnknownRealm((IRealm)realmImpl);
                    }
                }
            } catch (InternalException e) {
                logger.error("Exception caught", (Throwable)e);
            }
        }
        return message;
    }

    public DiameterLayer(DiameterSettings diameterSettings) throws Exception {
        this.diameterSettings = (LayerSettingsInterface)diameterSettings;
        try {
            InputStream inputStreamDictionary = (new SgwResource(diameterSettings.getDictionary())).getAsStream();
            AvpDictionary.INSTANCE.parseDictionary(inputStreamDictionary);
            this.diameterStack = new StackImpl();
            InputStream inputStreamXmlConfiguration = (new SgwResource(diameterSettings.getConfig())).getAsStream();
            this.diameterStack.init((Configuration)new XMLConfiguration(inputStreamXmlConfiguration));
            Thread.sleep(500L);
            this.diameterStack.start();
            Network network = (Network)this.diameterStack.unwrap(Network.class);
            Set<ApplicationId> stackAppIds = this.diameterStack.getMetaData().getLocalPeer().getCommonApplications();
            for (ApplicationId appId : stackAppIds) {
                logger.info("Adding diameter support for " + appId);
                network.addNetworkReqListener(this, new ApplicationId[] { appId });
            }
        } catch (Exception e) {
            logger.error("Exception caught", e);
        }
    }

    public void setChannelHandler(ChannelHandler channelHandler) {
        this.channelHandler = channelHandler;
    }

    public String getName() {
        return this.diameterSettings.getName();
    }

    public void stop() {
        try {
            this.diameterStack.stop(0);
            logger.info("DiameterLayer has been stopped");
        } catch (Exception e) {
            logger.error("Exception when stopping DiameterLayer ('" + getName() + "'). " + e);
        }
    }

    public LayerSettingsInterface getSetting() {
        return this.diameterSettings;
    }

    public Session getSession(String... sessionId) {
        Session session = null;
        try {
            synchronized (this) {
                if (sessionId != null) {
                    logger.debug(String.format("getSession(%s)", new Object[] { sessionId[0] }));
                    session = (Session)this.diameterStack.getSession(sessionId[0], Session.class);
                }
                if (session == null)
                    session = this.diameterStack.getSessionFactory().getNewSession(sessionId[0]);
            }
        } catch (Exception e) {
            logger.error("Caught exception while creating a new raw diameter session!", e);
        }
        return session;
    }

    public void sendMessage(Session session, Message message, Boolean routeRecord) throws InternalException, RouteException, OverloadException, IllegalDiameterStateException {
        logger.info(String.format(">> Diameter sending message for sessionId [%s]", new Object[] { message.getSessionId() }));
        if (routeRecord != null && routeRecord.booleanValue() && message.isRequest())
            addRouteRecord(message);
        session.send(message, this);
    }

    public void receivedSuccessMessage(Request request, Answer answer) {
        logger.info(String.format("<< Diameter received answer for request sessionId [%s] and answer sessionId [%s]", new Object[] { request.getSessionId(), answer.getSessionId() }));
        ChannelMessage channelMessage = new ChannelMessage("DMA");
        channelMessage.setParameter("REQUEST", request);
        channelMessage.setParameter("ANSWER", answer);
        this.channelHandler.receiveMessageRequest(channelMessage);
    }

    public void timeoutExpired(Request request) {
        logger.warn(String.format("== Diameter timeout for sessionId [%s]", new Object[] { (request == null) ? "null" : request.getSessionId() }));
        try {
            ((Session)this.diameterStack.getSession(request.getSessionId(), Session.class)).release();
        } catch (Exception exception) {}
    }

    private void addRouteRecord(Message message) {
        String diameterUri = this.diameterStack.getConfiguration().getStringValue(Parameters.OwnDiameterURI.ordinal(), "");
        Avp record = message.getAvps().getAvp(282);
        if (record == null)
            message.getAvps().addAvp(282, diameterUri, true, false, true);
    }

    public void addRealms(IRealm... realms) throws InternalException {
        IRealmTable realmTable = ((NetworkImpl)this.diameterStack.unwrap(Network.class)).getRealmTable();
        for (IRealm realm : realms)
            realmTable.addRealm(realm.getName(), realm.getApplicationId(), realm
                .getLocalAction(), (String)null, realm.isDynamic(), realm
                .getExpirationTime(), realm.getPeerNames());
    }

    public List<Peer> getPeerList() {
        List<Peer> peerList = new ArrayList<>();
        try {
            peerList = ((MutablePeerTable)this.diameterStack.unwrap(MutablePeerTable.class)).getPeerTable();
        } catch (Exception ex) {
            logger.error("Error on get peer list -> " + ex.getMessage());
        }
        return peerList;
    }

    public void addPeer(HashMap<String, Object> paramsData) {
        try {
            URI uri = new URI(paramsData.get("peerURI").toString());
            String ip = paramsData.get("ip").toString().isEmpty() ? null : paramsData.get("ip").toString();
            Peer peer = ((MutablePeerTable)this.diameterStack.unwrap(MutablePeerTable.class)).addPeer(uri, paramsData.get("realm").toString(),
                Boolean.parseBoolean(paramsData.get("connecting").toString()), ip);
        } catch (Exception ex) {
            logger.error("Error on try to add a Peer -> " + ex.getMessage());
        }
    }

    public void stopPeer(HashMap<String, Object> paramsData) {
        try {
            Peer peer = ((MutablePeerTable)this.diameterStack.unwrap(MutablePeerTable.class)).removePeer(paramsData.get("peerName").toString(),
                Integer.parseInt(paramsData.get("disconnectCause").toString()), Boolean.parseBoolean(paramsData.get("connecting").toString()));
        } catch (Exception ex) {
            logger.error("Error on try to stop Peer -> " + ex.getMessage());
        }
    }
}

