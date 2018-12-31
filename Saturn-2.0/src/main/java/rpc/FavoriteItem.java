package rpc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.favorite.FavoriteDao;
import db.favorite.FavoriteDaoFactory;
import entity.Item;
import utils.WebPrinter;

/**
 * Servlet implementation class FavoriteItem
 */
@WebServlet("/history")
public class FavoriteItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final FavoriteDao FAVORITE_DAO = FavoriteDaoFactory.get();
    private static final String RESULT_NAME = "result";
    private static final String RESULT_CODE_SUCCESS = "SUCCESS";
	
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
		String user_id = request.getParameter("user_id");
		List<Item> favoriteItems = FAVORITE_DAO.getFavoriteItems(user_id);
		JSONArray array = new JSONArray();
		for (Item item : favoriteItems) {
			JSONObject object = item.toJSONObject();
			try {
				object.put("favorite", true);
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
		JSONObject input = WebPrinter.readJSONObject(request);
		try {
			String userId = input.getString("user_id");
			JSONArray array = input.getJSONArray("favorite");
			for (int i = 0; i < array.length(); i++) {
				String itemId = array.getString(i);
				FAVORITE_DAO.addFavorite(userId, itemId);
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
				FAVORITE_DAO.deleteFavorite(userId, itemId);
			}
			JSONObject object = new JSONObject();
			object.put(RESULT_NAME, RESULT_CODE_SUCCESS);
			WebPrinter.printJSONObject(response, object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
