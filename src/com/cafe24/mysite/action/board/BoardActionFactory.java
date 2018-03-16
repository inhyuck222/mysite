package com.cafe24.mysite.action.board;

import com.cafe24.mvc.action.AbstractActionFactory;
import com.cafe24.mvc.action.Action;

public class BoardActionFactory extends AbstractActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if(actionName == null) {
			actionName = "";
		}
		
		switch(actionName) {
		case "newpostwrite" : 
			action = new NewPostWriteAction();
			break;
		case "newpostwriteform" :
			action = new NewPostWriteFormAction();
			break;
		case "list" :
			action = new ListAction();
			break;
		default :
			action = new ListAction();
			break;
		}		
		
		return action;
	}

}
