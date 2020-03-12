package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import infectEmpl.InfectMap;

/**
 * Servlet implementation class StatServlet
 */
@WebServlet("/statServlet")
public class StatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String flag = request.getParameter("flag");
		String date = request.getParameter("date");
		String province = request.getParameter("province");
		if(date == null)
		{
			date = InfectMap.latestDate();
		}
		if(flag != null && flag.equals("mapStat"))
		{
			Map<String, String> stat = InfectMap.allStatistic(date);
			request.setAttribute("stat", stat);
			request.getRequestDispatcher("test2.jsp").forward(request, response);
		}
		
		else if(flag != null && flag.equals("dateSet"))
		{
			
		}
				
		else if(flag != null && flag.equals("increase"))
		{
			ArrayList<String> increase = InfectMap.compare(province, date);
			request.setAttribute("increase", increase);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
