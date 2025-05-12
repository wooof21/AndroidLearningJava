package com.androidlearning.retrofitandpaging.model.api;

import static com.androidlearning.retrofitandpaging.utils.Constants.API_KEY;
import static com.androidlearning.retrofitandpaging.utils.Constants.BASE_URL;

import com.androidlearning.retrofitandpaging.model.pojo.PMovieRes;


import io.reactivex.rxjava3.core.Single;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class APIClient {

    private static APIInterface apiInterface;

    //Retrofit Instance
    public static APIInterface getApiInterface() {
        if(apiInterface == null) {
            OkHttpClient.Builder client = new OkHttpClient.Builder();

            //Use an interceptor to add API key as query param to each request
            client.addInterceptor(chain -> {
                Request originalReq = chain.request();
                HttpUrl originalHttpUrl = originalReq.url();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", API_KEY)
                        .build();
                Request request = originalReq.newBuilder().url(url).build();
                return chain.proceed(request);
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();

            apiInterface = retrofit.create(APIInterface.class);
        }

        return apiInterface;
    }

    public interface APIInterface {
        @GET("movie/popular")
        Single<PMovieRes> getPopularMoviesByPages(@Query("page") int page);
    }
}
