package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import db.user.UserDao;
import db.user.UserDaoFactory;
import entity.User;
import utils.WebPrinter;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String USER_ID_KEY = "user_id";
	private static final String PASSWORD_KEY = "password";
	private static final String FULL_NAME_KEY = "name";
	private static final String STATUS_CODE_OK = "OK";
	private static final String STATUS_CODE_FAIL = "FAIL";
	private static final String STATUS_NAME = "status";
	// After how many seconds if the client has no interaction with 
	// server side the session will be terminated.
	private static final int MAX_INACTIVE_INTERVAL = 1800;
	
	private UserDao userDao = UserDaoFactory.get();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		JSONObject result = new JSONObject();
		try {
			if (session != null) {
				result.put(USER_ID_KEY, session.getAttribute(USER_ID_KEY))
					  .put(FULL_NAME_KEY, session.getAttribute(FULL_NAME_KEY))
				      .put("status", "OK");
			} else {
				result.put("status", "Invalid Session");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		WebPrinter.printJSONObject(response, result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject input = WebPrinter.readJSONObject(request);
		try {
			String userId = input.getString(USER_ID_KEY);
			String password = input.getString(PASSWORD_KEY);
			JSONObject result = new JSONObject();
			User user = null;
			if ((user = userDao.getUser(userId, password)) != null) {
				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
				session.setAttribute(USER_ID_KEY, userId);
				session.setAttribute(FULL_NAME_KEY, user.getFirstName() + " " + user.getLastName());
				result.put(STATUS_NAME, STATUS_CODE_OK)
				      .put(USER_ID_KEY, user.getId())
					  .put(FULL_NAME_KEY, user.getFirstName() + " " + user.getLastName())
				      .put("status", "OK");
			} else {
				result.put(STATUS_NAME, STATUS_CODE_FAIL);
			}

			WebPrinter.printJSONObject(response, result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
