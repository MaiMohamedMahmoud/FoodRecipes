package com.example.mac_os.foodrecipe.data.Utility;

import com.example.mac_os.foodrecipe.data.FatSecretApi;
import com.example.mac_os.foodrecipe.data.RetrofitClient;

public class ApiUtils {
    public static final String BASE_URL = "https://api.stackexchange.com/2.2/";

    public static FatSecretApi getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(FatSecretApi.class);
    }
}
