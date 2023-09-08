package lk.ijse.api;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = {"/pages/item"})
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "root123");
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.addHeader("Content-Type", "application/json");

            PreparedStatement pstm = connection.prepareStatement("select * from Item");
            ResultSet rst = pstm.executeQuery();

            while (rst.next()){

                String code = rst.getString(1);
                String description = rst.getString(2);
                String qty = rst.getString(3);
                String unitPrice = rst.getString(4);

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("code",code);
                objectBuilder.add("description",description);
                objectBuilder.add("qtyOnHand",qty);
                objectBuilder.add("unitPrice",unitPrice);

                arrayBuilder.add(objectBuilder.build());

            }
            resp.getWriter().print(arrayBuilder.build());

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String description = req.getParameter("description");
        String qtyOnHand = req.getParameter("qtyOnHand");
        String unitPrice = req.getParameter("unitPrice");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "root123");

            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.addHeader("Content-Type", "application/json");

            PreparedStatement pstm = connection.prepareStatement("insert into Item values(?,?,?,?)");
         pstm.setObject(1,code);
         pstm.setObject(2,description);
         pstm.setObject(3,qtyOnHand);
         pstm.setObject(4,unitPrice);

          if (pstm.executeUpdate() > 0){
              JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
              objectBuilder.add("state","Ok");
              objectBuilder.add("message", "Item Added..!");
              objectBuilder.add("Data", " ");

              resp.getWriter().print(objectBuilder.build());
          }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String code = jsonObject.getString("code");
        String description = jsonObject.getString("description");
        String qtyOnHand = jsonObject.getString("qtyOnHand");
        String unitPrice = jsonObject.getString("unitPrice");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "root123");
            PreparedStatement pstm = connection.prepareStatement("update Item set description=?, qtyOnHand=?, unitPrice=? where code=?");
            pstm.setObject(4,code);
            pstm.setObject(1,description);
            pstm.setObject(2,qtyOnHand);
            pstm.setObject(3,unitPrice);

            pstm.executeUpdate();


            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("state","Ok");
            objectBuilder.add("message","Item Successfully Updated..!");
            objectBuilder.add("Data"," ");
            resp.getWriter().print(objectBuilder.build());


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "root123");

            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.addHeader("Content-Type", "application/json");

            PreparedStatement pstm = connection.prepareStatement("delete from Item where code=?");

            pstm.setObject(1,code);

            if (pstm.executeUpdate() > 0){
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                objectBuilder.add("state","Ok");
                objectBuilder.add("message","Item Deleted Successfully");
                objectBuilder.add("Data"," ");

                resp.getWriter().print(objectBuilder.build());
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

            JsonObjectBuilder response = Json.createObjectBuilder();

            response.add("state","Error");
            response.add("message",e.getMessage());
            response.add("Data", " ");
            resp.setStatus(400);
            resp.getWriter().print(response.build());
        }

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "PUT, DELETE");
        resp.addHeader("Access-Control-Allow-Headers", "content-type");
    }
}
