package lk.ijse.api.servlet;

import lk.ijse.api.bo.BOFactory;
import lk.ijse.api.bo.custom.ItemBO;
import lk.ijse.api.dto.ItemDTO;
import lk.ijse.api.util.ResponseUtil;

import javax.json.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.List;

@WebServlet(urlPatterns = {"/pages/item"})
public class ItemServlet extends HttpServlet {
    private ItemBO itemBO;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        itemBO = (ItemBO) BOFactory.getBoFactory().getBoType(BOFactory.BoType.Item);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "root123");
            List<ItemDTO> allItems = itemBO.getAllItems();
            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.addHeader("Content-Type", "application/json");

            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();


//            PreparedStatement pstm = connection.prepareStatement("select * from Item");
//            ResultSet rst = pstm.executeQuery();

//            while (rst.next()){
            for (ItemDTO itm : allItems) {

//                String code = rst.getString(1);
//                String description = rst.getString(2);
//                String qty = rst.getString(3);
//                String unitPrice = rst.getString(4);

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("code", itm.getCode());
                objectBuilder.add("description", itm.getDescription());
                objectBuilder.add("qtyOnHand", itm.getQtyOnHand());
                objectBuilder.add("unitPrice", itm.getUnitPrice());

                arrayBuilder.add(objectBuilder.build());

            }
            resp.setContentType("application/json");
            resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded!", arrayBuilder.build()));

        } catch (ClassNotFoundException | SQLException e) {

            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");

        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "root123");

            String code = req.getParameter("code");
            String description = req.getParameter("description");
            String qtyOnHand = req.getParameter("qtyOnHand");
            String unitPrice = req.getParameter("unitPrice");

//            PreparedStatement pstm = connection.prepareStatement("insert into Item values(?,?,?,?)");
//         pstm.setObject(1,code);
//         pstm.setObject(2,description);
//         pstm.setObject(3,qtyOnHand);
//         pstm.setObject(4,unitPrice);

            if (itemBO.saveItem(new ItemDTO(code, description, qtyOnHand, unitPrice))) {
//              JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//              objectBuilder.add("state","Ok");
//              objectBuilder.add("message", "Item Added..!");
//              objectBuilder.add("Data", " ");

                resp.getWriter().print(ResponseUtil.genJson("Success", "Item Saved Successfully!"));
            } else {
                resp.getWriter().print(ResponseUtil.genJson("Error", "Save Failed!"));

            }


        } catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String code = jsonObject.getString("code");
        String description = jsonObject.getString("description");
        String qtyOnHand = jsonObject.getString("qtyOnHand");
        String unitPrice = jsonObject.getString("unitPrice");
        ItemDTO itemDTO = new ItemDTO(code, description, qtyOnHand, unitPrice);

        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");


        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "root123");
//            PreparedStatement pstm = connection.prepareStatement("update Item set description=?, qtyOnHand=?, unitPrice=? where code=?");
//            pstm.setObject(4,code);
//            pstm.setObject(1,description);
//            pstm.setObject(2,qtyOnHand);
//            pstm.setObject(3,unitPrice);
//
//            pstm.executeUpdate();

//            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//            objectBuilder.add("state","Ok");
//            objectBuilder.add("message","Item Successfully Updated..!");
//            objectBuilder.add("Data"," ");
//            resp.getWriter().print(objectBuilder.build());

            if (itemBO.updateItem(itemDTO)) {
                resp.getWriter().print(ResponseUtil.genJson("Success", "Successfully Updated !"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "root123");

            resp.setContentType("application/json");
            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.addHeader("Content-Type", "application/json");

//            PreparedStatement pstm = connection.prepareStatement("delete from Item where code=?");
//
//            pstm.setObject(1,code);

            if (itemBO.deleteItem(code)) {
//                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//
//                objectBuilder.add("state","Ok");
//                objectBuilder.add("message","Item Deleted Successfully");
//                objectBuilder.add("Data"," ");

                resp.getWriter().print(ResponseUtil.genJson("Success", "Successfully Deleted..!"));
            }


        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();

//            JsonObjectBuilder response = Json.createObjectBuilder();
//
//            response.add("state","Error");
//            response.add("message",e.getMessage());
//            response.add("Data", " ");
            resp.setStatus(400);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "PUT, DELETE");
        resp.addHeader("Access-Control-Allow-Headers", "content-type");
    }
}
