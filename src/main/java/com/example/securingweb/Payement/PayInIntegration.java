//package com.example.securingweb.Payement;
//
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import org.apache.http.Header;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author sinclairjaza
// * @Date 7/28/21 - 9:39 AM
// * @Project dile_rest_api
// */
//
//@Service
//public class PayInIntegration {
//
//    //@Value("${dohone.api.merchand.key}")
//    private final String DOHONE_MERCHAND_KEY;
//    //@Value("${dohone.api.hashcode}")
//    public final String DOHONE_HASH_CODE;
//    //@Value("${my.app.name}")
//    private final String MY_APP_NAME;
//   // @Value("${my.app.notify.url}")
//    private final String MY_APP_NOTIFY_URL;
//
//    //@Value("${dohone.api.command.cotation}")
//    public final String DOHONE_COTATION_COMMAND;
//    //@Value("${dohone.api.command.start}")
//    private final String DOHONE_START_COMMAND;
//   // @Value("${dohone.api.command.cfrmsms}")
//    private final String DOHONE_CFRMSMS_COMMAND;
//    //@Value("${dohone.api.command.verify}")
//    private final String DOHONE_VERIFY_COMMAND;
//
//    //@Value("${dohone.api.payin.base.url}")
//    private final String DOHONE_PAYIN_BASE_URL;
//
//    public PayInIntegration(@Value("${dohone.api.payin.base.url}")String baseUrl,
//                            @Value("${dohone.api.command.verify}") String verifyCmd,
//                            @Value("${dohone.api.command.cfrmsms}")String cfrmsmsCmd,
//                            @Value("${dohone.api.command.start}")String startCmd,
//                            @Value("${dohone.api.command.cotation}")String cotationCmd,
//                            @Value("${my.app.notify.url}")String notifyUrl,
//                            @Value("${my.app.name}")String appName,
//                            @Value("${dohone.api.hashcode}")String hashCode,
//                            @Value("${dohone.api.merchand.key}")String merchandKey
//                            ) {
//        this.DOHONE_PAYIN_BASE_URL = baseUrl;
//        this.DOHONE_VERIFY_COMMAND = verifyCmd;
//        this.DOHONE_CFRMSMS_COMMAND = cfrmsmsCmd;
//        this.DOHONE_START_COMMAND = startCmd;
//        this.DOHONE_COTATION_COMMAND = cotationCmd;
//        this.MY_APP_NOTIFY_URL = notifyUrl;
//        this.MY_APP_NAME = appName;
//        this.DOHONE_HASH_CODE = hashCode;
//        this.DOHONE_MERCHAND_KEY = merchandKey;
//    }
//
//    //    Apache HttpClient
//    private CloseableHttpClient httpClient = HttpClients.createDefault();
//
//    private OkHttpClient oKHttpClient = new OkHttpClient.Builder()
//            .connectTimeout(60, TimeUnit.SECONDS)
//            .writeTimeout(60, TimeUnit.SECONDS)
//            .readTimeout(60, TimeUnit.SECONDS)
//            .build();
//
//    /*
//        Cette commande vous permet d’interroger DOHONE, pour savoir quel est le montant total que vous
//        devez informer à votre client qu’il s’apprête à dépenser, afin qu’il puisse payer votre facture + les frais.
//           littéralement, cette commande signifie : « Combien au total coûtera t’il
//           à mon client, pour qu’il me paye XXX FCFA via XXX (Moyen de paiement OM, MOMO, EU, ...) sur DOHONE ? ».
//     */
//
////    With HttpURLConnection
//    public String cotation(String cmd, String rDvs, String rMt, String rMo, String rH, String levelFeeds) throws Exception {
//
//    // EXEMPLE: https://www.my-dohone.com/dohone/pay?cmd=cotation&rDvs=XAF&rMt=3500&rMo=1&levelFeeds=0
//
//        /*
//        cmd: Le nom de la commande. Valeur : cotation
//        rDvs: La devise correspondante au montant. Vous avez le choix entre 3 devises uniquement : EUR, XAF, USD. Par defaut c'est en EUR
//        rMt: Montant TOTAL net des achats
//        rMo: le type de paiement que le client choisira d’effectuer. La valeur est numérique. [1] = MTN-Money, [2] = Orange-Money, [3] = Express-Union mobile, [10] = virement DOHONE.
//        rH: Hashcode de votre compte Marchand. Le hashcode qui vous a été transmis par mail. Ce paramètre est facultatif
//        levelFeeds: Le niveau de d’information de cotation que vous souhaitez avoir en retour (valeur numérique) :
//                Par exemple: 0 = Montant achat + Frais opérateur + frais DOHONE d’achat en ligne du client
//                             1 = Montant achat + Frais d’opérateur
//         */
//
//        String myUrl = DOHONE_PAYIN_BASE_URL+
//                "?cmd="+cmd+
//                "&rDvs="+rDvs+
//                "&rMt="+rMt+
//                "&rMo="+rMo+
//                "&rH="+DOHONE_HASH_CODE+
//                "&levelFeeds="+levelFeeds;
//
//        URL url = new URL(myUrl);
//
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//        con.setRequestMethod("GET");
//
//        int responseCode = con.getResponseCode();
//        System.out.println("Response Code : " + responseCode);
//
//        try (BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()))) {
//
//            StringBuilder response = new StringBuilder();
//            String line;
//
//            while ((line = in.readLine()) != null) {
//                response.append(line);
//            }
//
//            //print result
//            System.out.println("La reponse finale !!! " + response.toString());
//
//            return  response.toString();
//        }
//    }
//
////    With HTTP CLIENT
//    public String cotation1(String rDvs, String rMt, String rMo, String levelFeeds) throws Exception {
//
//    // EXEMPLE: https://www.my-dohone.com/dohone/pay?cmd=cotation&rDvs=XAF&rMt=3500&rMo=1&levelFeeds=0
//
//        /*
//        cmd: Le nom de la commande. Valeur : cotation
//        rDvs: La devise correspondante au montant. Vous avez le choix entre 3 devises uniquement : EUR, XAF, USD. Par defaut c'est en EUR
//        rMt: Montant TOTAL net des achats
//        rMo: le type de paiement que le client choisira d’effectuer. La valeur est numérique. [1] = MTN-Money, [2] = Orange-Money, [3] = Express-Union mobile, [10] = virement DOHONE.
//        rH: Hashcode de votre compte Marchand. Le hashcode qui vous a été transmis par mail. Ce paramètre est facultatif
//        levelFeeds: Le niveau de d’information de cotation que vous souhaitez avoir en retour (valeur numérique) :
//                Par exemple: 0 = Montant achat + Frais opérateur + frais DOHONE d’achat en ligne du client
//                             1 = Montant achat + Frais d’opérateur
//         */
//
//        String url = DOHONE_PAYIN_BASE_URL+
//                "?cmd="+DOHONE_COTATION_COMMAND+
//                "&rDvs="+rDvs+
//                "&rMt="+rMt+
//                "&rMo="+rMo+
//                "&rH="+DOHONE_HASH_CODE+
//                "&levelFeeds="+levelFeeds;
//
//        return getFullHttpResponse(httpClient, url);
//    }
//
////  With OKHTTP
//    public String cotation2(String cmd, String rDvs, String rMt, String rMo, String rH, String levelFeeds) throws Exception {
//
//    // EXEMPLE: https://www.my-dohone.com/dohone/pay?cmd=cotation&rDvs=XAF&rMt=3500&rMo=1&levelFeeds=0
//
//        /*
//        cmd: Le nom de la commande. Valeur : cotation
//        rDvs: La devise correspondante au montant. Vous avez le choix entre 3 devises uniquement : EUR, XAF, USD. Par defaut c'est en EUR
//        rMt: Montant TOTAL net des achats
//        rMo: le type de paiement que le client choisira d’effectuer. La valeur est numérique. [1] = MTN-Money, [2] = Orange-Money, [3] = Express-Union mobile, [10] = virement DOHONE.
//        rH: Hashcode de votre compte Marchand. Le hashcode qui vous a été transmis par mail. Ce paramètre est facultatif
//        levelFeeds: Le niveau de d’information de cotation que vous souhaitez avoir en retour (valeur numérique) :
//                Par exemple: 0 = Montant achat + Frais opérateur + frais DOHONE d’achat en ligne du client
//                             1 = Montant achat + Frais d’opérateur
//         */
//
//        String url = DOHONE_PAYIN_BASE_URL+
//                "?cmd="+cmd+
//                "&rDvs="+rDvs+
//                "&rMt="+rMt+
//                "&rMo="+rMo+
//                "&rH="+DOHONE_HASH_CODE+
//                "&levelFeeds="+levelFeeds;
////        String url = "https://www.google.com/search?q=sinclairjaza";
//
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        try (Response response = oKHttpClient.newCall(request).execute()){
//
//            if(!response.isSuccessful()) throw  new IOException("Unexpected code " + response);
//
//            System.out.println("La reponse finale !!! " + response.body().string());
//
//            return response.body().string(); //We have an timeout error like the response
//        }
//    }
//
//    /*
//        Lancer la commande START revient à lancer une requête http vers le serveur DOHONE,
//        contenant certains paramètres, via la méthode GET.
//     */
//    public String start(String rN, String rT, String rE, String rI, String rMo,
//                             String rOTP, String rMt, String rDvs, String motif) throws Exception{
//
//        // EXEMPLE Orange Money (particulièrement): https://www.my-dohone.com/dohone/pay?cmd=start&rN=Bruno&rDvs=XAF&rMt=3500&rMo=2&rT=67203000001&rH=PE458Z7521&rI=1001&source=Car+Dispo&rOTP=112233
//        // EXEMPLE: https://www.my-dohone.com/dohone/pay?cmd=start&rN=Bruno&rDvs=XAF&rMt=3500&rMo=2&rT=67203000001&rH=PE458Z7521&rI=1001&source=Car+Dispo
//
//        /*
//            cmd: Le nom de la commande. Valeur : start
//            rN: Le nom du client qui effectue la transaction (Facultatif)
//            rT: Numéro de téléphone du client (Obligatoire)
//            rE: Adresse email qui effectue le paiement (Facultatif)
//            rH: Votre code marchand
//            rI: Le numéro de la transaction dans notre système (Facultatif)
//            rMo: le type de paiement que le client choisira d’effectuer. La valeur est numérique. [1] = MTN-Money, [2] = Orange-Money, [3] = Express-Union mobile, [10] = virement DOHONE.
//            rOTP: Le code OTP nécessaire/obligatoire pour les paiements via Orange Money, le code s'obtient par le client au #150*4*4#
//            rMt: Montant net des achats (Obligatoire). C’est le montant qui devra être payé par votre client. c'est le montant sans charge
//            rDvs: La devise correspondante au montant. Vous avez le choix entre 3 devises uniquement : EUR, XAF, USD. Par defaut c'est en EUR
//            source: Le nom commercial de votre application (Obligatoire)
//            notifyPage ou endPage: Adresse de notification automatique en cas de SUCCESS de paiement.
//            motif: motif du paiement. Cette information sera renvoyée vers « endPage » en cas de succès de paiement
//         */
//
//        try {
//            String url = DOHONE_PAYIN_BASE_URL+
//                    "?cmd="+DOHONE_START_COMMAND+
//                    "&rN="+URLEncoder.encode(rN,"UTF-8")+
//                    "&rDvs="+rDvs+
//                    "&rMt="+rMt+
//                    "&rMo="+rMo+
//                    "&rT="+rT+
//                    "&rH="+DOHONE_MERCHAND_KEY+
//                    "&rI="+rI+
//                    "&rOTP="+rOTP+
//                    "&rE="+rE+
//                    "&source="+MY_APP_NAME+
//                    "&motif="+URLEncoder.encode(motif,"UTF-8")+
//                    "&notifyPage="+MY_APP_NOTIFY_URL;
//
//            System.out.println("MY DOHONE URL " + url);
//            System.out.println("OM OPT " + rOTP);
//
//            String result = (rT.length() == 9 || rT.length() == 12)
//                    ? rMo.equals("2")  //si mode de paiement = Orange Money alors on demande le code OTP, sinon on exécute
//                        //? (rOTP != "" && rOTP.length() == 6)
//                        ? (!rOTP.equals("") && rOTP.length() == 6)
//                            ? getFullHttpResponse(httpClient, url)
//                            : "CODE OPT INCORRECT !"
//                        : getFullHttpResponse(httpClient, url)
//                    : "Le numero de téléphone est incorrect";
//
//            return result;
//        }
//        catch (UnsupportedEncodingException e){
//            throw e;
//        }
//    }
//
//    /*
//        Cette commande est utilisée s’il s’agit par exemple d’un paiement via VIREMENT DOHONE, ou par mesure de
//        sécurité. elle se fait via une méthode GET
//     */
//    public String cfrmsms(String rCS, String rT) throws Exception{
//
//        // EXEMPLE: https://www.my-dohone.com/dohone/pay?cmd=cfrmsms&rCS=4325&rT=673000001
//
//        /*
//            cmd: Le nom de la commande. Valeur : cfrmsms
//            rCS: Le code que votre client a reçu par SMS
//            rT: Numéro de téléphone du client qui a servi au paiement mobile money
//        */
//
//        String url = DOHONE_PAYIN_BASE_URL+
//                "?cmd="+DOHONE_CFRMSMS_COMMAND+
//                "&rCS="+rCS+
//                "&rT="+rT;
//
//        System.out.println("MY DOHONE cfrmsms URL " + url);
//
//        return (rT.length() == 9 || rT.length() == 12)
//                ? getFullHttpResponse(httpClient, url)
//                : "Impossible d'effectuer la requête";
//    }
//
//    /*
//        vérifier l’effectivité du succès de l’opération de paiement, avant de rendre le service à votre client.
//        Littéralement, cette commande demande à DOHONE :
//            « reconnaissez-vous une transaction dont la référence de transaction sur dohone est XXX, dont
//            le montant est XXX, et dont le numero de ma farure est XXX »
//     */
//    public String verify(String cmd, String rI, String rMt, String idReqDoh, String rDvs) throws Exception{
//
//        // EXEMPLE: https://www.my-dohone.com/dohone/pay?cmd=verify&rI=1001&rMt=3500&idReqDoh=432130005
//
//        /*
//            cmd: Le nom de la commande. Valeur : verify
//            rI: Le numéro de la transaction dans notre système (Facultatif)
//            rMt: Montant net des achats (Obligatoire). C’est le montant qui a été payé par le client. ce montant est sans charge
//            idReqDoh: La référence de la transaction dohone. Vous avez reçu cette valeur dans le résultat de la commande « cfrmsms » ou « start ». Vous devez parser la réponse de « cfrmsms », pour isoler la référence de transaction.
//            rDvs: La devise correspondante au montant. Vous avez le choix entre 3 devises uniquement : EUR, XAF, USD. Par defaut c'est en EUR
//         */
//
//        String url = DOHONE_PAYIN_BASE_URL+
//                "?cmd="+DOHONE_VERIFY_COMMAND+
//                "&rI="+rI+
//                "&rMt="+rMt+
//                "&idReqDoh="+idReqDoh+
//                "&rDvs="+rDvs;
//
//        return getFullHttpResponse(httpClient, url);
//    }
//
//    public String getUrlParams(HttpServletRequest request, HttpServletRequest response){
//
//        String hash = request.getParameter("hash");
//        String idReqDoh = request.getParameter("idReqDoh");
//        String rMt = request.getParameter("rMt");
//        String rH = request.getParameter("rH");
//
//        return hash + " - " + idReqDoh + " - " + rMt + " - " + rH ;
//    }
//
//    public Object getDohoneResponseCode(String url) throws Exception {
//            try {
//                Map<String, List<String>> params = new HashMap<>();
//                String[] urlParts = url.split("\\?");
//                if (urlParts.length > 1) {
//                    String query = urlParts[1];
//                    for (String param : query.split("&")) {
//                        String[] pair = param.split("=");
//                        String key = URLDecoder.decode(pair[0], "UTF-8");
//                        String value = "";
//                        if (pair.length > 1) {
//                            value = URLDecoder.decode(pair[1], "UTF-8");
//                        }
//
//                        List<String> values = params.get(key);
//                        if (values == null) {
//                            values = new ArrayList<String>();
//                            params.put(key, values);
//                        }
//                        values.add(value);
//                    }
//                }
//
//                return params;
//            } catch (UnsupportedEncodingException ex) {
//                throw new AssertionError(ex);
//            }
//    }
//
//    public String getFullHttpResponse(CloseableHttpClient httpClient, String url) throws Exception{
//
//        HttpGet request = new HttpGet(url);
//
//        try (CloseableHttpResponse response = httpClient.execute(request)){
//            // Get HttpResponse Status
//            System.out.println("HttpResponse Status " + response.getStatusLine().toString());
//
//            HttpEntity entity = response.getEntity();
//
//            String result = null;
//
//            if (entity != null) {
//                // return it as a String
//                result = EntityUtils.toString(entity);
//                System.out.println("La reponse finale: " + result);
//            }
//
//            return result;
//        }
//    }
//}
