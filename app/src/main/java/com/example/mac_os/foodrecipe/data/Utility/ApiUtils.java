package com.example.mac_os.foodrecipe.data.Utility;

import com.example.mac_os.foodrecipe.data.FatSecretApi;
import com.example.mac_os.foodrecipe.data.RetrofitClient;

public class ApiUtils {
    public static final String BASE_URL = "http://platform.fatsecret.com/";

    public static FatSecretApi getFoodService() {
        return RetrofitClient.getClient(BASE_URL).create(FatSecretApi.class);
    }
}
