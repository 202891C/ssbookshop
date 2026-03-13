// To save as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;            // Tomcat 9 (Java EE 8 / Jakarta EE 8)
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/ssshop")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class  SSshop extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
      // Print an HTML page as the output of the query
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head><title>SS Eshop</title>");
      out.println("<style>img{width:100px}</style>");
      out.println("</head>");
      out.println("<link rel='stylesheet' href='css/grid.css'>");
      out.println("<body style='display: inline'>");

     

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/ssbookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {
         // Step 3: Execute a SQL SELECT query
         // === Form the SQL command - BEGIN ===

         String login = null;
         String pw = null;
         String search = null;
         if(request.getParameterValues("email") !=null){
            String[] email = request.getParameterValues("email");
            String[] password = request.getParameterValues("password");

               if (email[0]!=null && password[0] !=null){
               String sqlStr = "select email, pass from customer, pw WHERE pass='"+ password[0] + 
               "' and email='" + email[0] + 
                  "' and customer.passkey = pw.passkey; ";
                  // out.println("<p>"+ sqlStr + "</p>");
                  ResultSet rset = stmt.executeQuery(sqlStr);
                  // rset.next();
                  // out.println("<p>"+ rset.getString("email") + "</p>");
                  while(rset.next()){
                     login = rset.getString("email");
                     pw = rset.getString("pass");
                     // out.println("<p>"+login+"</p>");
                     // out.println("<p>User Mode</p>");
                     if (rset.getString("email") == null){
                     out.println("<p>Error: Invalid Email or Password.</p>");
                     out.println("<a href='main.html'>Return to login</a>");
                     out.println("</body></html>");
                     out.close();
                     }
                  }  
               }
         }
         else{

         }
         out.println("<div id='navbar' style='display: flex; flex: 1 1 100%; height: 50px; background-color: rgb(190, 217, 243);'>");
         out.println("<div id='searchbar' style='padding: 12.5px; display: inline-block;'>");
         out.println("<form method='post' action='ssshop' style='display: flex; flex: 1 1 0%;'>");
         out.println("<input type='text' name='search' placeholder='' required/>");
         out.println("<input type='hidden' name='email' value='" + login + "'/>");
         out.println("<input type='hidden' name='password' value='" + pw + "'/>");
         out.println("<input type='submit' value='Search'/>");
         out.println("</form></div>");

         out.println("</div>");
         out.println("<br>");
         // out.println("<p>"+ email[0] + "</p>");
         // out.println("<p>"+ password[0] + "</p>");
         
         // out.println("<p>"+ login + "</p>");
         String sqlStr;
         if(request.getParameterValues("search") !=null){
            String[] query = request.getParameterValues("search");
            search = query[0];
            sqlStr = "SELECT * FROM book WHERE title Like '%" + search + "%' and qty > 0 ORDER BY title ASC";
         }
         else{
            sqlStr = "SELECT * FROM book WHERE qty > 0 ORDER BY title ASC";
         }
         
         // === Form the SQL command - END ===

         // out.println("<h3>Thank you for your query.</h3>");
         // out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server

         // Step 4: Process the query result set
         /*int count = 0;
         out.println("<div id='results'>");
         while(rset.next()) { 
            // Print a paragraph <p>...</p> for each record
            /*?*//*if(rset.getString("image") != null){
               out.println("<div><a href='ssbook?bookID=" + rset.getString("bookID")+ "'><img src='/ssbookshop/" + rset.getString("image")
                  + "'>, " + rset.getString("title")
                  + ", $" + rset.getDouble("price") + "</a></div>");

            }
            else{
               if(login==null){
                  out.println("<div><a href='ssbook?bookID=" + rset.getString("bookID") + "'>"
                  + rset.getString("title")
                  + ", $" + rset.getDouble("price") + "</div>");
               }
               else{
                  
                  out.println("<div><a href='ssbook?bookID=" + rset.getString("bookID") + 
                  "&login=" + login + "'>"
                  + rset.getString("title")
                  + ", $" + rset.getDouble("price") + "</div>");
               }
               
            }
           count++;
         }*/
        int count = 0;
out.println("<table class='book-table'>");

while (rset.next()) {
    if (count % 2 == 0) {
        out.println("<tr>");
    }

    out.println("<td class='book-cell'>");

    if (rset.getString("image") != null) {
      if(login!=null){
         out.println("<a href='ssbook?bookID=" + rset.getString("bookID") + 
                  "&login=" + login + "'>");
      }
      else{
         out.println("<a href='ssbook?bookID=" + rset.getString("bookID")+ "'>");
      }
        out.println("<img class='book-image' src='/ssbookshop/" + rset.getString("image") + "' alt='Book cover'>");
        out.println("<div class='book-title'>" + rset.getString("title") + "</div>");
        out.println("<div class='book-price'>$" + rset.getDouble("price") + "</div>");
        out.println("</a>");
    } else {
      if(login!=null){
         out.println("<a href='ssbook?bookID=" + rset.getString("bookID") + 
                  "&login=" + login + "'>");
      }
      else{
         out.println("<a href='ssbook?bookID=" + rset.getString("bookID")+ "'>");
      }
        out.println("<div class='book-title'>" + rset.getString("title") + "</div>");
        out.println("<div class='book-price'>$" + rset.getDouble("price") + "</div>");
        out.println("</a>");
    }

    out.println("</td>");

    count++;

    if (count % 2 == 0) {
        out.println("</tr>");
    }
}

if (count % 2 != 0) {
    out.println("</tr>");
}

out.println("</table>");
out.println("* * * * * * * Welcome to SS BOOKSHOP * * * * * * *");

         out.println("</div>");
         out.println("<br>");
         out.println("<p>==== " + count + " records found =====</p>");
         // === Step 4 ends HERE - Do NOT delete the following codes ===
      } catch(SQLException ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
      out.println("</body></html>");
      out.close();
   }
   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
      doGet(request, response);  // Re-direct POST request to doGet()
   }
}
