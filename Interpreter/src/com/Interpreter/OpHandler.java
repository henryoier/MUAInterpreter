package com.Interpreter;

import java.util.*;

class OpHandler{
	//namespace for the program
	private static Map<String, Value> space = new HashMap<String,Value>();
	
	//Reversed words that cannot be make as a name
	List<String> key = new ArrayList<String>(Arrays.asList(
			"make","thing",":","erase","isname",
            "print","read","readlist","add","sub",
            "mul","div","mod","eq","gt",
            "lt","and","or","not","random",
            "sqrt","isnumber","isword","islist","isbool",
            "test","iftrue","iffalse","word","list",
            "join","first","last","butfirst","butlast",
            "item","repeat","stop","wait","save",
            "load","erall","poall","output","stop",
            "local","pi"));
	
	public static boolean lastTest;
	
	public boolean checkNameillegal(String word){ 
	   return key.contains(word);	
	}
	
	public boolean checkExist(String word){
		return space.containsKey(word);
	}
	
	public void lineFilter(Line line){
		if(!line.nowline.isEmpty())
			while(line.nowline.charAt(0) == ' ' || line.nowline.charAt(0) == ','){
				line.nowline = line.nowline.substring(1);
				if(line.nowline.isEmpty())
					break;
			}
	}
	
	//for make function
    public void doMake(Line line){
    	Value arg0 = new Value();
    	Value arg1 = new Value();
    	arg0.getValue(0, line);
    	arg1.getValue(0, line);
    	if(arg0.type != arg0.WORD){
    		ErrorHandle EH = new ErrorHandle();
    		EH.Handle(EH.TYPEERROR);
    	}
    	if(checkNameillegal(arg0.word)){
    		ErrorHandle EH = new ErrorHandle();
    		EH.Handle(EH.NAMESPACEERROR);
    	}
    	else 
    		space.put(arg0.word, new Value(arg1));
    }
    
    public void doThing(Line line, Value returnValue){
    	Value arg0 = new Value();
    	arg0.getValue(0, line);
    	if(arg0.type == 0)
    		returnValue.Copy(space.get(arg0.word));
    	else {
    		returnValue.Copy(arg0);
    	}
    }
    
    public void doErase(Line line){
    	Value arg0 = new Value();
    	arg0.getValue(0, line);
    	if(checkExist(arg0.word))
    		space.remove(arg0.word);
    	else {
			ErrorHandle EH = new ErrorHandle();
		    EH.Handle(EH.ERASEERROR);
    	}
    }
    
    public void doPrint(Line line){
    	Value arg0 = new Value();
    	arg0.getValue(0, line);
    	arg0.print();
    }
    
    public void doIsname(Line line, Value returnValue){
    	Value arg0 = new Value();
    	arg0.getValue(0, line);
    	if(space.containsKey(arg0.word)){
    		returnValue.type = returnValue.BOOL;
    		returnValue.bool = true;
    	}
    	else{
    		returnValue.type = returnValue.BOOL;
    		returnValue.bool = false;
    	}
    }
    
    public void doRead(Line line, Value returnValue){
    	Value arg0 = new Value();
    	arg0.getValue(0, line);
    	if(arg0.type == arg0.NUMBER){
    		returnValue.type = arg0.type;
    		returnValue.number = arg0.number;
    	}else if(arg0.type == arg0.WORD){
    		returnValue.type = arg0.type;
    		returnValue.word = arg0.word;
    	}else{
    		ErrorHandle EH = new ErrorHandle();
    		EH.Handle(EH.READERROR);
    	}
    }
    
    public void doReadlist(Line line, Value returnValue){
    	Value arg = new Value();// to store each value
    	returnValue.type = returnValue.LIST;
    	
    	if(line.nowline.equals(""))
    	   	line.next();
		
		while(!line.nowline.isEmpty()){
			arg.getValue(0,line);
			returnValue.list.addElement(new Value(arg));
		}
    }
    
