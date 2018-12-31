package external;

import java.util.List;
import java.util.Set;

import entity.Item;

public interface TicketMasterDao {
	List<Item> search(double lat, double lon, String keyword);
	List<Item> search(Set<String> itemIds);
}
