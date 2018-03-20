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
		case "delete" :
			action = new DeleteAction();
			break;
		case "repostwrite" : 
			action = new RepostWriteAction();
			break;
		case "repostwriteform" : 
			action = new RepostWriteFormAction();
			break;
		case "modify" : 
			action = new ModifyAction();
			break;
		case "modifyform" :
			action = new ModifyFormAction();
			break;
		case "view" :
			action = new ViewAction();
			break;
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
