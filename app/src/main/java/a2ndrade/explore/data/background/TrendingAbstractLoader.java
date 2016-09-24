package a2ndrade.explore.data.background;

import android.content.Context;
import android.os.Bundle;

public abstract class TrendingAbstractLoader<T> extends AbstractAsyncTaskLoader<T> {
    private static final String TODAY = "today";
    private static final String WEEKLY = "weekly";
    private static final String MONTHLY = "monthly";
    private static final String BUNDLE_TIME_FRAME_KEY = "BUNDLE_TIME_FRAME_KEY";

    static final String QUERY_PARAM_SINCE = "?since=";

    String since;

    public TrendingAbstractLoader(Context context, Bundle bundle) {
        super(context);
        String timeFrame = bundle.getString(BUNDLE_TIME_FRAME_KEY, TODAY);
        if (timeFrame.equalsIgnoreCase(TODAY)) {
            since = TODAY;
        } else if (timeFrame.contains("week")) {
            since = WEEKLY;
        } else {
            since = MONTHLY;
        }
    }
}
