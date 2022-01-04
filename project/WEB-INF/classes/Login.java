import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;


public class Login extends HttpServlet {
  
  //Process the HTTP Get request
  	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
	response.setContentType("text/html");
    
	// get PrintWriter object
	PrintWriter out = response.getWriter();

    	String email=request.getParameter("email");
    	String pass=request.getParameter("password");

    	out.println("<html>");
    	out.println("<head><title>Log In</title></head>");
    	out.println("<body bgcolor=\"#ffffff\">");

	HttpSession session = request.getSession();
	session.setMaxInactiveInterval(2*60);


    	try{

    	Class.forName("com.mysql.jdbc.Driver");

    	String url = "jdbc:mysql://localhost/test";

    	Connection con=DriverManager.getConnection(url, "root", "root");

    	Statement st=con.createStatement();

     
    	String query="Select * from account where Email='"+email+"' and Password='"+pass+"' ";

     	System.out.println(query);

      	ResultSet rs = st.executeQuery(query);
      
      	boolean flag = false;
	if(rs.next() == true)
	{
	
 		String em = rs.getString("Email");
	   	if(em.equals(email))
	   	{	
			out.println("<script>");
         		out.println("setTimeout(function(){");
            		out.println("window.location.href = 'Main.html';");
         		out.println("}, 2000);");
      			out.println("</script><h1>LogIn Successfull!!</h1>");
			out.println("</body></html>");
			session.setAttribute(email, pass);
			flag = true;
		}
	}


	if(flag == false)
	{		
		out.println("<h1>CREDENTIAL NOT FOUND</h1>");	
        	out.println("</body></html>");
     	}

           st.close();
           con.close();

    	}catch(Exception e){

      	out.println(e);
    	}

}

}