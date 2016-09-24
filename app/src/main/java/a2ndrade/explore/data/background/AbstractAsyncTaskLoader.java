package a2ndrade.explore.data.background;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public abstract class AbstractAsyncTaskLoader<T> extends AsyncTaskLoader<T> {
    private static final String TAG = AbstractAsyncTaskLoader.class.getSimpleName();
    T data;

    public AbstractAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    public final T loadInBackground() {
        // Good to know: the Context returned by getContext() is the application context
        try {
            data = loadInBackground0();
        } catch (Exception e) {
            // TODO: Better error handling
            Log.d(TAG, e.getMessage());
        }
        return data;
    }

    @Override
    protected void onStartLoading() {
        if (data != null) {
            // Use cached data
            deliverResult(data);
        } else {
            // We have no data, so kick off loading it
            forceLoad();
        }
    }

    @Override
    public void deliverResult(T data) {
        // We’ll save the data for later retrieval
        this.data = data;
        // We can do any pre-processing we want here
        // Just remember this is on the UI thread so nothing lengthy!
        super.deliverResult(data);
    }

    protected abstract T loadInBackground0() throws Exception;

}
