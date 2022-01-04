import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;


public class transfer extends HttpServlet {

  	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
	response.setContentType("text/html");

	PrintWriter out = response.getWriter();

    	String bal=request.getParameter("amount");
    	String email=request.getParameter("email");
	String rec_email=request.getParameter("recipient_email");
	String password=request.getParameter("password");
   	String transP = "Transfer(+)";
	String transN = "Transfer(-)";
	String sess_email="";
	String sess_password="";

    	out.println("<html>");
    	out.println("<head><title>Response</title></head>");
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
        		while (attributeNames.hasMoreElements()) 
			{
           			sess_email = attributeNames.nextElement().toString();
           			sess_password = session.getAttribute(sess_email).toString();
        		}

     
    			String query="Select * from account where Email = '"+email+"' and Password ='"+password+"' ";
      			ResultSet rs = st.executeQuery(query);
      
      			boolean flag = false;
			if(rs.next() == true)//while1
			{
				//flag = true;
				String em = rs.getString("Email");
	   			if(em.equals(email))
	   			{	
					String query1="Select * from data where EMAIL = '"+email+"' ";
					ResultSet rs1 = st.executeQuery(query1);
				
					if(rs1.next() == true)
					{
						String account_date = rs1.getString("ACC_DATE");
						String balance = rs1.getString("BALANCE");
						double res = Double.parseDouble(balance) - Double.parseDouble(bal);
						String total_balance = String.valueOf(res);
						String trans_balance = String.valueOf(Double.parseDouble(bal));

						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
    						Date date = new Date();  
    						String transaction_date = formatter.format(date);

						if(Double.parseDouble(balance) >= Double.parseDouble(bal))
						{
							String query2 = "Select * from data where EMAIL = '"+rec_email+"' ";
			 				ResultSet rs2 = st.executeQuery(query2);
							if(rs2.next() == true)
							{
								String rec_ID  = rs2.getString("EMAIL");
								if(rec_ID.equals(rec_email))
	   							{
									String rec_account_date = rs2.getString("ACC_DATE");
									String rec_balance = rs2.getString("BALANCE");
		 							double result = Double.parseDouble(rec_balance) + Double.parseDouble(bal);
									String rec_total_balance = String.valueOf(result);

									String query3 = "UPDATE data SET BALANCE='"+rec_total_balance+"',TRANSACTIONS='"+transP+"',TRANS_BALANCE='"+trans_balance+"',TRANS_DATE='"+transaction_date+"',ACC_DATE='"+rec_account_date+"' " + " WHERE EMAIL = '"+rec_email+"' ";
									int rs3 = st.executeUpdate( query3 );

									String query4 = "UPDATE data SET BALANCE='"+total_balance+"',TRANSACTIONS='"+transN+"',TRANS_BALANCE='"+trans_balance+"',TRANS_DATE='"+transaction_date+"',ACC_DATE='"+account_date+"' " + " WHERE EMAIL = '"+email+"' ";
									int rs4 = st.executeUpdate( query4 );
									if(rs4 > 0)
									{
										out.println("<h1>FUNDS HAS BEEN TRANSFERED SUCCESSFULLY!!</h1>");
										out.println("</body></html>");
										flag = true;
 									}

									String query5 = "INSERT INTO history VALUES('" + email + "','" + total_balance + "','" + transN + "','" + trans_balance + "','" + transaction_date + "','" + account_date + "') ";
									int rs5 = st.executeUpdate( query5 );
							
									String query6 = "INSERT INTO history VALUES('" + rec_email + "','" + rec_total_balance + "','" + transP + "','" + trans_balance + "','" + transaction_date + "','" + rec_account_date + "') ";
									int rs6 = st.executeUpdate( query6 );
								}
								else
								{
									out.println("<h1>RECIPIENT'S CREDENTIALS NOT FOUND!!</h1>");
									out.println("</body></html>");
									flag = true;
								}
							}
							else
							{
								out.println("<h1>RECIPIENT'S CREDENTIALS NOT FOUND!!</h1>");
								out.println("</body></html>");
								flag = true;
							}
						
						}
						else
						{
							out.println("<h1>YOU DO NOT HAVE ENOUGH BALANCE!!</h1>");
							out.println("</body></html>");
							flag = true;
						}
					}
			}
			else
			{
				out.println("<h1>SENDER'S CREDENTIALS NOT FOUNDS</h1>");	
        			out.println("</body></html>");
			}
		}
		else
		{
			out.println("<h1>SENDER'S CREDENTIALS NOT FOUNDS</h1>");	
        		out.println("</body></html>");
		}
 			
		//}//while1 ends

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

           	st.close();
           	con.close();

    	}catch(Exception e)
	{
      		out.println(e);
    	}

  }

}