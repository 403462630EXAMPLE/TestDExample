package com.ytx.library.provider;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Multipart;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;
import rx.Observable;

/**
 * Created by Bruce on 12/16/14.
 */
public interface AudioApi {
    @Multipart
    @PUT("/voiceUpload/{csrId}/{userName}")
    Observable<Response> uploadVoiceFile(@Part("audio") TypedFile file, @Path("csrId") long csrId, @Path("userName") String userName);
}
