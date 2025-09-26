package ElementExtractor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StatePartyExtractor {
	
	private HeadQuarterExtractor addressExtractor;
	
	public StatePartyExtractor()
	{
		this.addressExtractor = new HeadQuarterExtractor();
	}

	public void statePartyData(Element element) throws InterruptedException, IOException 
	{
		if(element == null) 
    	{
    		System.out.println("No table found for State Party");
    		return;
    	}
		
		try 
			(
				BufferedWriter writer = new BufferedWriter(new FileWriter("StatePartyData.csv"))
			)
		{
			writer.write("Party Name,Election Symbol,Recognised In States,Headquarter Address\n");
			
			Elements rows = element.select("tbody > tr");
	        
	        for(Element row : rows) 
	        {
	        	Elements cells = row.select("td");
	        	
	        	if (cells.size() < 6) continue;
	        	
	        	Elements link = cells.get(1).select("a");
	        	String addressLink = ( link.isEmpty() ) ? "" : "https://en.wikipedia.org/" + link.attr("href");
	        	
	    		String partyName = cells.get(1).text();
	    		
	    	    Elements symbolLink = cells.get(3).select("img");
	    	    String electionSymbol = symbolLink.isEmpty() ? "Election Symbol not available" : "https:" + symbolLink.attr("src");
	    	    
	    		String recognisedInStates = cells.get(6).text();
	    		
	    		String headQuarterAddress = addressExtractor.getHeadQuarterAddressData(addressLink);
	    		
	    		StringBuilder sb = new StringBuilder();
                sb.append(partyName).append(",");
                sb.append(electionSymbol).append(",");
                sb.append(recognisedInStates).append(",");
                sb.append(headQuarterAddress).append("\n");
                
                writer.write(sb.toString());
	        	
	        	Thread.sleep(1000);
	        	
	        }	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Data successfully written to StatePartyData.csv");   
	}	
}