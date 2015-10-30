package Gradebook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StudentType
 */
@WebServlet("/StudentType")
public class StudentType extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentType() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type= request.getParameter("type");
		String Studentid= request.getParameter("id");
		
		String url = "jdbc:oracle:thin:system/password@localhost"; 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//properties for creating connection to Oracle database 
		Properties props = new Properties(); 
		props.setProperty("user", "TestUserDB"); 
		props.setProperty("password", "password"); 
		String sql="",sql2="";
		try {
 //creating connection to Oracle database using JDBC 
		Connection conn = DriverManager.getConnection(url,props); 
		if (Studentid.equals("0")){
			sql ="select studentid, assignment,a_date,grade from gradeBook where a_type= '"+type+"'";
			sql2="select max(grade),min(grade),SUM(grade)/count(*) from gradebook where a_type='"+type+"'";			
			
		}
		else{
			sql ="select assignment,a_date,grade from gradeBook where a_type='"+type+"' AND studentid="+Studentid;
			sql2="select SUM(grade)/count(*) from gradebook where a_type='"+type+"' AND studentid="+Studentid;		
		}
 //creating PreparedStatement object to execute query 
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		ResultSet result = null;
		ResultSet result2 = null;

		out.println("<head><title>" + "Gradebook" + "</title>");	
	    out.println( "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\" integrity=\"sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==\" crossorigin=\"anonymous\">" + "</head>");
		PreparedStatement preStatement = conn.prepareStatement(sql); 
		PreparedStatement preStatement2= conn.prepareStatement(sql2);

		result = preStatement.executeQuery();
		result2 = preStatement2.executeQuery();
		if (Studentid.equals("0")){
			out.println("<table class=\"table table-striped table table-bordered table table-hover\"><tr><th>"+ "Student ID" + "</th><th>"+"Assignment" + "</th><th>"+"Date" + "</th><th>"+"Grade" + "</th></tr>");
		}
		else out.println("<table class=\"table table-striped table table-bordered table table-hover\"><tr><th>"+ "Assignments for Student ID: " +Studentid+ " for "+type+" type</th></tr>");
	    	while(result.next()){
	    		String assignment=result.getString("assignment");	
	    		String date=result.getString("a_date");
	    		double grade=result.getDouble("grade");
	    		if(Studentid.equals("0")){
	    			String s_id=result.getString("studentid");
		    		out.println("<tr><td>"+ s_id + "</td><td>"+ assignment + "</td><td>"+ date + "</td><td>"+ grade + "</td></tr>");
			    	
	    		}
	    		else {out.println("<tr><td>"+ assignment + "</td><td>"+ date + "</td><td>"+ grade + "</td></tr>");
		    		 }
		
	    	}
	    	if(Studentid.equals("0")){
	    		result2.next();
	    		out.println("<tr><td><h3>Average for "+type+" type: "+result2.getDouble("SUM(grade)/count(*)")+"</h3></td></tr>");
	    		out.println("<tr><td><h3>Highest "+type+" grade: "+result2.getDouble("max(grade)")+", Lowest "+type+" grade: "+result2.getDouble("min(grade)")+"</h3></td></tr>");
	    	}
	    	else{
	    		result2.next();
   		     out.println("<tr><td><h3>Average for Student ID: "+Studentid+" and "+type+" type: "+result2.getDouble("SUM(grade)/count(*)")+"</h3></td></tr>");
	    	}
	    		out.println("</table>");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
