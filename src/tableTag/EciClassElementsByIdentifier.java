package tableTag;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import eciUrlEnum.EciTagType;

public class EciClassElementsByIdentifier {
	
	public static List<Element> getElementsByIdentifier(Document document ,String identifier, EciTagType identifiertype)
	{
     
		List<Element> elements = new ArrayList<>();

        switch (identifiertype){
        
            case ID:
            	
            	Element idElement = document.getElementById(identifier);
            	if (idElement != null)
                    elements.add(idElement);
                
                break;
            case TAG:
            	
            	Elements tagElements = document.getElementsByTag(identifier);
                elements.addAll(tagElements);
                break;
            case ATTRIBUTE:
            	Elements attrElements = document.getElementsByAttribute(identifier);
                elements.addAll(attrElements);
                break;
            case CLASS:
            	Elements classElements = document.getElementsByClass(identifier);
                elements.addAll(classElements);
                break;
            default:
                System.out.println("Not a valid Identifier type");
        }
        
        return elements;
	}
	
}
