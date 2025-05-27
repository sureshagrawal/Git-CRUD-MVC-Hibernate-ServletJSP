package com.nsgacademy.crudmvc.web;

import com.nsgacademy.crudmvc.dao.StudentDAO;
import com.nsgacademy.crudmvc.exception.DAOException;
import com.nsgacademy.crudmvc.model.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertStudent(request, response);
                    break;
                case "/delete":
                    deleteStudent(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateStudent(request, response);
                    break;
                default:
                    listStudents(request, response);
                    break;
            }
        } catch (DAOException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DAOException {
        List<Student> students = studentDAO.selectAllStudents();
        request.setAttribute("listStudents", students);
        request.getRequestDispatcher("student-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("student-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DAOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentDAO.selectStudent(id);
        request.setAttribute("student", student);
        request.getRequestDispatcher("student-form.jsp").forward(request, response);
    }

    private void insertStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException, DAOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        if (validateStudent(name, email, mobile)) {
            studentDAO.insertStudent(new Student(name, email, mobile));
            response.sendRedirect("list?success=Added Successfully");
        } else {
            response.sendRedirect("new?error=Validation Failed");
        }
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException, DAOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        if (validateStudent(name, email, mobile)) {
            studentDAO.updateStudent(new Student(id, name, email, mobile));
            response.sendRedirect("list?success=Updated Successfully");
        } else {
            response.sendRedirect("edit?id=" + id + "&error=Validation Failed");
        }
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException, DAOException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.deleteStudent(id);
        response.sendRedirect("list?success=Deleted Successfully");
    }

    private boolean validateStudent(String name, String email, String mobile) {
        return name != null && !name.trim().isEmpty()
                && email != null && email.contains("@")
                && mobile != null && mobile.matches("\\d{10}");
    }
}
