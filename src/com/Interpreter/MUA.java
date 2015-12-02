package com.Interpreter;

class MUA{
	
	public static void main(String args[]){
	    Line line = new Line();
	    line.next();
	    
	    //Main loop for the interpreter
		while(!line.nowline.isEmpty()){
			Value sentence = new Value();
			sentence.getValue(0, line);
			if(line.nowline.isEmpty())
				line.next();
			if(line.nowline.equals("exit"))
				break;
		}	
	}
}
