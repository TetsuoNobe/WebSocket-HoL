/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.oracle.ejbs;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author tnobe
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "${com.sun.aas.instanceName}"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/inforegtopic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "jms/inforegtopic"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")})

public class MessageListenerMDBImpl implements MessageListener {

    private static final Logger logger
            = Logger.getLogger(
                    MessageListenerMDBImpl.class.getPackage().getName());

    public MessageListenerMDBImpl() {
    }

     //@EJB
     @Inject        
     ClientManageSinglEJB clManager;
     
    @Override
    public void onMessage(Message message) {
          TextMessage textMessage = (TextMessage) message;
          try {
             String text = textMessage.getText();
             //System.out.println(text);
            clManager.sendMessage(text);
          }
          catch (JMSException ex) {
              logger.log(Level.SEVERE, "onMessage() failed",ex);
          }
    }

}
