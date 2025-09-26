package ElementExtractor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NationalPartyExtractor {
	
	private HeadQuarterExtractor addressExtractor;
	
	public NationalPartyExtractor()
	{
		this.addressExtractor = new HeadQuarterExtractor();
	}

	public void nationalPartyData(Element element) throws IOException, InterruptedException 
	{
		if(element == null) 
    	{
    		System.out.println("No table found for National Party");
    		return;
    	}
    	
		try (
				BufferedWriter writer = new BufferedWriter(new FileWriter("NationalPartyData.csv"))
			) 
		{
			writer.write("Party Name,Election Symbol,Political Position,Headquarter Address\n");
			
	        Elements rows = element.select("tbody > tr");
	        
	        for(Element row : rows) 
	        {
	        	Elements cells = row.select("td");
	        	
	        	if (cells.size() < 6) continue;
	    		
	        	Elements link = cells.get(1).select("a");
	        	String addressLink = ( link.isEmpty() ) ? "" : "https://en.wikipedia.org/" + link.attr("href");
	    		
	    		String partyName = cells.get(1).text();
	    		
	    	    Elements symbolLink = cells.get(4).select("img");
	    	    String electionSymbol = symbolLink.isEmpty() ? "Election Symbol not available" : "https:" + symbolLink.attr("src");
	    	    
	    		String partyPoliticalPosition = cells.get(5).text();
	    		
	    		String headQuarterAddress = addressExtractor.getHeadQuarterAddressData(addressLink);
	    		
	    		StringBuilder sb = new StringBuilder();
                sb.append(partyName).append(",");
                sb.append(electionSymbol).append(",");
                sb.append(partyPoliticalPosition).append(",");
                sb.append(headQuarterAddress).append("\n");
                
                writer.write(sb.toString());
	        	
	        	Thread.sleep(1000);
	        }
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Data successfully written to NationalPartyData.csv");
	}	
}