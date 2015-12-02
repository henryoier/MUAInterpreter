package com.Interpreter;

import java.util.*;

class Value{
	//define each type of value
	public final int UNDECIDE = -1;  
	public final int OP = 0;
	public final int NUMBER = 1;
	public final int WORD = 2;   
	public final int LIST = 3;
	public final int BOOL = 4;
			
	//store each type of value, everytime only one of them stores
	int type;
	float number;
	String word;
	boolean bool;
	Vector<Value> list;
	
	//Default constructor
	Value(){
		this.type = 0;
		this.number = 0;
		this.word = null;
		this.bool = false;
		this.list = new Vector<Value>();
	};
	
	//Copy constructor
	Value(Value v){
		this.type = v.type;
		this.number = v.number;
		this.word = v.word;
		this.bool = v.bool;
		this.list = v.list;
	}
	
	//Like "="
	public void Copy(Value v){
		this.type = v.type;
		this.number = v.number;
		this.word = v.word;
		this.bool = v.bool;
		this.list = v.list;
	}
	
	public int min(int a, int b){
		if(a > b)
			return b;
		else 
			return a;
	}
	
	//Check the type of next string, filter blanks and commas 
	public int checktype(Line line){
		lineFilter(line);
		if(Character.isDigit(line.nowline.charAt(0)) || line.nowline.charAt(0) == '-')
			return NUMBER;
		if(line.nowline.charAt(0) == '[')
			return LIST;
		if(((line.nowline.length()>=4) && line.nowline.substring(0,4).equals("true")) ||
               (line.nowline.length()>=5 && line.nowline.substring(0,5).equals("false")))
			return BOOL;
		if(line.nowline.charAt(0) == '"')
			return WORD;
		
		return OP;
	}
	
	//Find out the first separater
	public int getFirstSep(String line){
	    int end = -1;
		int First = line.indexOf(' ');
	    if(First > 0)
	    	if(end < 0 || end > First)
	    		end = First;
	    First = line.indexOf(',');
	    if(First > 0)
	    	if(end < 0 || end > First)
	    		end = First;
		First = line.indexOf(']');
	    if(First > 0)
	    	if(end < 0 || end > First)
	    		end = First;
		if(end == -1)
			end = line.length();
		
	    return end;
	}
	
	public void lineFilter(Line line){
		if(!line.nowline.isEmpty())
			while(line.nowline.charAt(0) == ' ' || line.nowline.charAt(0) == ','){
				line.nowline = line.nowline.substring(1);
				if(line.nowline.isEmpty())
					line.next();
			}
		else{
			line.next();
			lineFilter(line);
		}
	}
	
	//Get value for list
	public void getListValue(int type,Line line){
		int check = checktype(line);
		
		if(type != OP)
			if(check != type){
				ErrorHandle EH = new ErrorHandle();
				EH.Handle(EH.TYPEERROR);
			}
			else {
				this.type = type;
			}
		else 
		   this.type = check;
		
		switch(this.type){
			case NUMBER:{   
				String tmparg;
				int end = getFirstSep(line.nowline);
				
				tmparg = line.nowline.substring(0,end);
				line.nowline = line.nowline.substring(end);

				this.number = Float.parseFloat(tmparg);
				break;
			}
			case WORD:{
				// check if obey type of a word
				if(line.nowline.charAt(0)!='"'){
					ErrorHandle EH = new ErrorHandle();
				    EH.Handle(EH.GETWORDERROR);
				}
				else{
				String tmparg;
				int end = getFirstSep(line.nowline);
				
				tmparg = line.nowline.substring(1,end);
				line.nowline = line.nowline.substring(end);
				
				this.word =  tmparg;
				break;
				}	 
			}
			case BOOL:{
				String tmparg;
				int end = getFirstSep(line.nowline);
				
				tmparg = line.nowline.substring(0,end);
				line.nowline = line.nowline.substring(end);
				
				switch(tmparg){
					case "true":{
						this.bool = true;
						break;
					} 
					case "false":{
						this.bool  = false;
						break;
					}
					default:
						ErrorHandle EH = new ErrorHandle();
						EH.Handle(EH.GETBOOLERROR);
						break;
				}	
				break;
			}
			case LIST:{            
				line.nowline = line.nowline.substring(1);
				lineFilter(line);
				while(true){
					lineFilter(line);
					if(line.nowline.charAt(0) == ']')break;
					else{
						Value arg = new Value();
						arg.getListValue(0,line);
						this.list.addElement(new Value(arg));
					}
				}
				line.nowline = line.nowline.substring(1);
				break;
			}
			case OP:{
				String tmparg;
				int end = getFirstSep(line.nowline);
				
				tmparg = line.nowline.substring(0,end);
				line.nowline = line.nowline.substring(end);
                lineFilter(line);

                this.word = tmparg;
			}
		}//bracket of switch
	}
	
