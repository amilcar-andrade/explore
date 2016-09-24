package a2ndrade.explore.data.background;

import android.content.Context;
import android.os.Bundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import a2ndrade.explore.data.model.Repo;

public class ReposTrendingLoader extends TrendingAbstractLoader<List<Repo>> {
    private static final String END_POINT = "https://github.com/trending";

    public ReposTrendingLoader(Context context, Bundle bundle) {
        super(context, bundle);
    }

    @Override
    protected List<Repo> loadInBackground0() throws Exception {
        Document document = Jsoup.connect(END_POINT + QUERY_PARAM_SINCE + since).get();
        final Elements repoList = document.select(".repo-list-item");
        final Elements repo = repoList.select(".repo-list-name");
        final Elements des = repoList.select(".repo-list-description");

        final int size = repo.size();
        data = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            data.add(new Repo(repo.get(i).text(), des.get(i).text(), true));
        }

        return data;
    }
}
