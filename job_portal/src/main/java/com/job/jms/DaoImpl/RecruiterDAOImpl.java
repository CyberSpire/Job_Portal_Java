package com.job.jms.DaoImpl;

import com.job.jms.Entity.*;
import com.job.jms.Dao.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;


public class RecruiterDAOImpl implements RecruiterDAO {

 private final SessionFactory sessionFactory;

 public RecruiterDAOImpl(SessionFactory sessionFactory) {
     this.sessionFactory = sessionFactory;
 }

 @Override
 public void addRecruiter(Recruiter recruiter) {
     Session session = sessionFactory.openSession();
     Transaction transaction = null;
     try {
         transaction = session.beginTransaction();
         session.save(recruiter);
         transaction.commit();
     } catch (Exception e) {
         if (transaction != null) {
             transaction.rollback();
         }
         e.printStackTrace();
     } finally {
         session.close();
     }
 }

 @Override
 public void deleteRecruiter(long recruiterId) {
     Session session = sessionFactory.openSession();
     Transaction transaction = null;
     try {
         transaction = session.beginTransaction();
         Recruiter recruiter = session.get(Recruiter.class, recruiterId);
         if (recruiter != null) {
             session.delete(recruiter);
             transaction.commit();
         }
     } catch (Exception e) {
         if (transaction != null) {
             transaction.rollback();
         }
         e.printStackTrace();
     } finally {
         session.close();
     }
 }

 @Override
 public void updateRecruiter(Recruiter recruiter) {
     Session session = sessionFactory.openSession();
     Transaction transaction = null;
     try {
         transaction = session.beginTransaction();
         session.update(recruiter);
         transaction.commit();
     } catch (Exception e) {
         if (transaction != null) {
             transaction.rollback();
         }
         e.printStackTrace();
     } finally {
         session.close();
     }
 }

 @Override
 public List<Recruiter> getAllRecruiters() {
     try (Session session = sessionFactory.openSession()) {
         Query<Recruiter> query = session.createQuery("FROM Recruiter", Recruiter.class);
         return query.list();
     } catch (Exception e) {
         e.printStackTrace();
         return null;
     }
 }

 @Override
 public List<Candidate> viewJobApplicants(long jobId) {
     try (Session session = sessionFactory.openSession()) {
         Job job = session.get(Job.class, jobId);
         if (job != null) {
             return job.getApplicants();
         }
     } catch (Exception e) {
         e.printStackTrace();
     }
     return null;
 }

 @Override
 public void postJob(Job job) {
     Session session = sessionFactory.openSession();
     Transaction transaction = null;
     try {
         transaction = session.beginTransaction();
         session.save(job);
         transaction.commit();
     } catch (Exception e) {
         if (transaction != null) {
             transaction.rollback();
         }
         e.printStackTrace();
     } finally {
         session.close();
     }
 }
}