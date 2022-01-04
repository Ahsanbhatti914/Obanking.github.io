import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;


public class TransHistory extends HttpServlet {

  	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
	response.setContentType("text/html");

	PrintWriter out = response.getWriter();

    	String email=request.getParameter("email");
    	String password=request.getParameter("password");
	String sess_email="";
	String sess_password="";

    	out.println("<html>");
	out.println("<style>table, th, td {border: 1px solid black;}</style>");
    	out.println("<head><title>Transaction History</title></head>");
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
						String query1="Select * from history where EMAIL = '"+email+"'";
						ResultSet rs1 = st.executeQuery(query1);
						out.println("<h1>HERE IS YOUR ACCOUNT DETAILS!!</h1>");
						out.println("<table><tr><th>ACCOUNT ID</th><th>BALANCE</th><th>TRANSACTION</th><th>TRANSACTION BALANCE</th><th>TRANSACTION DATE</th><th>ACCOUNT DATE</th></tr>");
						while(rs1.next())
						{

							String balance = rs1.getString("BALANCE");
							String transaction = rs1.getString("TRANSACTIONS");
							String transaBalance = rs1.getString("TRANS_BALANCE");
							String transaDate = rs1.getString("TRANS_DATE");
							String accDate = rs1.getString("ACC_DATE");

							out.println("<tr><td>"+email+"</td><td>"+balance+"</td><td>"+transaction+"</td><td>"+transaBalance+"</td><td>"+transaDate+"</td><td>"+accDate+"</td></tr>");
					
						}
						out.println("</table>");
					}
					else
					{
						out.println("<h1>CREDENTIALS NOT FOUND!!</h1>");
					}
				}
				else
				{		
					out.println("<h1>PLEASE ENTER CORRECT CREDENTIALS!!</h1>");	
        				
     				}
			}
			else
			{		
				out.println("<h1>CREDENTIALS NOT FOUND!!</h1>");
     			}
			out.println("<br><br></body></html>");
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