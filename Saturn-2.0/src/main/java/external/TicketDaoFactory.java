package external;

public class TicketDaoFactory {
	private static final String API_KEY = "R58xd49lUlsLDpwPATXUmvNliwAnyRZW";
	private static final String URL = "https://app.ticketmaster.com/discovery/v2/events.json";
	private static final String SINGLE_EVENT_URL = "https://app.ticketmaster.com/discovery/v2/events/";
	private static final TicketMasterDaoImpl TICKET_MASTER_DAO = new TicketMasterDaoImpl(URL, SINGLE_EVENT_URL, API_KEY);
	
	public static TicketMasterDao get() {
		return TICKET_MASTER_DAO;
	}
}
