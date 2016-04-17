package com.mobiledev.topimpamatricks.OCR;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
/**
 * Created by maiaphoebedylansamerjan on 4/16/16.
 */
public interface OCRService {
    @Multipart
    @POST("1/api/async/ocrdocument/v1")
    Call<ResponseBody> postMatrix(@Part("apikey") RequestBody apikey, @Part("file\"; filename=\"image.png") RequestBody file, @Part("mode") RequestBody mode);
}
