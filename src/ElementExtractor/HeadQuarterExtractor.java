package ElementExtractor;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HeadQuarterExtractor {

	public String getHeadQuarterAddressData(String WebUrl) throws IOException 
	{
		if(WebUrl == null || WebUrl.isEmpty()) return "Web Link is not associated with the Party on Wikipedia";
		
		Document partyDocument = Jsoup.connect(WebUrl)
									.timeout(8000)
									.get();
		
        Elements tables = partyDocument.select("table");
		
		for (Element table : tables) {
			
            Elements rows = table.select("tr");

            for (Element row : rows) {
            
            	Element headerCell = row.selectFirst("th, td");
            	
                if (headerCell != null && headerCell.text().equals("Headquarters")) {
                	
                	// important point to note down here !!!!!
                    Element dataCell = row.selectFirst("td"); // the next <td> in the same row
                    
                    if (dataCell != null) return dataCell.text();
                    
                }
            }
        }
		
		return "Adress Not Found on Wikipedia";
	}
	
}