	public boolean getValue(int type,Line line){
		int check = checktype(line);
		
		if(type != OP)
			if(check != type){
				ErrorHandle EH = new ErrorHandle();
				EH.Handle(EH.TYPEERROR);
			}
			else {
				this.type = type;
			}
		else 
		   this.type = check;
		
		switch(this.type){
			case NUMBER:{   
				String tmparg;
				int end = getFirstSep(line.nowline);
				
				tmparg = line.nowline.substring(0,end);
				line.nowline = line.nowline.substring(end);

				this.number = Float.parseFloat(tmparg);
				break;
			}
			case WORD:{
				// check if obey type of a word
				if(line.nowline.charAt(0)!='"'){
					ErrorHandle EH = new ErrorHandle();
				    EH.Handle(EH.GETWORDERROR);
				}
				else{
				String tmparg;
				int end = getFirstSep(line.nowline);
				
				tmparg = line.nowline.substring(1,end);
				line.nowline = line.nowline.substring(end);
				
				this.word =  tmparg;
				break;
				}	 
			}
			case BOOL:{
				String tmparg;
				int end = getFirstSep(line.nowline);
				
				tmparg = line.nowline.substring(0,end);
				line.nowline = line.nowline.substring(end);
				
				switch(tmparg){
					case "true":{
						this.bool = true;
						break;
					} 
					case "false":{
						this.bool  = false;
						break;
					}
					default:
						ErrorHandle EH = new ErrorHandle();
						EH.Handle(EH.GETBOOLERROR);
						break;
				}	
				break;
			}
			case LIST:{
				line.nowline = line.nowline.substring(1);
				lineFilter(line);
				while(true){
					lineFilter(line);
					if(line.nowline.charAt(0) == ']')break;
					else{
						Value arg = new Value();
						arg.getListValue(0,line);
						this.list.addElement(new Value(arg));
					}
				}
				line.nowline = line.nowline.substring(1);
				break;
			}
			case OP:{
				boolean flag = false;
				int sep = getFirstSep(line.nowline);
				String op = line.nowline.substring(0,sep);
				if(op.equals("output"))
					flag = true;
				line.nowline = line.nowline.substring(sep);
                OpHandler OH = new OpHandler();
                OH.doOP(op, line, this);
                return flag;
			}
		}//bracket of switch
		return false;
	}
	
	//Print the value
	public void print(){
		switch(this.type){
			case NUMBER:{
				if(this.number == (int)this.number)
					System.out.print((int)this.number);
				else 
					System.out.print(this.number);
				break;
			}
			case WORD:{
				System.out.print('"' + this.word);
				break;
			}
			case LIST:{
				System.out.print('[');
				for(int i = 0;i < this.list.size();i++){
					this.list.elementAt(i).print();
					if(i != this.list.size()-1)
					   System.out.print(' ');
				}
				System.out.print(']');
				break;
			}
			case BOOL:{
				if(this.bool)
				  System.out.print("true");
				else
				  System.out.print("false");
				break;
			}
		}
	}
}