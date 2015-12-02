package com.Interpreter;

import java.util.*;

class Line{
	public Scanner in = new Scanner(System.in);
	
	String nowline;
    
	Line(){
		nowline = "";
	}
	
	public String next(){
		this.nowline = in.nextLine();
		if(this.nowline.indexOf("//") > 0)
			this.nowline = this.nowline.substring(0, this.nowline.indexOf("\\"));
		if(!this.nowline.equals("")) 
			while(this.nowline.charAt(0) == ' ' || this.nowline.charAt(0) == ','){
				this.nowline = this.nowline.substring(1);
			    if(this.nowline.isEmpty())
			    	break;
			}
		if(this.nowline.isEmpty())
			this.next();
		return this.nowline;
	}
}