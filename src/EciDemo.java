import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ElementExtractor.NationalPartyExtractor;
import ElementExtractor.StatePartyExtractor;
import ElementExtractor.UnrecognisedPartyExtractor;
import eciUrlEnum.EciTagType;
import eciUrlEnum.EciWikiUrlEnum;
import service.WikipediaEciUrlConnection;
import tableTag.EciClassElementsByIdentifier;

public class EciDemo {

    public static void main(String[] args) throws IOException, InterruptedException {

        String url = EciWikiUrlEnum.WIKIPEDIA_ECI_LIST_OF_POLITICAL_PARTIES_IN_INDIA_URL.getValue();
        
        WikipediaEciUrlConnection urlConnection = new WikipediaEciUrlConnection();        
        Document doc = urlConnection.getDocument(url);
        
        String tableClassIdentifier = "wikitable";
        
        List<Element> tableElementLists = EciClassElementsByIdentifier.getElementsByIdentifier(doc, tableClassIdentifier, EciTagType.CLASS);

        
        NationalPartyExtractor nationalPartyExtractor = new NationalPartyExtractor();
        nationalPartyExtractor.nationalPartyData(tableElementLists.get(0));
        

        StatePartyExtractor statePartyExtractor = new StatePartyExtractor();
        statePartyExtractor.statePartyData(tableElementLists.get(1));
        

        UnrecognisedPartyExtractor unrecognisedPartyExtractor = new UnrecognisedPartyExtractor();
        unrecognisedPartyExtractor.unRecognisedPartyData(tableElementLists.get(2));
        
    }
}