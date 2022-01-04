import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;


public class ViewBalance extends HttpServlet {

  	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
	response.setContentType("text/html");

	PrintWriter out = response.getWriter();

    	String email=request.getParameter("email");
    	String password=request.getParameter("password");
	String sess_email="";
	String sess_password="";

    	out.println("<html>");
	
	out.println("<style>table, th, td {border: 1px solid black;}</style>");
    	out.println("<head><title>View Balance</title></head>");
    	out.println("<body bgcolor=\"#ffffff\">");


    	try{

    		Class.forName("com.mysql.jdbc.Driver");
    		String url = "jdbc:mysql://localhost/test";
    		Connection con=DriverManager.getConnection(url, "root", "root");
    		Statement st=con.createStatement();
		
		HttpSession session = request.getSession(false);
     		if(session != null)
		{

			Enumeration attributeNames = session.getAttributeNames();
        		while (attributeNames.hasMoreElements()) {
           		sess_email = attributeNames.nextElement().toString();
           		sess_password = session.getAttribute(sess_email).toString();
           		//out.println(name + " = " + value + "<br>");
        		}

     
    			String query="Select * from account where Email='"+email+"' and Password='"+password+"' ";
      			ResultSet rs = st.executeQuery(query);
		
			if(rs.next() == true)
			{
 				String em = rs.getString("Email");
				String ps = rs.getString("Password");
				if(em.equals(sess_email) && ps.equals(sess_password))
				{
	   				if(em.equals(email))
	   				{	
						String query1="Select * from data where EMAIL = '"+email+"'";
						ResultSet rs1 = st.executeQuery(query1);
						out.println("<table><tr><th>ACCOUNT ID</th><th>CURRENT BALANCE</th></tr>");
						rs1.next();
						String balance = rs1.getString("BALANCE");

						out.println("<h1>HERE IS YOUR CURRENT BALANCE!!</h1>");
						out.println("<tr><td>"+email+"</td><td>"+balance+"</td></table>");
					}
					else
					{
						out.println("<h1>CREDENTIALS NOT FOUND!!</h1>");
					}
				}
				else
				{		
					out.println("<h1>PLEASE ENTER CORRECT CREDENTIALS!!</h1>");	
        				out.println("</body></html>");
     				}
			}
			else
			{		
				out.println("<h1>CREDENTIALS NOT FOUND!!</h1>");
     			}	
		
			out.println("</body></html>");
           		st.close();
           		con.close();
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