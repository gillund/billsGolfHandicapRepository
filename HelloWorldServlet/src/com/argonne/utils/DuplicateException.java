package com.argonne.utils;

public class DuplicateException extends Throwable{
	public DuplicateException (String astring){
		Log.information(astring.toString());
	}

}