    public void doOperator(String op, Line line, Value returnValue){
    	Value arg0 = new Value();
    	Value arg1 = new Value();
    	returnValue.type = returnValue.NUMBER;
    	arg0.getValue(0, line);
    	if(line.nowline.equals(""))
    		line.next();
    	arg1.getValue(0, line);
    	switch (op) {
			case "add":{
				returnValue.number = arg0.number + arg1.number;
				break;
			}
			case "sub":{
				returnValue.number = arg0.number - arg1.number;
				break;
			}
			case "mul":{
				returnValue.number = arg0.number * arg1.number;
				break;
			}
			case "div":{
				returnValue.number = arg0.number / arg1.number;
				break;
			}
			case "mod":{
				returnValue.number = arg0.number % arg1.number;
				break;
			}
		}
    	
    }
    
    public void doCompare(String op, Line line, Value returnValue){
    	Value arg0 = new Value();
    	Value arg1 = new Value();
    	returnValue.type = returnValue.BOOL;
   
    	arg0.getValue(0, line);
    	if(line.nowline.equals(""))
    		line.next();
    	arg1.getValue(0, line);
    	
    	if(arg0.type == arg0.NUMBER && arg1.type == arg1.NUMBER){
    		switch(op){
    			case "eq":{
    				returnValue.bool = (arg0.number == arg1.number);
    				break;
    			}
    			case "gt":{
    				returnValue.bool = (arg0.number > arg1.number);
    				break;
    			}
    			case "lt":{
    				returnValue.bool = (arg0.number < arg1.number);
    				break;
    			}
    		}
    	}else if(arg0.type == arg0.WORD && arg1.type == arg1.WORD){
	    		switch(op){
					case "eq":{
						returnValue.bool = (arg0.word.equals(arg1.word));
						break;
					}
					case "gt":{
						returnValue.bool = (arg0.word.compareTo(arg1.word) > 0);
						break;
					}
					case "lt":{
						returnValue.bool = (arg1.word.compareTo(arg0.word) > 0);
						break;
					}
	    		} 
    		  }else{
	    			ErrorHandle EH = new ErrorHandle();
	    			EH.Handle(EH.COMPAREERROR);
	    	  }
    }
    
    public void doDoublebool(String op, Line line, Value returnValue){
    	Value arg0 = new Value();
    	Value arg1 = new Value();
    	returnValue.type = returnValue.BOOL;
   
    	arg0.getValue(0, line);
    	if(line.nowline.equals(""))
    		line.next();
    	arg1.getValue(0, line);  
    	
    	switch (op) {
			case "and":{
				returnValue.bool = (arg0.bool && arg1.bool); 
				break;
			}
			case "or":{
				returnValue.bool = (arg0.bool || arg1.bool); 
				break;
			}
		}
    }
    
    public void doNot(Line line, Value returnValue){
    	Value arg0 = new Value();
    	arg0.getValue(0, line);
    	returnValue.type = returnValue.BOOL;
    	returnValue.bool = !arg0.bool;
    }
    
    public void doRandom(Line line, Value returnValue){
    	Value arg0 = new Value();
    	Random r = new Random();
    	arg0.getValue(0, line);
    	returnValue.type = returnValue.NUMBER;
    	returnValue.number = (int)(r.nextDouble()*arg0.number);
    }
    
    public void doSqrt(Line line, Value returnValue){
    	Value arg0 = new Value();
    	arg0.getValue(0, line);
    	returnValue.type = returnValue.NUMBER;
    	returnValue.number = (int)Math.sqrt(arg0.number);
    }
    
    public void doIsvalue(String op, Line line, Value returnValue){
    	Value arg0 = new Value();
    	arg0.getValue(0, line);
    	returnValue.type = returnValue.BOOL;
    	switch (op) {
    		case "isnumber":
    			returnValue.bool = (arg0.type == arg0.NUMBER);
    			break;
    		case "isword":
    			returnValue.bool = (arg0.type == arg0.WORD);
    			break;
    		case "islist":
    			returnValue.bool = (arg0.type == arg0.LIST);
    			break;
    		case "isbool":
    			returnValue.bool = (arg0.type == arg0.BOOL);
    			break;	
    		case "isempty":
    			if(arg0.type == arg0.LIST)
    				returnValue.bool = (arg0.list.size() == 0);
    			if(arg0.type == arg0.WORD)
    				returnValue.bool = (arg0.word.isEmpty());
    			break;
    	}
    }
    
