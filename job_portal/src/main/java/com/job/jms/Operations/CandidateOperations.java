package com.job.jms.Operations;

//CandiateOperations.java//
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.job.jms.DaoImpl.CandidateDAOImpl;
import com.job.jms.Entity.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.List;

public class CandidateOperations {
  public static void performCandidateOperations(Session session, Scanner scanner) {
      int choice;

      do {
          System.out.println("\n---CANDIDATE OPERATIONS:---");
          System.out.println("[1] ADD CANDIDATE");
          System.out.println("[2] DELETE CANDIDATE");
          System.out.println("[3] UPDATE CANDIDATE");
          System.out.println("[4] VIEW JOBS");
          System.out.println("[5] APPLY FOR A JOB");
          System.out.println("[6] VIEW APPLICATION STATUS");
          System.out.println("[7] GET CANDIDATE BY ID");
          System.out.println("[8] EXIT CANDIDATE OPERTAIONS");
          System.out.print(">> ENTER YOUR CHOICE: ");
          choice = scanner.nextInt();

          switch (choice) {
              case 1:
                  addCandidate(session, scanner);
                  break;
              case 2:
                  deleteCandidate(session, scanner);
                  break;
              case 3:
                  updateCandidate(session, scanner);
                  break;
              case 4:
                  viewJobs(session);
                  break;
              case 5:
                  applyForJob(session, scanner);
                  break;
              case 6:
                  viewApplicationStatus(session, scanner);
                  break;
              case 7:
            	  getCandidateById(session,scanner);
                  break;
              case 8:
                  System.out.println("<< EXITING CANDIDATE OPERATIONS... >>");
                  break;
              default:
                  System.out.println("### ERROR: Invalid choice. Please enter a valid option. ###");
                  break;
          }
      } while (choice != 8);
  }

  private static void addCandidate(Session session, Scanner scanner) {
      System.out.println("<< ENTER CANDIDATE DETAILS: >>");
      System.out.print(">> NAME: ");
      scanner.nextLine(); 
      String name = scanner.nextLine();

      System.out.print(">> EMAIL: ");
      String email = scanner.next();

      System.out.print(">> PHONE: ");
      String phone = scanner.next();

      System.out.print(">> GENDER: ");
      String gender = scanner.next();

      System.out.print(">> DATE OF BIRTH(YYYY-MM-DD): ");
      String dobString = scanner.next();
      Date dob = null;
      try {
          dob = new SimpleDateFormat("yyyy-MM-dd").parse(dobString);
      } catch (ParseException e) {
          System.out.println("\n### ERROR: Invalid date format. Please enter date in YYYY-MM-DD format. ###");
          return;
      }

      System.out.print(">> HIGHEST QUALIFICATION: ");
      String highestQualification = scanner.next();

      // Create a Candidate object
      Candidate candidate = new Candidate(name, email, phone, gender, dob, highestQualification);

      // Save the Candidate object to the database
      Transaction transaction = null;
      try {
          transaction = session.beginTransaction();
          session.save(candidate);
          transaction.commit();
          System.out.println("\n<< CANDIDATE ADDED SUCCESSFULLY. >>");
      } catch (Exception e) {
          if (transaction != null) {
              transaction.rollback();
          }
          System.out.println("\n### ERROR: Failed to add candidate. Please try again. ###");
      }
  }

  private static void deleteCandidate(Session session, Scanner scanner) {
      System.out.print(">> ENTER CANDIDATE_ID TO DELETE: ");
      long candidateId = scanner.nextLong();

      // Retrieve candidate from the database
      Candidate candidate = session.get(Candidate.class, candidateId);

      if (candidate != null) {
          Transaction transaction = null;
          try {
              transaction = session.beginTransaction();
              session.delete(candidate);
              transaction.commit();
              System.out.println("\n<< CANIDATE DELETED SUCCESSFULLY. >>");
          } catch (Exception e) {
              if (transaction != null) {
                  transaction.rollback();
              }
              System.out.println("\n### ERROR: Failed to delete candidate. Please try again. ###");
          }
      } else {
          System.out.println("\n### ERROR: No Candidate found with ID: " + candidateId + " ###");
      }
  }


