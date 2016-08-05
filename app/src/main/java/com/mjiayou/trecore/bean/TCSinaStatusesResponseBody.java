package com.mjiayou.trecore.bean;


import com.mjiayou.trecore.bean.entity.TCSinaStatuses;
import com.mjiayou.trecore.helper.GsonHelper;

import java.util.List;

/**
 * Created by treason on 15/3/11.
 */
public class TCSinaStatusesResponseBody extends TCResponseBody {

    private List<TCSinaStatuses> statuses;

    public static String getData(TCSinaStatusesResponseBody result) {
        return GsonHelper.get().toJson(result);
    }

    public static TCSinaStatusesResponseBody getObject(String data) {
        return GsonHelper.get().fromJson(data, TCSinaStatusesResponseBody.class);
    }

    public List<TCSinaStatuses> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<TCSinaStatuses> statuses) {
        this.statuses = statuses;
    }
}
