package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import entity.User;
import entity.User.UserBuilder;
import service.user.UserService;
import service.user.UserServiceImpl;
import utils.WebPrinter;

/**
 * Servlet implementation class Register
 */
@WebServlet("/registration")
public class RegistrationItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserService userService = new UserServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_id = request.getParameter("user_id");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		
		UserBuilder builder = new UserBuilder();
		builder.setId(user_id)
			   .setPassword(password)
			   .setFirstName(firstName)
			   .setLastName(lastName);
		
		User user = builder.build();
		userService.add(user);
		
		JSONObject object = new JSONObject();
		try {
			object.put("status", "success");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		WebPrinter.printJSONObject(response, object);
	}

}
