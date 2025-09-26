package eciUrlEnum;

public enum EciWikiUrlEnum {
	
	WIKIPEDIA_ECI_LIST_OF_POLITICAL_PARTIES_IN_INDIA_URL("https://en.wikipedia.org/wiki/List_of_political_parties_in_India");
	
	private String value;
	
	EciWikiUrlEnum(String val) {
		this.value = val;
	}
	
	public String getValue() 
	{
		return value;
	}
	
}