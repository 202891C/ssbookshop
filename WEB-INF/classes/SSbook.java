// To save as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;            // Tomcat 9 (Java EE 8 / Jakarta EE 8)
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/ssbook")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class  SSbook extends HttpServlet {

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
      out.println("<head><title>Query Response</title></head>");
      out.println("<body>");

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
         String bookID[] = request.getParameterValues("bookID");
         if (bookID == null) {
            out.println("<h2>No book selected. Please go back to select book(s)</h2><body></html>");
            return; // Exit doGet()
         } 
         String sqlStr = "SELECT * FROM book, book_author, author WHERE book.bookID = ";
         sqlStr += bookID[0]; 
         sqlStr += " AND book.bookID = book_author.bookID AND author.authorID = book_author.authorID;";

         // === Form the SQL command - END ===

         out.println("<head><style> table{ border: 1px solid black;}th, td{padding: 5px;box-shadow: 0px 0px 5px black;} img{width:100px}</style></head>");

         out.println("<h3>Thank you for your query.</h3>");
         out.println("<p>Your SQL statement is: " + sqlStr + "</p>"); // Echo for debugging
         ResultSet rset = stmt.executeQuery(sqlStr);  // Send the query to the server

         out.println("<form method='get' action='ssorder'>");
         out.println("<table><tr>");
         out.print("<th></th> <th>Title</th> <th>Price</th> <th>Quantity</th> <th>Author</th> </tr>");
         // Step 4: Process the query result set
         // int count = 0;
         rset.next();
         out.println("<tr>");
         out.println("<td><img src='/ssbookshop/" + rset.getString("image")+ "'>");
         out.println("<input type='hidden' name='bookID' value="+ rset.getString("bookID") +" ></td>");
         out.print("<td>"+ rset.getString("title") + "</td>");
         out.print("<td> $"+ rset.getString("price"));
         out.println("<input type='hidden' name='price' value="+ rset.getString("price") +" ></td>");
         out.print("<td> <input type='number' id='quantity' name='quantity' min='1' max='"+ rset.getString("qty") + "' value='1'></td>");
         out.print("<td>");
         out.print(rset.getString("name") + "<br>");
         while(rset.next()) {
            // Print a paragraph <p>...</p> for each record
            out.print(rset.getString("name") + "<br>");  
         }
         out.print("</td>");  
         out.print("</tr>");
         // count++;

         out.println("</table>");
         out.println("<p>Enter your Name: <input type='text' name='cust_name' /></p>");
         out.println("<p>Enter your Email: <input type='text' name='cust_email' /></p>");
         out.println("<p>Enter your Phone Number: <input type='text' name='cust_phone' /></p>");
         
 
         // Print the submit button and </form> end-tag
         out.println("<p><input type='submit' value='ORDER'/>");
         out.println("</form>");
         


         // out.println("<p>==== " + count + " records found =====</p>");
         // === Step 4 ends HERE - Do NOT delete the following codes ===
      } catch(SQLException ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
      out.println("</body></html>");
      out.close();
   }
}
