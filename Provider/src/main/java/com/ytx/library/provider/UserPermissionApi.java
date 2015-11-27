package com.ytx.library.provider;

import com.baidao.data.UserPermission;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by hexi on 15/3/19.
 */
public interface UserPermissionApi {
    static final String GROUP_TJX = "TJX";
    static final String GROUP_QKX = "QKX";
    static final String GROUP_QKT = "QKT";
    static final String GROUP_BYJZ = "BYJZ";

    public ImmutableMap<String, String> INDEX_TO_GROUP_ID = new ImmutableMap.Builder<String, String>()
            .put("TJ", GROUP_TJX)
            .put("QK", GROUP_QKX)
            .put("BY", GROUP_BYJZ)
            .build();

    public static final String GROUPS_OF_PERMISSION = Joiner.on(",").join(new String[]{
            GROUP_TJX,
            GROUP_QKX,
            GROUP_BYJZ
    });

    @GET("/api/rest/users/{usernames}/permissions?fields=func,permission")
    public Observable<ArrayList<UserPermission>> getPermissionsOfUser(@Path("usernames") String username, @Query("groups") String groups);
}
