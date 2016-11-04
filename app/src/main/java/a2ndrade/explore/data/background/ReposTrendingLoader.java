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
        final Elements repoList = document.getElementsByClass("repo-list");
        final Elements repoName = repoList.select(".d-inline-block.col-9.mb-1");
        final Elements descriptionRepo = repoList.select(".col-9.d-inline-block.text-gray.m-0.pr-4");

        final int size = repoName.size();
        data = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            data.add(new Repo(repoName.get(i).text(), descriptionRepo.get(i).text(), true));
        }

        return data;
    }
}
