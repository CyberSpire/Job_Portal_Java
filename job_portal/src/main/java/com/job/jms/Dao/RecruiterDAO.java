package com.job.jms.Dao;

import com.job.jms.Entity.Job;
import com.job.jms.Entity.Candidate;
import com.job.jms.Entity.Recruiter;
import java.util.List;

public interface RecruiterDAO {
	 void addRecruiter(Recruiter recruiter);
	 void deleteRecruiter(long recruiterId);
	 void updateRecruiter(Recruiter recruiter);
	 List<Recruiter> getAllRecruiters();
	 List<Candidate> viewJobApplicants(long jobId);
	 void postJob(Job job);
}