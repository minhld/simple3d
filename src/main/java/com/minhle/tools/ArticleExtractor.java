package com.minhle.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ArticleExtractor {

    public static String extractArticleText(String url) throws IOException {
        // Connect to the URL and parse the HTML
        Document document = Jsoup.connect(url).get();

        // Use a selector to find the main article content
        // This selector may vary depending on the website's structure
        Elements articleElements = document.select("article, #main-content, .post-content, .entry-content");

        // If no elements are found with the above selectors, try a more generic one
        if (articleElements.isEmpty()) {
            articleElements = document.select("body");
        }

        // Extract and return the text content of the first matching element
        if (!articleElements.isEmpty()) {
            Element articleElement = articleElements.first();
            return articleElement.text();
        }
        return "Article text could not be extracted.";
    }

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