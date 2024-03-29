package com.job.jms.Dao;

import java.util.List;
import com.job.jms.Entity.Candidate;
import com.job.jms.Entity.Job;

public interface CandidateDAO 
{
	void addCandidate(Candidate candidate);
	void deleteCandidate(long candidateId);
	void updateCandidate(Candidate candidate);
	Candidate getCandidateById(long candidateId);
	List<Job> viewJobs();
	void applyJob(long candidateId, long jobId);
	String viewApplicationStatus(long candidateId, long jobId);
}