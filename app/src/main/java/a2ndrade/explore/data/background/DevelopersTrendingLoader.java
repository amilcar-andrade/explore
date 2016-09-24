package a2ndrade.explore.data.background;

import android.content.Context;
import android.os.Bundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import a2ndrade.explore.data.model.Developer;

public class DevelopersTrendingLoader extends TrendingAbstractLoader<List<Developer>> {
    private static final String END_POINT = "https://github.com/trending/developers";

    public DevelopersTrendingLoader(Context context, Bundle bundle) {
        super(context, bundle);
    }

    @Override
    protected List<Developer> loadInBackground0() throws Exception {
        // Good to know: the Context returned by getContext()
        // is the application context
        Document document = Jsoup.connect(END_POINT + QUERY_PARAM_SINCE + since).get();
        final Elements description = document.select(".user-leaderboard-list-item.leaderboard-list-item");
        final Elements avatars = description.select(".leaderboard-gravatar");
        final Elements repo = description.select(".repo");
        final Elements des = description.select(".repo-snipit-description.css-truncate-target");
        final Elements names = description.select(".user-leaderboard-list-name");

        final int size = description.size();
        data = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            data.add(new Developer(
                    description.get(i).id(),
                    names.get(i).text(),
                    avatars.get(i).absUrl("src"),
                    repo.get(i).text(),
                    des.get(i).text()));
        }

        return data;
    }
}
