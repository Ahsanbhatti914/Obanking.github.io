import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;


public class Signup extends HttpServlet 
{
  
  	//Process the HTTP Get request
  	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	response.setContentType("text/html");
    
	// get PrintWriter object
	PrintWriter out = response.getWriter();

    	String fName=request.getParameter("firstname");
    	String lName=request.getParameter("lastname");
    	String email=request.getParameter("email");
    	String password=request.getParameter("password");

	String email_id = "null";
	String Bal = "0";
	String Trans = "null";
	String Trans_Bal = "0";
	String Trans_Date = "null";
	String Account_Date = "null";

    	out.println("<html>");
    	out.println("<head><title>Response</title></head>");
    	out.println("<body bgcolor=\"#ffffff\">");


    	try{

    	Class.forName("com.mysql.jdbc.Driver");

    	String url = "jdbc:mysql://localhost/test";

    	Connection con=DriverManager.getConnection(url, "root", "root");

    	Statement st=con.createStatement();
	
	String query="Select * from account where Email='"+email+"'";
	ResultSet rs = st.executeQuery(query);
      	boolean flag = false;
	while(rs.next())
	{
		String em = rs.getString("Email");
	   	if(em.equals(email))
	   	{
      			out.println("<h1>Account Already exists with this email!!</h1>");
			out.println("</body></html>");
			flag = true;
			st.close();
           		con.close();
			break;
		}
	}
     
	if(flag == false)
	{
     		String query1 = "INSERT INTO account VALUES('" + fName + "','" + lName + "','" + email + "','" + password + "') ";

     		int rs1 = st.executeUpdate( query1 );

     		if(rs1 > 0)
		{
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
    			Date date = new Date();  
    			Account_Date = formatter.format(date); 

			String query2 = "INSERT INTO data VALUES('" + email + "','" + Bal + "','" + Trans + "','" + Trans_Bal + "','" + Trans_Date + "','" + Account_Date + "') ";
			int rs2 = st.executeUpdate( query2 );
			if(rs2 > 0)
			{
				out.println("<script>");
         			out.println("setTimeout(function(){");
        			out.println("window.location.href = 'Login.html';");
        			out.println("}, 2000);");
      				out.println("</script><h1>Account created Successfully!!</h1>");
				out.println("</body></html>");
 			}
		
		}
		else
		{	
			out.println("<h1>Sign Up not successfull.</h1>"); 		
		}
     		out.println("</body></html>");

       		st.close();
        	con.close();
	}
    	}catch(Exception e)
	{
	out.println(e);
    	}

  }

}