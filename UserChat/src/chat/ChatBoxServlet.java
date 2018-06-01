package chat;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ChatBoxServlet")
public class ChatBoxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ChatBoxServlet ������ ����");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String userID = URLDecoder.decode(request.getParameter("userID"), "utf-8");
		if(userID == null || userID.equals("")) {
			response.getWriter().write("");
		} else {
			try {
				response.getWriter().write(getBox(userID));
			} catch (Exception e) {
				response.getWriter().write("");
			}
		}
	}
	
	public String getBox(String userID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		chatDAO chatDAO = new chatDAO();
		ArrayList<chatDTO> chatList = chatDAO.getBox(userID);
		if(chatList.size() == 0) return "";
		for(int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			if(i != chatList.size() -1) result.append(",");
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() -1).getChatID() + "\"}");
		return result.toString();
	}

}
