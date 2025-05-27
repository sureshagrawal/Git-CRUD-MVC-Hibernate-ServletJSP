package com.nsgacademy.crudmvc.dao;

import com.nsgacademy.crudmvc.model.Student;
import com.nsgacademy.crudmvc.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentDAOold {

    public void insertStudent(Student student) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.persist(student);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    public Student selectStudent(int id) {
        Session session = null;
        Student student = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            student = session.find(Student.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return student;
    }

    @SuppressWarnings("unchecked")
    public List<Student> selectAllStudents() {
        Session session = null;
        List<Student> students = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            students = session.createQuery("from Student").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return students;
    }

    public boolean updateStudent(Student student) {
        Session session = null;
        Transaction tx = null;
        boolean updated = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.merge(student);
            tx.commit();
            updated = true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return updated;
    }

    public boolean deleteStudent(int id) {
        Session session = null;
        Transaction tx = null;
        boolean deleted = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Student student = session.find(Student.class, id);
            if (student != null) {
                session.remove(student);
                tx.commit();
                deleted = true;
            } else {
                if (tx != null) tx.rollback();
            }
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return deleted;
    }
}
