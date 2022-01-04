import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;


public class Logout extends HttpServlet {

  	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
	response.setContentType("text/html");

	PrintWriter out = response.getWriter();


    	out.println("<html>");
    	out.println("<head><title>Log out</title></head>");
    	out.println("<body bgcolor=\"#ffffff\">");
	


    	try{

    		Class.forName("com.mysql.jdbc.Driver");
    		String url = "jdbc:mysql://localhost/test";
    		Connection con=DriverManager.getConnection(url, "root", "root");
    		Statement st=con.createStatement();

		HttpSession session = request.getSession(false);
     		if(session != null)
		{
			session.invalidate();
			out.println("<script>");
         		out.println("setTimeout(function(){");
            		out.println("window.location.href = 'Login.html';");
         		out.println("}, 2000);");
      			out.println("</script><h1>YOU ARE LOGGED OUT!!</h1>");
			out.println("</body></html>");
		}
		else
		{
			
			out.println("<script>");
         		out.println("setTimeout(function(){");
            		out.println("window.location.href = 'Login.html';");
         		out.println("}, 2000);");
      			out.println("</script><h1>PLEASE DO LOG IN FIRST!!</h1>");
			out.println("</body></html>");
		}
    		
	}catch(Exception e)
	{
      		out.println(e);
    	}

  }

}