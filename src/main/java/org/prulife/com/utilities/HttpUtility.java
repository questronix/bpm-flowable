package org.prulife.com.utilities;


import com.mashape.unirest.http.HttpResponse;

import java.util.Map;

public interface HttpUtility {

    HttpResponse<String> HttpPost(String url, Map<String, Object> headers, String body);

}
