package com.dbserver.lunchtime.util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * This class implements the connection witht the imgur api web service
 *
 * @author Thúlio Araújo (thuliolins@gmail.com)
 * @since 2/6/2017
 */
public class ClientConnection {

    private static final String BASE_URL = "https://stark-temple-49959.herokuapp.com/";
    static String requestUrl;
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getAccess(String URL, RequestParams params, AsyncHttpResponseHandler handler){
        requestUrl = BASE_URL + URL;
        client.get(requestUrl, params, handler);
        Logger.info(requestUrl);
    }

    public static void postAccess(String URL, RequestParams params, AsyncHttpResponseHandler handler){
        requestUrl = BASE_URL + URL;
        client.post(requestUrl, params, handler);
        Logger.info(requestUrl);
    }
}
