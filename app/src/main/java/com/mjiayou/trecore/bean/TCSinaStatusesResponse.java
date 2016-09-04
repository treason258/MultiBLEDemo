package com.mjiayou.trecore.bean;


import com.mjiayou.trecore.bean.entity.TCSinaStatuses;
import com.mjiayou.trecore.helper.GsonHelper;

import java.util.List;

/**
 * Created by treason on 15/3/11.
 */
public class TCSinaStatusesResponse extends TCResponse {

    private List<TCSinaStatuses> statuses;

    public static String parseString(TCSinaStatusesResponse result) {
        return GsonHelper.get().toJson(result);
    }

    public static TCSinaStatusesResponse parseObject(String data) {
        return GsonHelper.get().fromJson(data, TCSinaStatusesResponse.class);
    }

    public List<TCSinaStatuses> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<TCSinaStatuses> statuses) {
        this.statuses = statuses;
    }
}
