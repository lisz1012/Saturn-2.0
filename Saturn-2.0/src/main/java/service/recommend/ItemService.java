package service.recommend;

import java.util.Set;

import entity.Item;

public interface ItemService {
	Set<Item> getItemsFromTicketMaster(double lat, double lon, Set<String> categories);
}
