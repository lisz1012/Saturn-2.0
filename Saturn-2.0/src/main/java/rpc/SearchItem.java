package rpc;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.category.CategoryDao;
import db.category.CategoryDaoFactory;
import db.favorite.FavoriteDao;
import db.favorite.FavoriteDaoFactory;
import db.item.ItemDao;
import db.item.ItemDaoFactory;
import entity.Item;
import external.TicketDaoFactory;
import external.TicketMasterDao;
import utils.HttpCode;
import utils.WebPrinter;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final String URL = "https://app.ticketmaster.com/discovery/v2/events.json?";
	//private static final String DEDAULT_KEYWORD = "events";
	private static final TicketMasterDao TICKET_MASTER_DAO = TicketDaoFactory.get();
	private static final ItemDao ITEM_DAO = ItemDaoFactory.get();
	private static final FavoriteDao FAVORITE_DAO = FavoriteDaoFactory.get();
	private static final CategoryDao CATEGORY_DAO = CategoryDaoFactory.get();
	
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(HttpCode.NO_AUTH.value());
			return;
		}
		
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));
		String term = request.getParameter("term");
		String user_id = request.getParameter("user_id");
		
		List<Item> items = TICKET_MASTER_DAO.search(lat, lon, term);
		//Must add items first because of the foreign key constraint
		ITEM_DAO.add(items);
		Set<String> favorites = FAVORITE_DAO.getFavoriteItemIds(user_id);
		JSONArray array = new JSONArray();
		for (Item item : items) {
			JSONObject object = item.toJSONObject();
			try {
				object.put("favorite", favorites.contains(item.getId()));
				for (String category : item.getCategories()) {
					CATEGORY_DAO.add(item.getId(), category);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			array.put(object);
		}
		
		WebPrinter.printJSONArray(response, array);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
