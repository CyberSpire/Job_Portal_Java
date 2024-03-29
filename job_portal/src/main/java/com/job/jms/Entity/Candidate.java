package com.job.jms.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "candidate")
public class Candidate {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "candidate_id")
  private Long cid;

  private String name;
  @Column(name = "email_id")
  private String email;
  private String phone;
  private String gender;
  @Temporal(TemporalType.DATE)
  private Date dob;
  @Column(name = "highest_qualification")
  private String highestQualification;

  @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Application> applications;

  // Default constructor
  public Candidate() 
  {}

  // Constructors, getters, and setters
  public Candidate(String name, String email, String phone, String gender, Date dob, String highestQualification) {
      this.name = name;
      this.email = email;
      this.phone = phone;
      this.gender = gender;
      this.dob = dob;
      this.highestQualification = highestQualification;
  }

  // Getters and setters
  public Long getCid() {
      return cid;
  }

  public void setCid(Long cid) {
      this.cid = cid;
  }

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  public String getEmail() {
      return email;
  }

  public void setEmail(String email) {
      this.email = email;
  }

  public String getPhone() {
      return phone;
  }

  public void setPhone(String phone) {
      this.phone = phone;
  }

  public String getGender() {
      return gender;
  }

  public void setGender(String gender) {
      this.gender = gender;
  }

  public Date getDob() {
      return dob;
  }

  public void setDob(Date dob) {
      this.dob = dob;
  }

  public String getHighestQualification() {
      return highestQualification;
  }

  public void setHighestQualification(String highestQualification) {
      this.highestQualification = highestQualification;
  }

  public List<Application> getApplications() {
      return applications;
  }

  public void setApplications(List<Application> applications) {
      this.applications = applications;
  }

  // toString method
  @Override
  public String toString() {
      return "Candidate [cid=" + cid + ", name=" + name + ", email=" + email + ", phone=" + phone + ", gender="
              + gender + ", dob=" + dob + ", highestQualification=" + highestQualification + "]";
  }
 
 
  public String getApplicationStatus(Job job) {
      if (applications != null) {
          for (Application application : applications) {
              if (application.getJob().equals(job)) {
                  // Check if the status is pending
                  if (application.getStatus().equals("PENDING")) {
                      return "PENDING";
                  } else {
                      return application.getStatus();
                  }
              }
          }
      }
      // If no application is found for the specified job, return a default message
      return "Application Not Found";
  }
  
  public Application getApplication() {
	    if (applications != null && !applications.isEmpty()) {
	        // Return the first application associated with the candidate
	        return applications.get(0);
	    }
	    // If no application is found, return null or handle the situation accordingly
	    return null;
	}

}
