package db.favorite;

import java.util.List;
import java.util.Set;

import entity.Item;
import external.TicketDaoFactory;
import external.TicketMasterDao;

public class TicketMasterFavoriteDao extends AbstractFavoriteDao {

	@Override
	public List<Item> getFavoriteItems(String user_id) {
		TicketMasterDao ticketMasterDao = TicketDaoFactory.get();
		Set<String> favoriteItemIds = getFavoriteItemIds(user_id);
		for (String itemId : favoriteItemIds) {
			System.out.print(itemId + " - ");
		}
		System.out.println();
		List<Item> favoriteItems = ticketMasterDao.search(favoriteItemIds);
		
		return favoriteItems;
	}

	public static void main(String[] args) {
		AbstractFavoriteDao favoriteDaoImpl = new TicketMasterFavoriteDao();
		Set<String> set = favoriteDaoImpl.getFavoriteItemIds("1111");
		for (String s : set) {
			System.out.println(s);
		}
		
	}
}
