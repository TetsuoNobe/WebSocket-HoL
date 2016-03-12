/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.oracle.websockets;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;
import jp.co.oracle.ejbs.ClientManageSinglEJB;

/**
 *
 * @author tnobe
 */
@ServerEndpoint("/infotrans")
public class InfoTransServerEndpoint {
    
   //@EJB 
   @Inject
   ClientManageSinglEJB clManager;

    @OnOpen
    public void initOpen(Session session) {
        //System.out.println("接続");
        clManager.addClient(session);
    }

    @OnClose
    public void closeWebSocket(Session session) {
        //System.out.println("切断");
        clManager.removeClient(session);
    }

}
