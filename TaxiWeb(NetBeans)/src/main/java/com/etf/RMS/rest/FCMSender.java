/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etf.RMS.rest;

import org.riversun.fcm.FcmClient;
import org.riversun.fcm.model.EntityMessage;
import org.riversun.fcm.model.FcmResponse;

/**
 *
 * @author HP EliteBook 840 G1
 */
public class FCMSender {

    private String vrednost;
    private String kljuc;
    private String token;

    public FCMSender() {
    }

    public FCMSender(String vrednost, String token) {
        this.vrednost = vrednost;
        this.token = token;
       
    }

    // public static void main(String[] args) {
    public void slanjeNotifikacije(int voznjaId) {
        FcmClient client = new FcmClient();
        // You can get from firebase console.
        // "select your project>project settings>cloud messaging"
        client.setAPIKey("YOUR_API_KEY");

        // Data model for sending messages to specific entity(mobile devices,browser front-end apps)s
        EntityMessage msg = new EntityMessage();

        // Set registration token that can be retrieved
        // from Android entity(mobile devices,browser front-end apps) when calling
        // FirebaseInstanceId.getInstance().getToken();
        msg.addRegistrationToken(token);

        // Add key value pair into payload
        System.out.println("Usao je u main");
        msg.putStringData("myKey1", vrednost);

        if (voznjaId != 0) {
            msg.putStringData("myKey2", Integer.toString(voznjaId));
            System.out.println(Integer.toString(voznjaId));
        }

        //	msg.putStringData("myKey2", "myValue2");
        // push
        FcmResponse res = client.pushToEntities(msg);
        System.out.println("Ulazi u main");

        System.out.println(res);
    }

    //}
}
