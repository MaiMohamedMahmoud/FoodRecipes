package com.example.mac_os.foodrecipe;

import android.net.Uri;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class FatSecretSearchFood {
    final static private String APP_METHOD = "GET";
    final static private String APP_KEY = "e7c5644c6e59405eb10d3e5b215bb28d";
    final static private String APP_SECRET = "d510bf3fe2e14aa4b673105505ff4f0e";
    final static private String APP_URL = "http://platform.fatsecret.com/rest/server.api";
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private String resuOfsignature="";
    private static String timestampValue ="";
    private static String nonceValue="";
    JSONObject food;

//    public JSONObject searchFood(String searchFood, int page) throws UnsupportedEncodingException {
//        final List<String> params = new ArrayList<>(Arrays.asList(generateOauthParams(page)));
//        final String[] template = new String[1];
//        params.add("method=foods.search");
//        params.add("search_expression=" + Uri.encode(searchFood));
//        params.add("oauth_signature=" + sign(APP_METHOD, APP_URL, params.toArray(template), searchFood));
//
//        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (this) {
//                    BufferedReader reader = null;
//                    try {
//
//
//
//                        URL url = new URL(APP_URL + "?" + paramify(params.toArray(template)));
//                        Log.i("URL", url.toString());
//                        HttpURLConnection foodSearchConnection = (HttpURLConnection) new URL(url.toString()).openConnection();
//                        reader = new BufferedReader(new InputStreamReader(foodSearchConnection.getInputStream()));
//                        StringBuilder sb = new StringBuilder();
//                        String line = null;
//                        while ((line = reader.readLine()) != null) {
//                            sb.append(line + "\n");
//                        }
//                        String result = sb.toString();
//                        JSONObject jObject = new JSONObject(result);
//                        food = jObject.getJSONObject("foods");
//                        Log.i("Food search", food.toString());
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } finally {
//                        try {
//                            reader.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        });
//
//        thread.start();
//        while (thread.getState() == Thread.State.RUNNABLE) {
//            try {
//                thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//        Log.i(" YARAAAAB", thread.getState().toString());
//        Log.i(" YARAAAAB", thread.getState() + food.toString());
//        return food;
//    }

    public String searchFood(String searchFood, int page) throws UnsupportedEncodingException {
        final List<String> params = new ArrayList<>(Arrays.asList(generateOauthParams(page)));
        final String[] template = new String[1];
        params.add("method=foods.search");
        params.add("search_expression=" + Uri.encode(searchFood));
        params.add("oauth_signature=" + sign(APP_METHOD, APP_URL, params.toArray(template), searchFood));
        String url = null;

            url =  paramify(params.toArray(template));

        Log.i("URL", url.toString());
        String[] par = params.toArray(template);
        for (int i = 0; i < par.length; i++) {
            Log.i("tag param", par[i] + "");
        }
        return resuOfsignature;
    }

    /**
     * Returns the percent-encoded string for the given url
     *
     * @param url URL which is to be encoded using percent-encoding
     * @return the encoded url
     */
    public String encode(String url) {
        if (url == null)
            return "";

        try {
            return URLEncoder.encode(url, "utf-8")
                    .replace("+", "%20")
                    .replace("!", "%21")
                    .replace("*", "%2A")
                    .replace("\\", "%27")
                    .replace("(", "%28")
                    .replace(")", "%29");
        } catch (UnsupportedEncodingException wow) {
            throw new RuntimeException(wow.getMessage(), wow);
        }
    }

    private static String[] generateOauthParams(int i) {
        timestampValue = Long.valueOf(System.currentTimeMillis() * 2).toString();
        nonceValue=nonce();
        return new String[]{
                "oauth_consumer_key=" + APP_KEY,          // Your API key when you registered as a developer
                "oauth_signature_method=HMAC-SHA1",   //The method used to generate the signature (only HMAC-SHA1 is supported)
                "oauth_timestamp=" + timestampValue                    //The date and time, expressed in the number of seconds since January 1, 1970 00:00:00 GMT.
                        , // Should be  Long.valueOf(System.currentTimeMillis() / 1000).toString()
                "oauth_nonce=" + nonceValue ,               // A randomly generated string for a request that can be combined with the timestamp to produce a unique value
                "oauth_version=1.0",                    // MUST be "1.0"
                "format=json",                          // The desired response format. Valid reponse formats are "xml" or "json" (default value is "xml").
                "page_number=" + i,                     // The zero-based offset into the results for the query. Use this parameter with max_results to request successive pages of search results (default value is 0).
                "max_results=" + 20};                   // The maximum number of results to return (default value is 20). This number cannot be greater than 50.
    }

    public String getoauth_consumer_key() {
        return APP_KEY;
    }

    public String getoauth_signature_method() {
        return "HMAC-SHA1";
    }

    public String getoauth_timestamp() {
        return timestampValue;
    }

    public String getoauth_nonce() {
        return nonceValue;
    }

    public String getoauth_version() {
        return "1.0";
    }

    public String getformat() {
        return "json";
    }

    public String getpage_number(int i) {
        return "" + i;
    }

    public String getmax_results() {
        return "20";
    }

    public String getmethod() {
        return "foods.search";
    }

    public String getsearch_expression(String searchFood) {
        return Uri.encode(searchFood);
    }


    public String sign(String method, String uri, String[] params, String Search) throws UnsupportedEncodingException {
        String encodedURI = encode(uri);
        String encodedParams = encode(paramify(params));
        String[] p = {method, encodedURI, encodedParams};
        String s = join(p, "&");
        Log.i("Food s", s);
        String key = APP_SECRET + "&";
        SecretKey sk = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
        try {
            Mac m = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            m.init(sk);
            Log.i("Url m", m.getAlgorithm());
            Log.i("Url", sk.getEncoded().toString());
            Log.i("URL Sign", encode(new String(Base64.encode(m.doFinal(s.getBytes()), Base64.DEFAULT)).trim()));
            resuOfsignature =  encode(new String(Base64.encode(m.doFinal(s.getBytes()), Base64.DEFAULT)).trim());
            Log.i("url res of sign", resuOfsignature);
            return resuOfsignature;
        } catch (java.security.NoSuchAlgorithmException e) {
            Log.w("FatSecret_TEST FAIL", e.getMessage());
            return null;
        } catch (java.security.InvalidKeyException e) {
            Log.w("FatSecret_TEST FAIL", e.getMessage());
            return null;
        }


//        return consumer.sign(url);
    }

    private static String paramify(String[] params) {
        String[] p = Arrays.copyOf(params, params.length);
        Arrays.sort(p);
        return join(p, "&");
    }

    private static String join(String[] array, String separator) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0)
                b.append(separator);
            b.append(array[i]);
        }
        return b.toString();
    }

    private static String nonce() {
        Random r = new Random();
        StringBuilder n = new StringBuilder();
        for (int i = 0; i < r.nextInt(8) + 2; i++)
            n.append(r.nextInt(26) + 'a');
        return n.toString();
    }
}