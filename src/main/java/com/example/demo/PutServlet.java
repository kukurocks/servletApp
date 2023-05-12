package com.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/putServlet")
public class PutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);
        Employee employee = EmployeeRepository.createFromRequest(request);
        employee.setId(id);


        int status = 0;
        try {
            status = EmployeeRepository.update(employee);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (status > 0) {
            response.sendRedirect("viewServlet");
        } else {
            out.println ("Sorry! unable to update record");
        }


        out.close();
    }
}
