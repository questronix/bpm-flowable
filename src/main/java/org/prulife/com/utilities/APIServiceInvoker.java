package org.prulife.com.utilities;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by jonathanc on 7/30/2018.
 */
@Component
public class APIServiceInvoker implements HttpUtility {

    private final Logger LOGGER = LoggerFactory.getLogger(APIServiceInvoker.class);

    Unirest unirest = null;
    @Override
    public HttpResponse<String> HttpPost(String url, Map<String, Object> headers, String body) {

        LOGGER.info(url);
        Unirest unirest  = getConfiguredUnirest(headers);
        try {
            HttpResponse<String>  response = unirest.post(url).body(body).asString();
            return response;
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

//    @Override
//    public HttpResponse<ResponseModel> HttpGet(String url, Map<String, Object> headers, String body) {
//        return null;
//    }
//
    private Unirest getConfiguredUnirest(Map<String, Object> headers){

        unirest = new Unirest();
        try {
            if(headers != null)
                for (Map.Entry<String, Object> entry : headers.entrySet()){  unirest.setDefaultHeader(entry.getKey()  , entry.getValue().toString());}
            HttpClient httpClient = getConfiguredHttpClient();
            unirest.setHttpClient(httpClient);
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return unirest;

    }

    private HttpClient getConfiguredHttpClient(){

        HttpClient client = null;
        try {
            client = HttpClients.custom()
                    .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, (x509Certificates, s) -> true).build())
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .disableCookieManagement()
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return  client;

    }


}
