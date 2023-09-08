package lk.ijse.api;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = {"/pages/customer"})
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "root123");
            PreparedStatement pstm = connection.prepareStatement("select  * from Customer");
            ResultSet rst = pstm.executeQuery();

            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.addHeader("Content-Type", "application/json");

            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            while (rst.next()){
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                objectBuilder.add("id",id);
                objectBuilder.add("name",name);
                objectBuilder.add("address",address);

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
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "root123");

            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.addHeader("Content-Type", "application/json");

            PreparedStatement pstm = connection.prepareStatement("insert into Customer values(?,?,?)");
            pstm.setObject(1,id);
            pstm.setObject(2,name);
            pstm.setObject(3,address);

            if (pstm.executeUpdate() > 0){
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                objectBuilder.add("state","Ok");
                objectBuilder.add("message","Successfully added!!");
                objectBuilder.add("data"," ");

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
        JsonObject customerObject = reader.readObject();


        String id = customerObject.getString("id");
        String name = customerObject.getString("name");
        String address = customerObject.getString("address");


        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "root123");
            PreparedStatement pstm = connection.prepareStatement("update Customer set name=?, address=? where id=?");
            pstm.setObject(3,id);
            pstm.setObject(1,name);
            pstm.setObject(2,address);

            pstm.executeUpdate();

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("state","Ok");
            objectBuilder.add("message","Successfully Updated...!");
            objectBuilder.add("Data", " ");
            resp.getWriter().print(objectBuilder.build());


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "root123");

            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.addHeader("Content-Type", "application/json");

            PreparedStatement pstm = connection.prepareStatement("delete from Customer where id=?");

            pstm.setObject(1,id);

             if (pstm.executeUpdate() > 0){
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                objectBuilder.add("state","Ok");
                objectBuilder.add("message","Deleted Successfully");
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
