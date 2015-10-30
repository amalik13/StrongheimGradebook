package Gradebook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Gradebook
 */
@WebServlet("/strongGradebook")
public class strongGradebook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public strongGradebook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		ResultSet result = null;

		out.println("<head><title>" + "Gradebook" + "</title>");	
	    out.println( "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\" integrity=\"sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==\" crossorigin=\"anonymous\">" + "</head>");	
	    try {
	    	double j= Double.parseDouble(request.getParameter("grade"));
	    	String S_ID= request.getParameter("studentid");
	    	String type= request.getParameter("type");
	    	String S_date= request.getParameter("date");
	    	
	    	
	    	int l=0;
	    	double sum=0;
	    	  updateInfo(S_ID,request.getParameter("assignment"),type,S_date,j);
	    	  result = retrieveInfo(); 
	    	  out.println("<table class=\"table table-striped table table-bordered table table-hover\"><tr><th>"+ "Student ID" + "</th><th>"+"Assignment" + "</th><th>"+"Type" + "</th><th>"+"Date" + "</th><th>"+"Grade" + "</th></tr>");
	    	while(result.next()){
	    		String assignment=result.getString("assignment");
	    		double grade=result.getDouble("grade");
	    		String stID=result.getString("studentid"),sType=result.getString("a_type"),sDate=result.getString("a_date");
			out.println("<tr><td><a href=\"http://localhost:8080/StrongheimGradebook/StudentAssignment?id="+stID+"\">"+ stID +"</a></td><td>"+ assignment + "</td><td><a href=\"http://localhost:8080/StrongheimGradebook/StudentType?id=0&type="+sType+"\">"+ sType +"</a></td><td>"+ sDate +"</td><td>"+grade + "</td></tr>");
		//	out.println("<tr><td>"+ S_ID + "</td><td>"+grade + "</td></tr>");
			sum+=grade;
			l++;
	    	}
	    	out.println("</table>");
	    	double avg=sum/l;
	    	DecimalFormat df = new DecimalFormat("#.##");
	    	avg=Double.parseDouble(df.format(avg));
	    	out.println("<br><a href='http://localhost:8080/GradebookWebApp/Average?avg="+avg+"'>Show Average</a>");
	    	out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script><script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\" integrity=\"sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==\" crossorigin=\"anonymous\"></script></body>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	public static ResultSet retrieveInfo() throws ClassNotFoundException 
	{ 
		try 
	{ 
	//URL of Oracle database server 
			String url = "jdbc:oracle:thin:system/password@localhost"; 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//properties for creating connection to Oracle database 
			Properties props = new Properties(); 
			props.setProperty("user", "TestUserDB"); 
			props.setProperty("password", "password"); 
	 
	 //creating connection to Oracle database using JDBC 
			Connection conn = DriverManager.getConnection(url,props); 
	 
			String sql ="select studentid,assignment, a_type, a_date, grade from gradeBook";
						
			
	 //creating PreparedStatement object to execute query 
			PreparedStatement preStatement = conn.prepareStatement(sql); 
	 
			ResultSet result = preStatement.executeQuery();
			return result;
		 
		
		}catch(SQLException e) 
		{ 
			
			System.out.println(e.getMessage()); 
			
			e.printStackTrace();
			return null;
		}}
	public static void updateInfo(String studentid, String assignment,String a_type, String a_date, Double grade) throws ClassNotFoundException 
	{ 
		try 
	{ 
	//URL of Oracle database server 
			String url = "jdbc:oracle:thin:system/password@localhost"; 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//properties for creating connection to Oracle database 
			Properties props = new Properties(); 
			props.setProperty("user", "TestUserDB"); 
			props.setProperty("password", "password"); 
	 
	 //creating connection to Oracle database using JDBC 
			Connection conn = DriverManager.getConnection(url,props); 
	 
			String sql ="insert into gradeBook (studentid,assignment,a_type,a_date, grade) Values ('"+studentid+"','"+assignment+"', '"+a_type+"','"+a_date+"',"+grade+")";
	 
	 //creating PreparedStatement object to execute query 
			PreparedStatement preStatement = conn.prepareStatement(sql); 
	 
			ResultSet result = preStatement.executeQuery(); 
			
	
		}catch(SQLException e) 
		{ 
			System.out.println(e.getMessage()); 
			e.printStackTrace(); 
		}}

}
