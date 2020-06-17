package main.java.Controller.Api.SaleRecord;


import main.java.Database.SaleRecordDataController;
import main.java.Model.SaleRecord;
import main.java.Model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class ModifySaleRecordController extends HttpServlet {

    private final String[] ModifySaleRecordMessage = {"Modify SaleRecord Successfully", "SaleRecord Does not Exist"};
    private final Logger logger = LogManager.getLogger(ModifySaleRecordController.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");

        SaleRecord saleRecord = new SaleRecord(Integer.parseInt(request.getParameter("saleRecordID")),
                Integer.parseInt(request.getParameter("totalPrice")), request.getParameter("date"));

        try {
            if (user != null) {
                SaleRecordDataController.modifySaleRecord(saleRecord);
                request.setAttribute("Code", 0);
                request.setAttribute("Message", ModifySaleRecordMessage[0]);
            }
        } catch (SQLException sqlException) {
            if (sqlException.getErrorCode() == 1054) {
                // SaleRecord Does not Exist
                logger.error("SaleRecord Does not Exist !");
                session.setAttribute("Code", 1);
                session.setAttribute("Message", ModifySaleRecordMessage[1]);
                // Other Operations
            }
            logger.error(sqlException.getMessage());
        }
    }
}