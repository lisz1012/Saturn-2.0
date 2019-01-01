package rpc;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import db.favorite.FavoriteDao;
import db.favorite.FavoriteDaoFactory;
import db.recommend.RecommendationDao;
import db.recommend.RecommendationDaoFactory;
import entity.Item;
import service.recommend.ItemService;
import service.recommend.TicketMasterServiceImpl;
import utils.HttpCode;
import utils.WebPrinter;

/**
 * Servlet implementation class RecommendItem
 */
@WebServlet("/recommendation")
public class RecommendationItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ItemService ITEM_SERVICE = new TicketMasterServiceImpl();
	private static final RecommendationDao RECOMMENDATION_DAO = RecommendationDaoFactory.get();
	private static final FavoriteDao FAVORITE_DAO = FavoriteDaoFactory.get();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommendationItem() {
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
		
		String userId = request.getParameter("user_id");
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request .getParameter("lon"));
		Set<String> categories = RECOMMENDATION_DAO.getRecommendedCategories(userId);
		
		Set<Item> recommendedItems = ITEM_SERVICE.getItemsFromTicketMaster(lat, lon, categories);
		Set<String> favoriteIds = FAVORITE_DAO.getFavoriteItemIds(userId);
		
		JSONArray array = new JSONArray();
		for (Item item : recommendedItems) {
			if (!favoriteIds.contains(item.getId())) {
				array.put(item.toJSONObject());
			}
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

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
