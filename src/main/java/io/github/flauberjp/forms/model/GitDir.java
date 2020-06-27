package io.github.flauberjp.forms.model;

import java.awt.Component;

public class GitDir {
	  private String path;
	   private boolean isSelected = false;
	 
	   public GitDir(String path) {
	      this.path = path;
	   }
	   
	   public String getPath() {
		   return path;
	   }
	 
	   public boolean isSelected() {
	      return isSelected;
	   }
	 
	   public void setSelected(boolean isSelected) {
	      this.isSelected = isSelected;
	   }
	 
	   public String toString() {
	      return path;
	   }
}
