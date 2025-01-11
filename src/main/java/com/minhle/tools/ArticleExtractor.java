package com.minhle.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ArticleExtractor {

    public static String extractArticle(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element article = doc.select("article, .post, .entry").first();

        if (article == null) {
            article = doc.select("body").first();
        }

        removeAds(article);

        return article.outerHtml();
    }

    private static void removeAds(Element element) {
        Elements ads = element.select("aside, .ad, .advertisement, [id~=(ad|advertisement|sponsor)]");
        ads.forEach(ad -> ad.remove());

        Elements scripts = element.select("script");
        scripts.forEach(script -> script.remove());

        Elements iframes = element.select("iframe");
        iframes.forEach(iframe -> {
            if (iframe.attr("src").contains("youtube") || iframe.attr("src").contains("vimeo")) {
                // Keep video iframes
            } else {
                iframe.remove();
            }
        });
    }
}