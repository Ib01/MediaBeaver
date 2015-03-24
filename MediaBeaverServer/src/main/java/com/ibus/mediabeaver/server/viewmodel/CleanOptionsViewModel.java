package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.ibus.mediabeaver.core.entity.Activity;

public class CleanOptionsViewModel extends ViewModel
{
	private String directoryToClean;
	private boolean restructureFiles;
	private boolean deleteEmptyDirectories;
	private boolean deleteNonMediaFiles;
	private boolean showExecutionPlan; 

	public String getDirectoryToClean() {
		return directoryToClean;
	}

	public void setDirectoryToClean(String directoryToClean) {
		this.directoryToClean = directoryToClean;
	}

	public boolean isRestructureFiles() {
		return restructureFiles;
	}

	public void setRestructureFiles(boolean restructureFiles) {
		this.restructureFiles = restructureFiles;
	}

	public boolean isDeleteEmptyDirectories() {
		return deleteEmptyDirectories;
	}

	public void setDeleteEmptyDirectories(boolean deleteEmptyDirectories) {
		this.deleteEmptyDirectories = deleteEmptyDirectories;
	}

	public boolean isDeleteNonMediaFiles() {
		return deleteNonMediaFiles;
	}

	public void setDeleteNonMediaFiles(boolean deleteNonMediaFiles) {
		this.deleteNonMediaFiles = deleteNonMediaFiles;
	}

	public boolean isShowExecutionPlan() {
		return showExecutionPlan;
	}

	public void setShowExecutionPlan(boolean showExecutionPlan) {
		this.showExecutionPlan = showExecutionPlan;
	}

	//@NotEmpty
	//@DateTimeFormat(pattern = "dd MMMMM yyyy")
	//@NotNull
	
}