    public void doTest(Line line, Value returnValue){
    	Value arg0 = new Value();
    	arg0.getValue(0, line);
    	returnValue.type = returnValue.BOOL;
    	if(arg0.type == arg0.BOOL)
    		returnValue.bool = arg0.bool;
    	else if(arg0.type == arg0.NUMBER)
    		returnValue.bool = (arg0.number!=0);
    	else if(arg0.type == arg0.WORD)
 		   	returnValue.bool = (!arg0.word.isEmpty());
    	else
    		returnValue.bool = (arg0.list.size()!=0);
    	lastTest = returnValue.bool;
    }
    
    public String addListToLine(String addline,Value v){
    	for(int i = 0;i<v.list.size();i++){
			switch (v.list.elementAt(i).type) {
			case 1:
				addline = addline + v.list.elementAt(i).number + ' ';
				break;
			case 2:
				addline = addline + '"' + v.list.elementAt(i).word + ' ';
				break;
			case 3:
				addline = addline + '[';
				addline = addline + addListToLine("", v.list.elementAt(i));
				addline = addline + "] ";
				break;
			case 4:
				if(v.bool)
					addline = addline + "true ";
				else 
					addline = addline + "false ";
				break;
			default:
				addline = addline + v.list.elementAt(i).word + ' ';
				break;
			}
		}
    	return addline;
    }
    
    public void doIf(String op, Line line){
    	Value arg0 = new Value();
    	arg0.getValue(0, line);
    	if(op.equals("iftrue") && lastTest){
    		Line addline = new Line();
        	addline.nowline = addline.nowline + addListToLine("", arg0);
            line.nowline = addline.nowline + ' ' + line.nowline;
        	}
    	if(op.equals("iffalse") && !lastTest){
    		Line addline = new Line();
        	addline.nowline = addline.nowline + addListToLine("", arg0);
        	line.nowline = addline.nowline + ' ' + line.nowline;
        	}
    	if(op.equals("if") && arg0.type == arg0.BOOL){
    		Value arg1 = new Value();
    		Value arg2 = new Value();
    		if(arg0.bool){
    			Line addline = new Line();
            	addline.nowline = addline.nowline + addListToLine("", arg1);
            	line.nowline = addline.nowline + ' ' + line.nowline;
    		}else{
    			Line addline = new Line();
            	addline.nowline = addline.nowline + addListToLine("", arg2);
            	line.nowline = addline.nowline + ' ' + line.nowline;
    		}
    	}
    }
    
    public void doWord(Line line, Value returnValue){
    	Value arg0 = new Value();
    	Value arg1 = new Value();
    	returnValue.type = returnValue.WORD;
    	arg0.getValue(0, line);
    	arg1.getValue(0, line);
    	if(arg1.type == arg1.NUMBER)
    		returnValue.word = arg0.word + arg1.number;
    	if(arg1.type == arg1.WORD)
    		returnValue.word = arg0.word + arg1.word;
    	if(arg1.type == arg1.BOOL)
    		if(arg1.bool)
    			returnValue.word = arg0.word + "true";
    		else
    			returnValue.word = arg0.word + "false";
    }
    
    public void doList(Line line, Value returnValue){
    	Value arg0 = new Value();
    	Value arg1 = new Value();
    	returnValue.type = returnValue.LIST;
    	arg0.getValue(0, line);
    	arg1.getValue(0, line);
        for(int i = 0;i < arg0.list.size();i++)
        	returnValue.list.addElement(new Value(arg0.list.elementAt(i)));
        for(int i = 0;i < arg1.list.size();i++)
        	returnValue.list.addElement(new Value(arg1.list.elementAt(i)));
    }
    
