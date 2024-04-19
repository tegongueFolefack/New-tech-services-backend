//package com.example.securingweb.Payement;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.nio.charset.StandardCharsets;
//
////import com.dile.company.restControllers.dohone.PayInIntegration.getFullHttpResponse;
//
///**
// * @author sinclairjaza
// * @Date 7/28/21 - 9:40 AM
// * @Project dile_rest_api
// */
//
//@Service
//public class PayOutIntegration {
//
//	    private final PayInIntegration payInIntegration;
//
//	    //@Value("${dohone.api.account.number}")
//	    private final String MY_DOHONE_ACCOUNT_TEL_NUMBER;
//
//	    //@Value("${dohone.api.payout.base.url}")
//	    private final String DOHONE_PAYOUT_BASE_URL;
//
//	    //@Value("${dohone.api.merchand.key}")
//	    private final String DOHONE_MERCHAND_KEY;
//	    //@Value("${dohone.api.hashcode}")
//	    public final String DOHONE_HASH_CODE;
//	    //@Value("${my.app.name}")
//	    private final String MY_APP_NAME;
//	    // @Value("${my.app.notify.url}")
//	    private final String MY_APP_NOTIFY_URL;
//
//	    //@Value("${dohone.api.command.cotation}")
//	    public final String DOHONE_COTATION_COMMAND;
//	    //@Value("${dohone.api.command.start}")
//	    private final String DOHONE_START_COMMAND;
//	    // @Value("${dohone.api.command.cfrmsms}")
//	    private final String DOHONE_CFRMSMS_COMMAND;
//	    //@Value("${dohone.api.command.verify}")
//	    private final String DOHONE_VERIFY_COMMAND;
//
//	    //@Value("${dohone.api.payin.base.url}")
//	    private final String DOHONE_PAYIN_BASE_URL;
//
//	    public PayOutIntegration(@Value("${dohone.api.payin.base.url}")String baseUrl,
//	                            @Value("${dohone.api.command.verify}") String verifyCmd,
//	                            @Value("${dohone.api.command.cfrmsms}")String cfrmsmsCmd,
//	                            @Value("${dohone.api.command.start}")String startCmd,
//	                            @Value("${dohone.api.command.cotation}")String cotationCmd,
//	                            @Value("${my.app.notify.url}")String notifyUrl,
//	                            @Value("${my.app.name}")String appName,
//	                            @Value("${dohone.api.hashcode}")String hashCode,
//	                            @Value("${dohone.api.merchand.key}")String merchandKey,
//	                             @Value("${dohone.api.account.number}")String phoneNumber,
//	                             @Value("${dohone.api.payout.base.url}")String payOutBaseUrl,
//	                             PayInIntegration payInIntegration
//	    ) {
//	        this.DOHONE_PAYIN_BASE_URL = baseUrl;
//	        this.DOHONE_VERIFY_COMMAND = verifyCmd;
//	        this.DOHONE_CFRMSMS_COMMAND = cfrmsmsCmd;
//	        this.DOHONE_START_COMMAND = startCmd;
//	        this.DOHONE_COTATION_COMMAND = cotationCmd;
//	        this.MY_APP_NOTIFY_URL = notifyUrl;
//	        this.MY_APP_NAME = appName;
//	        this.DOHONE_HASH_CODE = hashCode;
//	        this.DOHONE_MERCHAND_KEY = merchandKey;
//	        this.MY_DOHONE_ACCOUNT_TEL_NUMBER = phoneNumber;
//	        this.DOHONE_PAYOUT_BASE_URL = payOutBaseUrl;
//	        this.payInIntegration = payInIntegration;
//	    }
//
//	    //    Apache HttpClient
//	    private CloseableHttpClient httpClient = HttpClients.createDefault();
//
//	    /*
//	         api de transferts automatiques via DOHONE, à partir de votre système. Ce module vous permettra de lancer ou rediriger des transferts d’argent de votre
//	         compte DOHONE, vers des destinataires, selon le mode de transfert que vous choisirez.
//	     */
//
//	    //    With HTTP CLIENT
//	    public String transfert(String destination, String mode, String amount, String devise, String nameDest, String ville,
//	                                   String pays, String transID) throws Exception {
//
//	        // EXEMPLE: https://www.my-dohone.com/dohone/transfert?account=237696000001&destination=237675444442&mode=6&amount=2500&devise=XAF&nameDest=carim&ville=yaound%C3%A9&pays=Cameroun&hash=654dfg5df54gfgdg16d5fdf5g1df3g11cs164s56c215s1d1s515azea5dz
//
//	        /*
//	            account: Le numéro de téléphone de votre compte DOHONE. bien vouloir présicer l'indicatif du pay: 237699009900
//	            destination: le numéro de téléphone du destinataire s'il s'agit d'un transfert par OM, MOMO, EU, Virement Dohone
//	            amount: Montant total à transferer. Ce montant par défaut est en FCFA
//	            mode: le mode de transfert que le client choisira d’effectuer. La valeur est numérique. [5] = MTN-Money, [6] = Orange-Money, [3] = Express-Union mobile, [1] = virement DOHONE.
//	            devise: Il s’agit de la devise sous laquelle est donné le montant de votre transfert. La devise par défaut est XAF. les autres devices EUR, USD
//	            nameDest: nom complet du destinataire de votre transfert
//	            ville: ville du destinataire de votre transfert
//	            pays:  pays du destinataire de votre transfert
//	            hash: Il s’agit du chiffrement en MD5  ( account + mode + amount + devise + transID + code de hashage )
//	            notifyUrl: L’adresse de notification du statu de la transaction
//	            transID: a référence de cette transaction dans votre système
//	            rUserId: ’identifiant du compte de votre client dans votre système. Ce paramètre est facultatif.
//	        */
//
//	        String stringToHash= MY_DOHONE_ACCOUNT_TEL_NUMBER + mode + amount + devise + transID + DOHONE_HASH_CODE;
//
//	        byte[] stringToHashInBytes = MD5HashBuilder.digest(stringToHash.getBytes(StandardCharsets.UTF_8));
//
//	        String myFinalMd5HashCode = MD5HashBuilder.bytesToHex(stringToHashInBytes);
//
//	        String url = DOHONE_PAYOUT_BASE_URL+
//	                "?account="+MY_DOHONE_ACCOUNT_TEL_NUMBER+
//	                "&destination="+destination+
//	                "&mode="+mode+
//	                "&amount="+amount+
//	                "&devise="+devise+
//	                "&nameDest="+nameDest+
//	                "&ville="+ville+
//	                "&pays="+pays+
//	                "&hash="+myFinalMd5HashCode+
//	                "&transID="+transID+
//	                "&rUserId="+destination;
//
//	        System.out.println("L'url de la requete de transfert " + url);
//
//	        return getFullHttpResponse(httpClient, url);
//	    }
//
//	    /*
//	         api de transferts automatiques via DOHONE, à partir de votre système. Ce module vous permettra de lancer ou rediriger des transferts d’argent de votre
//	         compte DOHONE, vers des destinataires, selon le mode de transfert que vous choisirez.
//	     */
//
//	    //    With HTTP CLIENT
//	    public String cotationTransfert(String devise, String amount, String mode) throws Exception {
//
//	        // EXEMPLE: https://www.my-dohone.com/dohone/transfert?account=237696000001&destination=237675444442&mode=6&amount=2500&devise=XAF&nameDest=carim&ville=yaound%C3%A9&pays=Cameroun&hash=654dfg5df54gfgdg16d5fdf5g1df3g11cs164s56c215s1d1s515azea5dz
//
//	        /*
//	            account: Le numéro de téléphone de votre compte DOHONE. bien vouloir préciser l'indicatif du pay: 237699009900
//	            amount: Montant TOTAL net à transférer
//	            mode: le mode de transfert que le client choisira d’effectuer. La valeur est numérique. [5] = MTN-Money, [6] = Orange-Money, [3] = Express-Union mobile, [1] = virement DOHONE.
//	            devise: Il s’agit de la devise sous laquelle est donné le montant de votre transfert.
//	        */
//
//	        String url = DOHONE_PAYOUT_BASE_URL+
//	                "?cmd="+DOHONE_COTATION_COMMAND+
//	                "&devise="+devise+
//	                "&amount="+amount+
//	                "&mode="+mode+
//	                "&account="+MY_DOHONE_ACCOUNT_TEL_NUMBER;
//
//	        return getFullHttpResponse(httpClient, url);
//	    }
//
//	    public String getFullHttpResponse(CloseableHttpClient httpClient, String url) throws Exception{
//
//	        HttpGet request = new HttpGet(url);
//
//	        try (CloseableHttpResponse response = httpClient.execute(request)){
//	            // Get HttpResponse Status
//	            System.out.println("HttpResponse Status " + response.getStatusLine().toString());
//
//	            HttpEntity entity = response.getEntity();
//
//	            String result = null;
//
//	            if (entity != null) {
//	                // return it as a String
//	                result = EntityUtils.toString(entity);
//	                System.out.println("La reponse finale: " + result);
//	            }
//
//	            return result;
//	        }
//	    }
//	}
//
//
//}
