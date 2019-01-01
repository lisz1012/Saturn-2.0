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
import entity.Item;
import utils.HttpCode;
import utils.WebPrinter;

/**
 * Servlet implementation class FavoriteItem
 */
@WebServlet("/history")
public class FavoriteItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String RESULT_NAME = "result";
    private static final String RESULT_CODE_SUCCESS = "SUCCESS";
    
    private FavoriteDao favoriteDao = FavoriteDaoFactory.get();
    
    private CategoryDao categoryDao = CategoryDaoFactory.get();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FavoriteItem() {
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
		
		String user_id = request.getParameter("user_id");
		List<Item> favoriteItems = favoriteDao.getFavoriteItems(user_id);
		JSONArray array = new JSONArray();
		for (Item item : favoriteItems) {
			JSONObject object = item.toJSONObject();
			try {
				object.put("favorite", true);
				Set<String> categories = categoryDao.getCategory(item.getId());
				object.put("categories", categories);
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
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(HttpCode.NO_AUTH.value());
			return;
		}
		
		JSONObject input = WebPrinter.readJSONObject(request);
		try {
			String userId = input.getString("user_id");
			JSONArray array = input.getJSONArray("favorite");
			for (int i = 0; i < array.length(); i++) {
				String itemId = array.getString(i);
				favoriteDao.addFavorite(userId, itemId);
			}
			JSONObject object = new JSONObject();
			object.put(RESULT_NAME, RESULT_CODE_SUCCESS);
			WebPrinter.printJSONObject(response, object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject input = WebPrinter.readJSONObject(request);
		try {
			String userId = input.getString("user_id");
			JSONArray array = input.getJSONArray("favorite");
			for (int i = 0; i < array.length(); i++) {
				String itemId = array.getString(i);
				favoriteDao.deleteFavorite(userId, itemId);
			}
			JSONObject object = new JSONObject();
			object.put(RESULT_NAME, RESULT_CODE_SUCCESS);
			WebPrinter.printJSONObject(response, object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
