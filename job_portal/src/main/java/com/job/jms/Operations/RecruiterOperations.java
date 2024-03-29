package com.job.jms.Operations;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.job.jms.Entity.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RecruiterOperations {
  public static void performRecruiterOperations(Session session, Scanner scanner) {
      int choice;

      // After each operation, display the menu and wait for user input
      do {
          System.out.println("\n---RECRUITER OPERATIONS:---");
          System.out.println("[1] ADD RECRUITER");
          System.out.println("[2] DELETE RECRUITER");
          System.out.println("[3] UPDATE RECRUITER");
          System.out.println("[4] VIEW JOB APPLICANTS");
          System.out.println("[5] POST JOB");
          System.out.println("[6] UPDATE APPLICATION STATUS");
          System.out.println("[7] EXIT RECRUITER OPERATIONS");
          System.out.print(">> ENTER YOUR CHOICE: ");
          choice = scanner.nextInt();

          // Perform the corresponding operation based on user input
          switch (choice) {
              case 1:
                  addRecruiter(session, scanner);
                  break;
              case 2:
                  deleteRecruiter(session, scanner);
                  break;
              case 3:
                  updateRecruiter(session, scanner);
                  break;
              case 4:
                  viewJobApplicants(session, scanner);
                  break;
              case 5:
                  postJob(session, scanner);
                  break;
              case 6:
              	updateApplicationStatus(session, scanner);
                  break;
              case 7:
                  System.out.println("\n<< EXITING THE RECRUITER OPERATIONS... >>");
                  break;
              default:
                  System.out.println("\n### ERROR: Invalid choice. Please enter a valid option. ###");
                  break;
          }
      } while (choice != 7); // Continue until the user chooses to exit
  }

  private static void updateApplicationStatus(Session session, Scanner scanner) {
      Transaction transaction = null;
      try {
          // Ask user to enter the application ID
          System.out.print(">> ENTER THE APPLICATION_ID TO UPDATE: ");
          long applicationId = scanner.nextLong();
          scanner.nextLine(); // Consume newline character
          
          // Retrieve the application from the database using the provided ID
          Application application = session.get(Application.class, applicationId);

          // If application exists, proceed with updating its status
          if (application != null) {
              // Ask user to enter the new status
              System.out.print(">> ENTER THE NEW STATUS: ");
              String newStatus = scanner.nextLine();
              
              // Update the application status
              application.setStatus(newStatus);
              
              // Start a transaction
              transaction = session.beginTransaction();

              // Update the application in the database
              session.update(application);

              // Commit the transaction
              transaction.commit();

              System.out.println("\n<< APPLICATION STATUS UPDATED SUCCESSFULLY. >>");
          } else {
              System.out.println("\n<< APPLICATION_ID NOT FOUND! >>");
          }
      } catch (InputMismatchException e) {
          // Handle input mismatch exception
          System.out.println("\n### ERROR: Invalid input format. Please enter a valid ID. ###");
          scanner.nextLine(); // Consume invalid input
      } catch (Exception e) {
          // Rollback the transaction if an exception occurs
          if (transaction != null) {
              transaction.rollback();
          }
          System.out.println("\n### ERROR: An error occurred while updating application status. ###");
      }
  }



	private static void addRecruiter(Session session, Scanner scanner) {
      Transaction transaction = null;
      try {
          System.out.println("<< ENTER RECRUITER DETAILS: >>");
          scanner.nextLine(); // Consume newline character

          System.out.print(">> NAME: ");
          String name = scanner.nextLine();
          System.out.print(">> EMAIL: ");
          String email = scanner.nextLine();
          System.out.print(">> COMPANY NAME: ");
          String companyName = scanner.nextLine();

          Recruiter recruiter = new Recruiter(name, email, companyName);

          transaction = session.beginTransaction();
          session.save(recruiter);
          transaction.commit();

          System.out.println("\n<< RECRUITER ADDED SUCCESSFULLY. >>");
      } catch (Exception e) {
          if (transaction != null) {
              transaction.rollback();
          }
          e.printStackTrace();
      }
  }

  private static void deleteRecruiter(Session session, Scanner scanner) {
      // Ask for recruiter ID to delete
      System.out.print(">> ENTER RECRUITER_ID TO DELETE: ");
      long recruiterId = scanner.nextLong();

      // Retrieve the Recruiter object from the database
      Recruiter recruiter = session.get(Recruiter.class, recruiterId);

      // If Recruiter exists, delete it
      if (recruiter != null) {
          session.delete(recruiter);
          System.out.println("\n<< RECRUITER DELETED SUCCESSFULLY. >>");
      } else {
          System.out.println("\n<< RECRUITER_ID NOT FOUND! >>");
      }
  }

  private static void updateRecruiter(Session session, Scanner scanner) {
      // Ask for recruiter ID to update
      System.out.print(">> ENTER RECRUITER_ID TO UPDATE: ");
      long recruiterId = scanner.nextLong();

      // Consume newline character
      scanner.nextLine();

      // Retrieve the Recruiter object from the database
      Recruiter recruiter = session.get(Recruiter.class, recruiterId);

      // If Recruiter exists, update its attributes
      if (recruiter != null) {
          // Ask for new details
          System.out.println("<< ENTER NEW DETAILS FOR THE RECRUITER: >>");
          System.out.print(">> NAME: ");
          String name = scanner.nextLine();
          System.out.print(">> EMAIL: ");
          String email = scanner.nextLine();
          System.out.print(">> COMPANY NAME: ");
          String companyName = scanner.nextLine();

          // Update the Recruiter object
          recruiter.setName(name);
          recruiter.setEmail(email);
          recruiter.setCompanyName(companyName);

          // Save the changes
          Transaction transaction = session.beginTransaction();
          session.update(recruiter);
          transaction.commit();

          System.out.println("\n<< RECRUITER UPDATED SUCCESSFULLY. >>");
      } else {
          System.out.println("\n<< RECRUITER_ID NOT FOUND! >>");
      }
  }

  public static void viewJobApplicants(Session session, Scanner scanner) {
	    System.out.print(">> ENTER THE JOB_ID YOU WANT TO VIEW APPLICANTS FOR: ");
	    long jobId = scanner.nextLong();

	    // Retrieve the job from the database
	    Job job = session.get(Job.class, jobId);
	    if (job != null) {
	        // Refresh the job entity to ensure it has the latest data
	        session.refresh(job);
	        
	        // Get the list of applicants for the job
	        List<Candidate> applicants = job.getApplicants();

	        // Print the details of each applicant
	        if (applicants.isEmpty()) {
	            System.out.println("\n<< NO APPLICANTS FOUND FOR THIS JOB. >>");
	        } else {
	        	System.out.println("\n<< APPLICANTS FOR THE JOB: ");
	        	System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------");
	        	System.out.printf("%-13s %-30s %-20s %-15s %-10s %-12s %-20s\n", "Applicant ID", "Name", "Email", "Phone", "Gender", "DOB", "Highest Qualification");
	        	System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
	            for (Candidate applicant : applicants) {
	                // Accessing application ID and other details
	                Application application = applicant.getApplication();
	                if (application != null) {
	                	System.out.printf("%-13d %-30s %-20s %-15s %-10s %-12s %-20s\n", 
	                            application.getAid(), applicant.getName(), applicant.getEmail(), applicant.getPhone(), applicant.getGender(), applicant.getDob(), applicant.getHighestQualification());
	                } else {
	                    System.out.println("No application found for candidate: " + applicant.getName());
	                }
	            }
	        }
	    } else {
	        System.out.println("\n<< JOB NOT FOUND! >>");
	    }
	}


  private static void postJob(Session session, Scanner scanner) {
      try {
          System.out.println("<< ENTER JOB DETAILS: >>");
          System.out.print(">> RECRUITER_ID: ");
          Long recruiterId = scanner.nextLong();
          scanner.nextLine(); // Consume the newline character
          Recruiter recruiter = session.get(Recruiter.class, recruiterId);

          System.out.print(">> JOB ROLE: ");
          String jobRole = scanner.nextLine();
          System.out.print(">> DESCRIPTION: ");
          String description = scanner.nextLine();
          System.out.print(">> ADDRESS: ");
          String address = scanner.nextLine();
          System.out.print(">> SALARY: ");
          double salary = scanner.nextDouble();
          scanner.nextLine(); // Consume the newline character

          // Create a new Job object with the provided details
          Job job = new Job();
          job.setRecruiter(recruiter);
          job.setJobRole(jobRole);
          job.setDescription(description);
          job.setAddress(address);
          job.setSalary(salary);

          // Save the Job object to the database
          Transaction transaction = session.beginTransaction();
          session.save(job);
          transaction.commit();

          System.out.println("\n<< JOB POSTED SUCCESSFULLY. >>");
      } catch (Exception e) {
          System.out.println("\n### ERROR: An error occurred while posting the job. ###");
      }
  }

}