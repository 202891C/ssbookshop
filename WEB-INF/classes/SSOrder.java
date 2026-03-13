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
      out.println("<head><title>Query Response</title>");
      out.println("<style> table{ border: 1px solid black;}th, td{padding: 5px;box-shadow: 0px 0px 5px black;} img{width:100px}</style></head>");
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

            //get user ID
            sqlStr = "select custID from customer where (name, email, phone_no) = ('"+ cust_name[0] + "', '" + cust_email[0]+ "', " + cust_phone[0]+ ");";
            // out.println("<p>"+ sqlStr +"</p>");
            ResultSet rset = stmt.executeQuery(sqlStr);
            rset.next();

               if(rset.getString("custID")==null){
               //create user
               sqlStr = "insert into customer(name, email, phone_no, passkey) values ('"+ cust_name[0] + "', '" + cust_email[0] + "', " + cust_phone[0] + ", 10001);";
               // out.println("<p>"+ sqlStr +"</p>");
               count = stmt.executeUpdate(sqlStr);
               // out.println("<p>" + count + " record updated.</p>");
               
               sqlStr = "select custID from customer where (name, email, phone_no) = ('"+ cust_name[0] + "', '" + cust_email[0]+ "', " + cust_phone[0]+ ");";
               // out.println("<p>"+ sqlStr +"</p>");
               ResultSet rs = stmt.executeQuery(sqlStr);
               rs.next();
               custID = rs.getString("custID");
               }
               else{
                  custID = rset.getString("custID");
               }
            // out.println("<p>"+ rset +"</p>");
            // out.println("<p>"+ rset.getString("custID") +"</p>");
            // out.println("<p>" + custID + " </p>");
            rset.close();


            //update qty in table 'book'
            sqlStr = "UPDATE book SET qty = qty - " + Integer.parseInt(qty[0]) + " WHERE bookID = " + Integer.parseInt(bookID[0]) + ";";
            // out.println("<p>"+ sqlStr +"</p>");
            count = stmt.executeUpdate(sqlStr);
            // out.println("<p>" + count + " record updated.</p>");

            
            // out.println("<p> TEST </p>");

            //create customer_order
            sqlStr = "insert into customer_order (custID, bookID, qty, price) values (" + custID + ", " + Integer.parseInt(bookID[0]) + ", " + Integer.parseInt(qty[0])+", " + Integer.parseInt(qty[0])*Float.parseFloat(price[0]) + ");";
            //  out.println("<p> TEST2 </p>");
            // out.println("<p>"+ sqlStr +"</p>");
            count = stmt.executeUpdate(sqlStr);
            // out.println("<p>" + count + " record updated.</p>");
            // out.println("<h3>Your order for book" + Integer.parseInt(bookID[0])
            //          + " has been confirmed.</h3>");
         
            // out.println("<h3>Thank you.<h3>");

            // View customer orders
            out.println("<h2>Your orders</h2>");
            sqlStr = "select customer_order.bookID, email, name, title, customer_order.price, customer_order.qty, image from customer_order, customer, book WHERE customer.custID = customer_order.custID AND book.bookID = customer_order.bookID AND customer_order.custID =" + custID + ";";

            ResultSet resset = stmt.executeQuery(sqlStr);
            
            out.println("<table><tr>");
            out.print("<th></th> <th>Title</th> <th>Quantity</th> <th>Cost</th> <th>Buyer</th> </tr>");
            while(resset.next()) {
               out.println("<tr>");
               out.println("<td><img src='/ssbookshop/" + resset.getString("image")+ "'></td>");
               out.print("<td> "+ resset.getString("title") + "</td>");
               out.print("<td> "+ resset.getString("qty") + "</td>");
               out.print("<td> $"+ resset.getString("price")+"</td>");
               out.print("<td>");
               out.print(resset.getString("name") + "<br>");
               out.print("</td>");  
               out.print("</tr>");
            }
            
         // count++;

         out.println("</table>");

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
   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
                   throws ServletException, IOException {
      doGet(request, response);  // Re-direct POST request to doGet()
   }
}