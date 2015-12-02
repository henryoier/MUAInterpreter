package com.Interpreter;

class ErrorHandle{
	//Define each type of errors
	public final int GETOPERROR = 0;
	public final int GETWORDERROR = 1;
	public final int GETBOOLERROR = 2;
	public final int GETVALUEERROR = 3;
	public final int READERROR = 4;
	public final int TYPEERROR = 5;
	public final int COMPAREERROR = 6;
	public final int NAMESPACEERROR = 7;
	public final int ERASEERROR = 8;
	public final int FUNCTIONNAMEERROR = 9;
	
	//Print error information and halt
	public void Handle(int ErrorType){
		switch(ErrorType){
			case GETOPERROR:{
				System.out.println("ERROR:Get operator error!");
				System.out.println("MUA aborted!");
				System.exit(0);
				break;
			}
			case GETWORDERROR:{
				System.out.println("ERROR:Get word error!");
				System.out.println("MUA aborted!");
				System.exit(0);
				break;
			}
			case GETBOOLERROR:{
				System.out.println("ERROR:Get boolean error!");
				System.out.println("MUA aborted!");
				System.exit(0);
				break;
			}
			case GETVALUEERROR:{
				System.out.println("ERROR:Get value error!");
				System.out.println("MUA aborted!");
				System.exit(0);
				break;
			}
			case READERROR:{
				System.out.println("ERROR:Read argument is neither word nor number!");
				System.out.println("MUA aborted!");
				System.exit(0);
				break;
			}
			case TYPEERROR:{
				System.out.println("Argument type is error");
				System.out.println("MUA aborted!");
				System.exit(0);
				break;
			}
			case COMPAREERROR:{
				System.out.println("Compare arguments types are not same");
				System.out.println("MUA aborted!");
				System.exit(0);
				break;
			}
			case NAMESPACEERROR:{
				System.out.println("Name exists or illegal!");
				System.out.println("MUA aborted!");
				System.exit(0);
				break;
			}
			case ERASEERROR:{
				System.out.println("Name do not exists, can not erase!");
				System.out.println("MUA aborted!");
				System.exit(0);
				break;
			}
			case FUNCTIONNAMEERROR:{
				System.out.println("No such function!");
				System.out.println("MUA aborted!");
				System.exit(0);
				break;
			}
	    }
	}
}
