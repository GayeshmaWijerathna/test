package lk.ijse.api.servlet;

import lk.ijse.api.bo.BOFactory;
import lk.ijse.api.bo.custom.CustomerBO;
import lk.ijse.api.bo.custom.PurchaseOrderBO;

import javax.json.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


@WebServlet(urlPatterns = {"/pages/purchase-order"})
public class Purchase_OrderServlet extends HttpServlet {
    private PurchaseOrderBO purchaseOrderBO;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBoType(BOFactory.BoType.PurchaseOrder);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String orderID = req.getParameter("oid");

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root",
                    "root123");

            PreparedStatement pstm = connection.prepareStatement("select Orders.oid,Orders.date,Orders.customerID,OrderDetails.itemCode," +
                    "OrderDetails.qty,OrderDetails.unitPrice from Orders inner join OrderDetails on Orders.oid = " +
                    "OrderDetails.oid where Orders.oid=?");
            resp.addHeader("Content-Type","application/json");
            resp.addHeader("Access-Control-Allow-Origin","*");
            pstm.setObject(1, orderID);
                      ResultSet set = pstm.executeQuery();


            JsonArrayBuilder allOrders = Json.createArrayBuilder();

            while (set.next()){
                String oid = set.getString(1);
                String date = set.getString(2);
                String customerID = set.getString(3);
                String itemCode = set.getString(4);
                String qty = set.getString(5);
                String unitPrice = set.getString(6);

                JsonObjectBuilder orders = Json.createObjectBuilder();
                orders.add("oid",oid);
                orders.add("date",date);
                orders.add("customerID",customerID);
                orders.add("itemCode",itemCode);
                orders.add("qty",qty);
                orders.add("unitPrice",unitPrice);

                allOrders.add(orders.build());

            }

            resp.getWriter().print(allOrders.build());

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }

    //    @Override
  /*  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        resp.addHeader("Content-Type","application/json");
        resp.addHeader("Access-Control-Allow-Origin","*");

        String orderId = jsonObject.getString("oid");
        String orderDate = jsonObject.getString("date");
        String customerId = jsonObject.getString("customerID");
        String itemCode = jsonObject.getString("code");
        String qty = jsonObject.getString("qtyOnHand");
        String unitPrice = jsonObject.getString("unitPrice");

        JsonArray orderDetails = jsonObject.getJsonArray("orderDetails");

        try {
            forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "root123");

            connection.setAutoCommit(false);

            PreparedStatement orderStatement = connection.prepareStatement("INSERT INTO orders VALUES(?,?,?)");

            orderStatement.setString(1, orderId);
            orderStatement.setString(2, orderDate);
            orderStatement.setString(3, customerId);

            int affectedRows = orderStatement.executeUpdate();

            if (affectedRows == 0) {
                connection.rollback();
                throw new RuntimeException("Failed to save the order");
            } else {
                System.out.println("Order Saved");
            }


            PreparedStatement orderDetailStatement = connection.prepareStatement("INSERT INTO orderDetails VALUES(?,?,?,?)");

            orderDetailStatement.setString(1, orderId);
            orderDetailStatement.setString(2, itemCode);
            orderDetailStatement.setString(3, qty);
            orderDetailStatement.setString(4, unitPrice);

            affectedRows = orderDetailStatement.executeUpdate();
            if (affectedRows == 0) {
                connection.rollback();
                throw new RuntimeException("Failed to save the order details");
            } else {
                System.out.println("Order Details Saved");
            }

            connection.commit();

            resp.setStatus(HttpServletResponse.SC_OK);

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("message", "Successfully Purchased Order.");
            objectBuilder.add("status", resp.getStatus());
            resp.getWriter().print(objectBuilder.build());


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);

        } catch (SQLException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("message", "Failed to save the order.");
            objectBuilder.add("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().print(objectBuilder.build());
        }
    }
*/
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "PUT, DELETE");
        resp.addHeader("Access-Control-Allow-Headers", "content-type");

    }
}
