package com.example.demo;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EmployeeRepository {

    public static void main(String[] args) {
        getConnection();

        Employee employee = new Employee();

        employee.setName("oleg");
        employee.setEmail(" ");
        employee.setCountry(" ");
        save(employee);
    }

    public static Connection getConnection() {

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "postgres";

        try {
            return DriverManager.getConnection(url, user, password);

        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return null;
    }


    public static int save(Employee employee) {
        int status = 0;
        try(Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = Objects.requireNonNull(connection).prepareStatement("insert into users(name,email,country) values (?,?,?)")
        ) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getCountry());
            status = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return status;
    }
    public static int update(Employee employee) throws SQLException {
        int status = 0;

        try (Connection connection = EmployeeRepository.getConnection();
             PreparedStatement ps = Objects.requireNonNull(connection).prepareStatement("update users set name=?, email=?, country=? where id=? and is_deleted = false")) {

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getCountry());
            ps.setInt(4, employee.getId());

            status = ps.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return status;
    }
    public static int delete(int id) {

        int status = 0;

        try(Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = Objects.requireNonNull(connection).prepareStatement("update users set is_deleted = true where id=?")) {

            ps.setInt(1, id);
            status = ps.executeUpdate();


        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return status;
    }

    public static Optional<Employee> getEmployeeById(int id) {
        Optional<Employee> optionalEmployee = Optional.empty();

        try(Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = Objects.requireNonNull(connection).prepareStatement("select * from users where id=? and is_deleted = false")
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setEmail(rs.getString(3));
                employee.setCountry(rs.getString(4));
                optionalEmployee = Optional.of(employee);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        if(optionalEmployee.isEmpty()){
            System.out.println("user is deleted");
        }
        return optionalEmployee;
    }

    public static List<Employee> getAllEmployees() {

        List<Employee> listEmployees = new ArrayList<>();

        try( Connection connection = EmployeeRepository.getConnection();
              PreparedStatement ps =Objects.requireNonNull(connection).prepareStatement("select * from users where is_deleted = false");
              ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Employee employee = new Employee();

                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setEmail(rs.getString(3));
                employee.setCountry(rs.getString(4));

                listEmployees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listEmployees;
    }
    public static String getSaveMessage(int status){

        if (status > 0) {
            return "Record saved successfully!";
        } else {
            return "Sorry! Unable to save record";
        }
}

    public static Employee createFromRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        return new Employee(false, name, email, country);
    }
}