    public void doJoin(Line line, Value returnValue){
    	Value arg0 = new Value();
    	Value arg1 = new Value();
    	arg0.getValue(0, line);
    	arg1.getValue(0, line);
    	if(arg0.type != arg0.LIST){
    		ErrorHandle EH = new ErrorHandle();
    		EH.Handle(EH.TYPEERROR);
    	}
    	arg0.list.addElement(new Value(arg1));
    	returnValue.Copy(arg0);
    }
    
    public void doFirst(String op, Line line, Value returnValue){
    	Value arg0 = new Value();
    	arg0.getValue(0, line);
    	if(arg0.type == arg0.WORD){
    		returnValue.type = returnValue.WORD;
    		switch (op) {
				case "first":
					returnValue.word = "" + arg0.word.charAt(0);
					break;
				case "last":
					returnValue.word = "" + arg0.word.charAt(arg0.word.length()-1);
					break;
				case "butfirst":
					returnValue.word = arg0.word.substring(1);
					break;
				case "butlast":
					returnValue.word = arg0.word.substring(0, arg0.word.length()-1);
					break;
			}
    	}
    	if(arg0.type == arg0.LIST){
    		switch (op) {
				case "first":
					returnValue.type = arg0.list.elementAt(0).type;
					switch (returnValue.type) {
						case 1:
							returnValue.number = arg0.list.elementAt(0).number;	
							break;
						case 2:
							returnValue.word = arg0.list.elementAt(0).word;	
							break;
						case 3:
							returnValue.list = arg0.list.elementAt(0).list;	
							break;
						case 4:
							returnValue.bool = arg0.list.elementAt(0).bool;	
							break;
					}
					break;
				case "last":
					returnValue.type = arg0.list.elementAt(arg0.list.size()-1).type;
					switch (returnValue.type) {
						case 1:
							returnValue.number = arg0.list.elementAt(arg0.list.size()-1).number;	
							break;
						case 2:
							returnValue.word = arg0.list.elementAt(arg0.list.size()-1).word;	
							break;
						case 3:
							returnValue.list = arg0.list.elementAt(arg0.list.size()-1).list;	
							break;
						case 4:
							returnValue.bool = arg0.list.elementAt(arg0.list.size()-1).bool;	
							break;
					}
					break;
				case "butfirst":
					returnValue.type = arg0.LIST;
                    for(int i = 1;i<arg0.list.size();i++)
                    	returnValue.list.addElement(new Value(arg0.list.elementAt(i)));
					break;
				case "butlast":
					returnValue.type = arg0.LIST;
                    for(int i = 0;i<arg0.list.size()-1;i++)
                    	returnValue.list.addElement(new Value(arg0.list.elementAt(i)));
					break;
		}
    	}
    }
    
    public void doItem(Line line, Value returnValue){
    	Value arg0 = new Value();
    	Value arg1 = new Value();
    	arg0.getValue(0, line);
    	arg1.getValue(0, line);
    	if(arg0.type != arg0.NUMBER){
    		ErrorHandle EH = new ErrorHandle();
    		EH.Handle(EH.TYPEERROR);
    	}
    	if(arg1.type == arg1.WORD){
    		if(arg0.number >= arg1.word.length()){
    			ErrorHandle EH = new ErrorHandle();
        		EH.Handle(EH.TYPEERROR);
    		}
    		returnValue.type = returnValue.WORD;
    		returnValue.word = "" + arg1.word.charAt((int)arg0.number - 1);
    	}
    	if(arg1.type == arg1.LIST){
    		if(arg0.number >= arg1.list.size()){
    			ErrorHandle EH = new ErrorHandle();
        		EH.Handle(EH.TYPEERROR);
    		}
    		returnValue.type = arg1.list.elementAt((int)arg0.number - 1).type;
    		switch (returnValue.type) {
				case 1:
					returnValue.number = arg1.list.elementAt((int)arg0.number - 1).number;	
					break;
				case 2:
					returnValue.word = arg1.list.elementAt((int)arg0.number - 1).word;	
					break;
				case 3:
					returnValue.list = arg1.list.elementAt((int)arg0.number - 1).list;	
					break;
				case 4:
					returnValue.bool = arg1.list.elementAt((int)arg0.number - 1).bool;	
					break;
    		}
    	}
    	
    }
    
