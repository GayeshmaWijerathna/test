package lk.ijse.api.servlet;

import lk.ijse.api.bo.BOFactory;
import lk.ijse.api.bo.custom.CustomerBO;
import lk.ijse.api.dto.CustomerDTO;
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

@WebServlet(urlPatterns = {"/pages/customer"})
public class CustomerServlet extends HttpServlet {

    private CustomerBO customerBO;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        customerBO = (CustomerBO) BOFactory.getBoFactory().getBoType(BOFactory.BoType.Customer);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        ServletContext servletContext = getServletContext();
//        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");
        try {

            List<CustomerDTO> customerDTOS = customerBO.getAllCustomer();

//            PreparedStatement pstm = connection.prepareStatement("select  * from Customer");
//            ResultSet rst = pstm.executeQuery();
            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.addHeader("Content-Type", "application/json");

            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
//            while (rst.next()){
//                String id = rst.getString(1);
//                String name = rst.getString(2);
//                String address = rst.getString(3);
            for (CustomerDTO cus : customerDTOS) {

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                objectBuilder.add("id", cus.getId());
                objectBuilder.add("name", cus.getName());
                objectBuilder.add("address", cus.getAddress());
                arrayBuilder.add(objectBuilder.build());
            }
            resp.setContentType("application/json");
            resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded", arrayBuilder.build()));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");

//        ServletContext servletContext = getServletContext();
//        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");

        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "root123");

//            PreparedStatement pstm = connection.prepareStatement("insert into Customer values(?,?,?)");
//            pstm.setObject(1,id);
//            pstm.setObject(2,name);
//            pstm.setObject(3,address);

            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String address = req.getParameter("address");

            if (customerBO.saveCustomer(new CustomerDTO(id, name, address))) {
//                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//
//                objectBuilder.add("state", "Ok");
//                objectBuilder.add("message", "Successfully added!!");
//                objectBuilder.add("data", " ");

//                resp.getWriter().print(objectBuilder.build());
                resp.getWriter().print(ResponseUtil.genJson("Success", "Successfully Saved..!"));
            } else {
                resp.getWriter().print(ResponseUtil.genJson("Error", "Save Failed..!"));

            }


        } catch (SQLException e) {
            e.printStackTrace();
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject customerObject = reader.readObject();


        String id = customerObject.getString("id");
        String name = customerObject.getString("name");
        String address = customerObject.getString("address");
        CustomerDTO customerDTO = new CustomerDTO(id, name, address);

        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");

//        ServletContext servletContext = getServletContext();
//        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");


        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "root123");
//            PreparedStatement pstm = connection.prepareStatement("update Customer set name=?, address=? where id=?");
//            pstm.setObject(3, id);
//            pstm.setObject(1, name);
//            pstm.setObject(2, address);
//
//            pstm.executeUpdate();
//
//            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//            objectBuilder.add("state", "Ok");
//            objectBuilder.add("message", "Successfully Updated...!");
//            objectBuilder.add("Data", " ");
//            resp.getWriter().print(objectBuilder.build());

            if (customerBO.updateCustomer(customerDTO)) {
                resp.getWriter().print(ResponseUtil.genJson("Success", "Updated Successfully!"));
            } else {
                resp.getWriter().print(ResponseUtil.genJson("Error", "Update Failed!"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        resp.setContentType("application/json");

        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");


        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "root123");


//            PreparedStatement pstm = connection.prepareStatement("delete from Customer where id=?");
//
//            pstm.setObject(1, id);

            if (customerBO.deleteCustomer(id)) {
//                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//
//                objectBuilder.add("state", "Ok");
//                objectBuilder.add("message", "Deleted Successfully");
//                objectBuilder.add("Data", " ");

                resp.getWriter().print(ResponseUtil.genJson("Success", "Successfully Deleted!"));
            } else {
                resp.getWriter().print(ResponseUtil.genJson("Error", "Delete Failed!"));
            }


        } catch (SQLException | ClassNotFoundException e) {
            resp.setStatus(500);
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
