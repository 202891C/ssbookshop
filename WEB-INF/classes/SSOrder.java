// To save as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;            // Tomcat 9 (Java EE 8 / Jakarta EE 8)
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/ssorder")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class SSOrder extends HttpServlet {

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
         // Step 3 & 4: Execute a SQL SELECT query and Process the query result
         // Retrieve the books' id. Can order more than one books.
         
         String bookID[] = request.getParameterValues("bookID");
         String price[] = request.getParameterValues("price");
         String qty[] = request.getParameterValues("quantity");
         String cust_name[] = request.getParameterValues("cust_name");
         String cust_email[] = request.getParameterValues("cust_email");
         String cust_phone[] = request.getParameterValues("cust_phone");
         
         // out.println("<div>"+bookID[0]+"</div>");
         // out.println(bookID[0]);
         // out.println(price[0]);
         // out.println(qty[0]);
         // out.println(Integer.parseInt(qty[0])*Float.parseFloat(price[0]));
         // out.println(cust_name[0]);
         // out.println(cust_email[0]);
         // out.println(cust_phone[0]);

         if (qty !=null){
            int count;
            String sqlStr;
            String sqlStr2;
            String custID;

            //create user
            sqlStr = "insert into customer(name, email, phone_no, passkey) values ('"+ cust_name[0] + "', '" + cust_email[0] + "', " + cust_phone[0] + ", 10001);";
            out.println("<p>"+ sqlStr +"</p>");
            count = stmt.executeUpdate(sqlStr);
            out.println("<p>" + count + " record updated.</p>");

            //update qty in table 'book'
            sqlStr = "UPDATE book SET qty = qty - " + Integer.parseInt(qty[0]) + " WHERE bookID = " + Integer.parseInt(bookID[0]) + ";";
            out.println("<p>"+ sqlStr +"</p>");
            count = stmt.executeUpdate(sqlStr);
            out.println("<p>" + count + " record updated.</p>");

            //get user ID
            sqlStr = "select custID from customer where (name, email, phone_no) = ('"+ cust_name[0] + "', '" + cust_email[0]+ "', " + cust_phone[0]+ ");";
            out.println("<p>"+ sqlStr +"</p>");
            ResultSet rset = stmt.executeQuery(sqlStr);
            rset.next();
            custID = rset.getString("custID");
            // out.println("<p>"+ rset +"</p>");
            // out.println("<p>"+ rset.getString("custID") +"</p>");
            out.println("<p>" + custID + " </p>");
            rset.close();
            // out.println("<p> TEST </p>");
            //create customer_order
            sqlStr = "insert into customer_order (custID, bookID, qty, price) values (" + custID + ", " + Integer.parseInt(bookID[0]) + ", " + Integer.parseInt(qty[0])+", " + Integer.parseInt(qty[0])*Float.parseFloat(price[0]) + ");";
            //  out.println("<p> TEST2 </p>");
            out.println("<p>"+ sqlStr +"</p>");
            count = stmt.executeUpdate(sqlStr);
            out.println("<p>" + count + " record updated.</p>");
            out.println("<h3>Your order for book" + Integer.parseInt(bookID[0])
                     + " has been confirmed.</h3>");
         
            out.println("<h3>Thank you.<h3>");

         } else { // No book selected
            out.println("<h3>Please go back and select a book...</h3>");
         }
               
         //-----------------------------------------------------------------------------------------------------------------

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