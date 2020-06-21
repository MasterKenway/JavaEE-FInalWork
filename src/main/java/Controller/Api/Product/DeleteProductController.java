package main.java.Controller.Api.Product;

import main.java.Database.ProductDataController;
import main.java.Model.Product;
import main.java.Model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteProductController extends HttpServlet {
    private static final String[] Message = {"Delete Successfully", "Without Permission"};
    private final Logger logger = LogManager.getLogger(DeleteProductController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {
            try {
                if ("staff".equals(currentUser.getPosition())) {
                    req.setAttribute("Code", 1);
                    req.setAttribute("Message", Message[1]);
                } else {
                    ProductDataController productDataController = new ProductDataController();
                    productDataController.delete(Integer.parseInt(req.getParameter("productID")));

                    req.setAttribute("Code", 0);
                    req.setAttribute("Message", Message[0]);
                }
            } catch (SQLException sqlException) {
                logger.error(sqlException.getMessage());
            } catch (NamingException namingException) {
                logger.error(namingException.getMessage());
            }

        } else {
            req.setAttribute("Code", 1);
            req.setAttribute("Message", Message[1]);
        }

    }
}
