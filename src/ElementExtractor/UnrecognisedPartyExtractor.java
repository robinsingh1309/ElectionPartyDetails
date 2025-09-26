package ElementExtractor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UnrecognisedPartyExtractor {
	
	private HeadQuarterExtractor addressExtractor;
	
	public UnrecognisedPartyExtractor()
	{
		this.addressExtractor = new HeadQuarterExtractor();
	}

	public void unRecognisedPartyData(Element element) throws IOException, InterruptedException 
	{
		if(element == null) 
    	{
    		System.out.println("No table found for Unrecognised Party");
    		return;
    	}
		
		try 
			(
				BufferedWriter writer = new BufferedWriter(new FileWriter("UnrecognisedPartyData.csv"))
			)
		{
			
			writer.write("Party Name,Election Symbol,States,Headquarter Address\n");
			
			Elements rows = element.select("tbody > tr");
	        
	        for(Element row : rows) 
	        {
	        	Elements cells = row.select("td");
	        	
	        	if (cells.size() < 2) continue;
	        	
	        	Elements link = cells.get(0).select("a");
	        	String addressLink = ( link.isEmpty() ) ? "" : "https://en.wikipedia.org/" + link.attr("href");
	        	
	    		String partyName = cells.get(0).text();
	    		
	    	    String electionSymbol = "Election Symbol not available";
	    	    
	    		String states = cells.get(3).text();
	    		
	    		String headQuarterAddress = addressExtractor.getHeadQuarterAddressData(addressLink);
	    		
	    		StringBuilder sb = new StringBuilder();
                sb.append(partyName).append(",");
                sb.append(electionSymbol).append(",");
                sb.append(states).append(",");
                sb.append(headQuarterAddress).append("\n");
                
                writer.write(sb.toString());
                
                
	        	Thread.sleep(1000);
	        }
	        
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Data successfully written to UnrecognisedPartyData.csv");      
	}	
}