import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;


public class Deposit extends HttpServlet {

  	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
	response.setContentType("text/html");

	PrintWriter out = response.getWriter();

    	String bal=request.getParameter("amount");
    	String email=request.getParameter("email");
	String password=request.getParameter("password");
	String sess_email="";
	String sess_password="";
   	String trans = "Deposit";

    	out.println("<html>");
    	out.println("<head><title>Deposit</title></head>");
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


			String query="Select * from account where Email='"+email+"' and Password='"+password+"'";
      			ResultSet rs = st.executeQuery(query);
      
      			boolean flag = false;
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
				
						rs1.next();
						String trans_balance = String.valueOf(Double.parseDouble(bal));
						String account_date = rs1.getString("ACC_DATE");
						String balance = rs1.getString("BALANCE");
						double res = Double.parseDouble(balance) + Double.parseDouble(bal);
						String total_balance = String.valueOf(res);


						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
    						Date date = new Date();  
    						String transaction_date = formatter.format(date);

						String query2 = "UPDATE data SET BALANCE='"+total_balance+"',TRANSACTIONS='"+trans+"',TRANS_BALANCE='"+trans_balance+"',TRANS_DATE='"+transaction_date+"',ACC_DATE='"+account_date+"' " + " WHERE EMAIL = '"+email+"' ";
						int rs2 = st.executeUpdate( query2 );
						if(rs2 > 0)
						{
							out.println("<h1>FUNDS HAS BEEN DEPOSIT SUCCESSFULLY!!</h1>");
							out.println("</body></html>");
 						}
					
						String query3 = "INSERT INTO history VALUES('" + email + "','" + total_balance + "','" + trans + "','" + trans_balance + "','" + transaction_date + "','" + account_date + "') ";
						int rs3 = st.executeUpdate( query3 );				
					
						flag = true;
					
					}
					else
					{		
						out.println("<h1>PLEASE ENTER CORRECT CREDENTIALS!!</h1>");	
        					out.println("</body></html>");
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
        			out.println("</body></html>");
     			}

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