package com.android.mymusic.service;

public class APIService {

    private static String base_url = "https://mymp3server.000webhostapp.com/Server/";

    public  static Dataservice getService(){
        return APIRetrofitClient.getClient(base_url).create(Dataservice.class);
    }
}