    public void doRepeat(Line line){
    	Value arg0 = new Value();
    	Value arg1 = new Value();
    	arg0.getValue(0, line);
    	arg1.getValue(0, line);
    	if(arg0.type != arg0.NUMBER || arg1.type != arg1.LIST){
    		ErrorHandle EH = new ErrorHandle();
    		EH.Handle(EH.TYPEERROR);
    	}
    	Line addline = new Line();
    	for(int i = 0;i < arg0.number;i++){
    		addline.nowline = addline.nowline + addListToLine("", arg1);
    	}

    	Value re = new Value();
    	while(!addline.nowline.isEmpty()){
    		re.getValue(0, addline);
    		lineFilter(addline);
    	}
    }
    
    public void doStop(Line line){
    	line.nowline = "";
    }
    
    public void doWait(Line line){
    	Value arg0 = new Value();
      	arg0.getValue(0, line);
      	if(arg0.type != arg0.NUMBER){
      		ErrorHandle EH = new ErrorHandle();
      		EH.Handle(EH.TYPEERROR);
      	}
      	try{
			Thread.sleep((int)arg0.number);
      	}catch(Exception e){}
    }
    
    public void doSpace(String op){
    	if(op.equals("erall")){
		    Set<String> set = space.keySet();   
		    for (String s:set) {  
		    	System.out.print(s+" ");  
		    } 
		    System.out.println();
    	}
    	else {
    		space.clear();
		}
    }
    
    public String replaceListToLine(String addline,Map<String,Value> args, Value operation){
    	for(int i = 0;i < operation.list.size();i++){
			switch (operation.list.elementAt(i).type) {
				case 1:
					addline = addline + operation.list.elementAt(i).number + ' ';
					break;
				case 2:
					addline = addline + '"' + operation.list.elementAt(i).word + ' ';
					break;
				case 3:
					addline = addline + '[';
					addline = addline + replaceListToLine("", args, operation.list.elementAt(i));
					addline = addline + "] ";
					break;
				case 4:
					if(operation.bool)
						addline = addline + "true ";
					else 
						addline = addline + "false ";
					break;
				default:
					if(args.containsKey(operation.list.elementAt(i).word)){
						Value v = (Value)args.get(operation.list.elementAt(i).word);
						switch (v.type) {
							case 1:
								addline = addline + v.number + ' ';
								break;
							case 2:
								addline = addline + '"' + v.word + ' ';
								break;
							case 3:
								addline = addline + '[';
								addline = addline + replaceListToLine("",args, v);
								addline = addline + "] ";
								break;
							case 4:
								if(v.bool)
									addline = addline + "true ";
								else 
									addline = addline + "false ";
								break;
						}
					}
					else
						addline = addline + operation.list.elementAt(i).word + ' ';
					break;
			}
		}
    	return addline;
    }
    
    public void doOutput(Line line, Value returnValue){
    	Value arg0 = new Value();
    	arg0.getValue(0, line);
    	returnValue.type = arg0.type;
    	switch (arg0.type) {
	    	case 1:returnValue.number = arg0.number;
			   break;
	    	case 2:returnValue.word = arg0.word;
			   break;
	    	case 3:returnValue.list = arg0.list;
			   break;
	    	case 4:returnValue.bool = arg0.bool;
			   break;
		}
    }
    
    public void doRun(Line line){
    	Value arg0 = new Value();
    	arg0.getValue(0, line);
    	
    	Line addline = new Line();
    	addline.nowline = addListToLine("", arg0);
    	Value re = new Value();
    	while(!addline.nowline.isEmpty()){
    		re.getValue(0, addline);
			lineFilter(addline);
    	}
    }
    
