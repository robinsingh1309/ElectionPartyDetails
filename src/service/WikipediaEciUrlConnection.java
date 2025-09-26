package service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WikipediaEciUrlConnection {
	
	public Document getDocument(String wikiEciUrl) throws IOException 
	{
		return Jsoup.connect(wikiEciUrl)
				.timeout(5000)
				.get();
	}
	
}