  private static void updateCandidate(Session session, Scanner scanner) {
      System.out.print(">> ENTER CANDIDATE_ID TO UPDATE: ");
      long candidateId = scanner.nextLong();

      // Retrieve candidate from the database
      Candidate candidate = session.get(Candidate.class, candidateId);

      if (candidate != null) {
          System.out.println("\n<< ENTER UPDATED CANDIDATE DETAILS: >>");
          System.out.print(">> NAME: ");
          scanner.nextLine(); // Consume newline character
          String name = scanner.nextLine();
          candidate.setName(name);

          System.out.print(">> EMAIL: ");
          String email = scanner.nextLine();
          candidate.setEmail(email);

          System.out.print(">> PHONE: ");
          String phone = scanner.nextLine();
          candidate.setPhone(phone);

          System.out.print(">> GENDER: ");
          String gender = scanner.next();
          candidate.setGender(gender);

          System.out.print(">> DATE OF BIRTH(YYYY-MM-DD): ");
          String dobString = scanner.next();
          Date dob = null;
          try {
              dob = new SimpleDateFormat("yyyy-MM-dd").parse(dobString);
          } catch (ParseException e) {
              System.out.println("\n### ERROR: Invalid date format. Please enter date in YYYY-MM-DD format. ###");
              return;
          }
          candidate.setDob(dob);

          System.out.print(">> HIGHEST QUALIFICATION: ");
          String highestQualification = scanner.next();
          candidate.setHighestQualification(highestQualification);

          // Update the candidate object in the database
          Transaction transaction = null;
          try {
              transaction = session.beginTransaction();
              session.update(candidate);
              transaction.commit();
              System.out.println("\n<< CANIDATE UPDATED SUCCESSFULLY. >>");
          } catch (Exception e) {
              if (transaction != null) {
                  transaction.rollback();
              }
              System.out.println("\n### ERROR: Failed to Update Candidate. Please try again. ###");
          }
      } else {
          System.out.println("\n### ERROR: No Candidate found with ID: " + candidateId + " ###");
      }
  }

  private static void applyForJob(Session session, Scanner scanner) {
      System.out.print(">> ENTER YOUR CANDIDATE_ID: ");
      long candidateId = scanner.nextLong();

      System.out.print(">> ENTER THE JOB_ID YOU WANT TO APPLY FOR: ");
      long jobId = scanner.nextLong();

      // Retrieve candidate and job from the database
      Candidate candidate = session.get(Candidate.class, candidateId);
      Job job = session.get(Job.class, jobId);

      if (candidate != null && job != null) {
          // Check if candidate has already applied for this job
          if (!job.getApplicants().contains(candidate)) {
              // Create a new application instance
              Application application = new Application();
              application.setCandidate(candidate);
              application.setJob(job);
              application.setRecruiter(job.getRecruiter()); // Set the recruiter associated with the job
              // Set the status and application date
              application.setStatus("PENDING"); // Or whatever initial status you want
              application.setApplicationDate(new Date()); // Current date and time

              // Save the application to the database
              Transaction transaction = null;
              try {
                  transaction = session.beginTransaction();
                  session.save(application);
                  transaction.commit();
                  System.out.println("\n---------------------------------------------------------------------------");
                  System.out.println("\n<< YOU HAVE SUCCESSFULLY APPLIED FOR THE JOB: " + job.getJobRole() + " >>");
                  System.out.println("\n---------------------------------------------------------------------------");
              } catch (Exception e) {
                  if (transaction != null) {
                      transaction.rollback();
                  }
                  System.out.println("\n### ERROR: Failed to apply for the job. Please try again. ###");
              }
          } else {
              System.out.println("\n<< YOU HAVE ALREADY APPLIED FOR THIS JOB. >>");
          }
      } else {
          if (candidate == null) {
              System.out.println("\n### ERROR: No candidate found with ID: " + candidateId + " ###");
          }
          if (job == null) {
              System.out.println("\n### ERROR: No job found with ID: " + jobId + " ###");
          }
      }
  }


