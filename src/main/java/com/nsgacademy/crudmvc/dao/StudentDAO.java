package com.nsgacademy.crudmvc.dao;

import com.nsgacademy.crudmvc.model.Student;
import com.nsgacademy.crudmvc.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentDAO {

    // session will be auto-closed as i have used try with resource in each method
    public void insertStudent(Student student) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(student); // persist instead of save
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.err.println("Insert Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Student selectStudent(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Student.class, id); // find instead of get
        } catch (HibernateException e) {
            System.err.println("Select Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Student> selectAllStudents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Student", Student.class).list();
        } catch (HibernateException e) {
            System.err.println("SelectAll Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateStudent(Student student) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(student); // merge instead of update
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.err.println("Update Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStudent(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Student student = session.find(Student.class, id); // find instead of get
            if (student != null) {
                session.remove(student); // remove instead of delete
                tx.commit();
                return true;
            } else {
                System.err.println("Student not found with ID: " + id);
                if (tx != null) tx.rollback();
                return false;
            }
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.err.println("Delete Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
