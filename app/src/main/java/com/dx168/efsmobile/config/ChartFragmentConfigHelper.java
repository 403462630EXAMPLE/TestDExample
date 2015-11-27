package com.dx168.efsmobile.config;

import android.content.Context;

import com.dx168.chart.IndexPermission;
import com.baidao.chart.config.ChartConfig;
import com.baidao.chart.config.CustomIndexConfig;
import com.baidao.chart.config.CustomIndexPermission;
import com.baidao.chart.config.UserPermission;
import com.baidao.data.User;
import com.baidao.tools.UserHelper;
import com.baidao.tools.Util;
import com.ytx.library.provider.Domain;
import com.ytx.library.provider.UserPermissionApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjhy on 15-7-16.
 */
public class ChartFragmentConfigHelper {

    public static void setUpOnCreate(Context context, String categoryId) {
        setUpIndexConfig(context, categoryId);
        setUpDomain();
    }

    public static void setUpOnStart(Context context, String categoryId) {
        setUpIndexPermissionConfig(context, categoryId);
        setUpUserPermission(context);
    }

    private static void setUpIndexPermissionConfig(Context context, String categoryId) {
        List<IndexPermission> indexPermissions = IndexPermissionHelper.getIndexPermissionsOfSid(context, categoryId);
        if (indexPermissions != null && indexPermissions.size() > 0) {
            List<CustomIndexPermission> customIndexPermissions = new ArrayList();
            for (IndexPermission indexPermission : indexPermissions) {
                CustomIndexPermission customIndexPermission = getCustomIndexPermission(indexPermission, context, categoryId);
                if (customIndexPermission != null) {
                    customIndexPermissions.add(customIndexPermission);
                }
            }
            ChartConfig.setIndexPermissions(customIndexPermissions);
        }
    }

    private static void setUpIndexConfig(Context context, String categoryId) {
        List<CustomIndexConfig> indexConfigs = IndexConfigHelper.getIndexConfig(context, categoryId);

        ChartConfig.restIndexConfig();
        ChartConfig.setIndexConfigs(indexConfigs);

    }

    private static CustomIndexPermission getCustomIndexPermission(IndexPermission indexPermission, Context context, String categoryId) {
        if (indexPermission.hasIndex) {
            String groupId = UserPermissionApi.INDEX_TO_GROUP_ID.get(indexPermission.indexName);
            int permissionOfUser = UserPermissionHelper.getUserIndexPermission(context, categoryId, indexPermission.lineType, groupId);
            return new CustomIndexPermission(categoryId, indexPermission.lineType, indexPermission.indexName, indexPermission.hasIndex, permissionOfUser);
        }

        return null;
    }

    private static void setUpUserPermission(Context context) {
        User user = UserHelper.getInstance(context).getUser();
        UserPermission userPermission = UserPermission.getInstance();
        userPermission.userType = user.userType;
        userPermission.hasPhone = user.hasPhone;
        userPermission.username = user.username;
        userPermission.serverId = Util.getCompanyId(context);
    }

    public static void setUpDomain() {
        String quoteDomain = Domain.get(Domain.DomainType.QUOTES);
        ChartConfig.setQuoteDomain(quoteDomain);

        String calendarDomain = Domain.get(Domain.DomainType.MOBILESERVICE);
        ChartConfig.setCalendarDomain(calendarDomain);
    }

}