  private static void viewJobs(Session session) {
      // Query to retrieve available jobs from the database
      Query<Job> query = session.createQuery("FROM Job", Job.class);
      System.out.println("\n>> AVAILABLE JOBS: \n");
      List<Job> availableJobs = query.list();

      // Check if there are any available jobs
      if (availableJobs.isEmpty()) {
          System.out.println("\n<< NO AVAILABLE JOBS >>");
      } else {
          // Print details of each available job
    	  System.out.println("\n<< AVAILABLE JOBS ARE LISTED >>");
    	  System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
    	  System.out.printf("%-7s  %-15s  %-30s  %-15s  %-40s  %-10s  %-10s\n", "Job ID", "Recruiter", "Job Role", "Posted Date", "Description", "Address", "Salary");
    	  System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
          for (Job job : availableJobs) {
        	  System.out.printf("%-7d  %-15s  %-30s  %-15s  %-40s  %-10s  $%-10.2f\n", 
                      job.getJid(), job.getRecruiter().getName(), job.getJobRole(), job.getPostedDate(), job.getDescription(), job.getAddress(), job.getSalary());
          }
      }
  }
  
  private static void viewApplicationStatus(Session session, Scanner scanner) {
	    System.out.println(">> ENTER YOUR CANDIDATE_ID: ");
	    long candidateId = scanner.nextLong();
	    System.out.println(">> ENTER THE JOB_ID YOU WANT TO VIEW THE APPLICATION STATUS FOR: ");
	    long jobId = scanner.nextLong();

	    // Assuming sessionFactory is accessible or obtained from session
	    SessionFactory sessionFactory = session.getSessionFactory();

	    // Create an instance of CandidateDAOImpl
	    CandidateDAOImpl candidateDAO = new CandidateDAOImpl(sessionFactory);

	    // Call viewApplicationStatus method
	    String applicationStatus = candidateDAO.viewApplicationStatus(candidateId, jobId);

	    if (applicationStatus != null) {
	    	System.out.println("\n-------------------------------------------");
	        System.out.println("<< APPLICATION STATUS: " + applicationStatus + " >>");
	        System.out.println("-------------------------------------------");
	    } else {
	        System.out.println("\n### ERROR: Unable to retrieve application status. ###");
	    }
	}

   
  private static void getCandidateById(Session session, Scanner scanner) {
      System.out.print(">> ENTER CANDIDATE_ID: ");
      long candidateId = scanner.nextLong();
      // Retrieve a candidate by ID
      Candidate candidate = session.get(Candidate.class,candidateId);

      // Do something with the candidate object, like printing its details
      if (candidate != null) {
    	  System.out.println("\n---------------------------------------------------------");
    	  System.out.println("<< CANDIDATE DETAILS >>\n");
          System.out.printf("%-15s: %s\n", "CANDIDATE_ID", candidate.getCid());
          System.out.printf("%-15s: %s\n", "NAME", candidate.getName());
          System.out.printf("%-15s: %s\n", "EMAIL", candidate.getEmail());
          System.out.printf("%-15s: %s\n", "PHONE", candidate.getPhone());
          System.out.printf("%-15s: %s\n", "GENDER", candidate.getGender());
          System.out.printf("%-15s: %s\n", "DOB", candidate.getDob());
          System.out.printf("%-15s: %s\n", "HIGHEST QUAL.", candidate.getHighestQualification());
          System.out.println("---------------------------------------------------------");
          // Print other candidate details
      } else {
          System.out.println("\n<< CANDIDATE NOT FOUND. >>");
      }
  }

}
  
