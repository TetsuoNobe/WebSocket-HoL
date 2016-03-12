/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.oracle.ejbs;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.websocket.Session;

/**
 *
 * @author tnobe
 */
@Singleton
@LocalBean
@Startup
public class ClientManageSinglEJB {

    private static final Logger logger
            = Logger.getLogger(
                    ClientManageSinglEJB.class.getPackage().getName());

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public ClientManageSinglEJB() {
    }
    private final Set<Session> peers
            = Collections.synchronizedSet(new HashSet<Session>());

    public void addClient(Session session) {
        peers.add(session);
    }

    public void removeClient(Session session) {
        peers.remove(session);
    }

    public void sendMessage(String message) {
        for (Session session : peers) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException ex) {
                logger.log(Level.SEVERE,
                        "Failed to send the message to Client :", ex);
            }
        }
    }
}