    public void doFunction(String op, Line line, Value returnValue){
    	if(!space.containsKey(op)){
    		ErrorHandle EH = new ErrorHandle();
    		EH.Handle(EH.FUNCTIONNAMEERROR);
    	}
    	Value oparg = space.get(op);
    	if(oparg.type == oparg.LIST && oparg.list.size() == 2 && 
    		oparg.list.elementAt(0).type == oparg.LIST &&
    		 oparg.list.elementAt(1).type == oparg.LIST){
    	Value argument = space.get(op).list.elementAt(0);
    	Value operation = space.get(op).list.elementAt(1);
    	Map<String, Value> args = new HashMap<String, Value>();
    	
    	for(int i = 0;i < argument.list.size();i++){
        	Value tmparg = new Value();
    		tmparg.getValue(0, line);
    		args.put(argument.list.elementAt(i).word, new Value(tmparg));
    	}
    	
    	Line addline = new Line();
    	addline.nowline = replaceListToLine("", args, operation);
    	
    	Value re = new Value();
    	while(!addline.nowline.isEmpty()){
    		if(re.getValue(0, addline)){
    			returnValue.type = re.type;
    			switch (re.type){
              		case 1:
              			returnValue.number = re.number;
              			break;
              		case 2:
              			returnValue.word = re.word;
              			break;
              		case 3:
              			returnValue.list = re.list;
              			break;
              		case 4:
              			returnValue.bool = re.bool;
              			break;
    			} 
    		}
			lineFilter(addline);
    	}
    }
    	else{
    		returnValue.type = oparg.type;
    		returnValue.Copy(oparg);
    	}
    }
    
	public void doOP(String op, Line line, Value returnValue){
		switch(op){
			case "make":{
				doMake(line);
				break;
			}
			case "thing":{
				doThing(line, returnValue);
				break;
			}
			case ":":{
				doThing(line, returnValue);
				break;
			}
			case "erase":{
				doErase(line);
				break;
			}
			case "print":{
				doPrint(line);
				break;
			}
			case "isname":{
				doIsname(line, returnValue);
				break;
			}
			case "read":{
				doRead(line, returnValue);
				break;
			}
			case "readlist":{
				doReadlist(line, returnValue);
			    break;
			}
			case "add":
			case "sub":
			case "mul":
			case "div":
			case "mod":{
				doOperator(op, line, returnValue);
			    break;
			}
			case "eq":
			case "gt":
			case "lt":{
				doCompare(op, line ,returnValue);
				break;
			}
			case "and":
			case "or":{
				doDoublebool(op, line, returnValue);
			    break;
			}
			case "not":{
				doNot(line, returnValue);
				break;
			}
			case "random":{
				doRandom(line, returnValue);
				break;
			}
			case "sqrt":{
				doSqrt(line, returnValue);
				break;
			}
			case "isnumber":
			case "isword":
			case "islist":
			case "isbool":
			case "isempty":{
				doIsvalue(op, line, returnValue);
				break;
			}
			case "test":{
				doTest(line,returnValue);
				break;
			}
			case "iftrue":
			case "iffalse":
			case "if":{
				doIf(op, line);
				break;
			}
			case "word":{
				doWord(line, returnValue);
				break;
			}
			case "list":{
				doList(line, returnValue);
				break;
			}
			case "join":{
				doJoin(line, returnValue);
				break;
			}
			case "first":
			case "last":
			case "butfirst":
			case "butlast":{
				doFirst(op, line, returnValue);
				break;
			}
			case "item":{
				doItem(line, returnValue);
				break;
			}
			case "repeat":{
				doRepeat(line);
				break;
			}
			case "stop":{
				doStop(line);
				break;
			}
			case "wait":{
				doWait(line);
				break;
			}
			case "erall":
			case "poall":{
				doSpace(op);
				break;
			}
			case "output":{
				doOutput(line, returnValue);
                break;
			}
			case "pi":{
				returnValue.type = returnValue.NUMBER;
				returnValue.number = 3;
				break;
			}
			case "run":{
				doRun(line);
				break;
			}
			default:
				doFunction(op, line, returnValue);
		}
	}
}