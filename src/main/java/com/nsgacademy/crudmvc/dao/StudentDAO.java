package com.nsgacademy.crudmvc.dao;

import com.nsgacademy.crudmvc.model.Student;
import com.nsgacademy.crudmvc.utils.HibernateUtil;
import com.nsgacademy.crudmvc.exception.DAOException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentDAO {

    public void insertStudent(Student student) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(student);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new DAOException("Error inserting student", e);
        }
    }

    public Student selectStudent(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Student.class, id);
        } catch (HibernateException e) {
            throw new DAOException("Error retrieving student with ID: " + id, e);
        }
    }

    public List<Student> selectAllStudents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Student", Student.class).list();
        } catch (HibernateException e) {
            throw new DAOException("Error retrieving student list", e);
        }
    }

    public boolean updateStudent(Student student) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(student);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new DAOException("Error updating student", e);
        }
    }

    public boolean deleteStudent(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Student student = session.find(Student.class, id);
            if (student != null) {
                session.remove(student);
                tx.commit();
                return true;
            } else {
                if (tx != null) tx.rollback();
                return false;
            }
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new DAOException("Error deleting student with ID: " + id, e);
        }
    }
}
