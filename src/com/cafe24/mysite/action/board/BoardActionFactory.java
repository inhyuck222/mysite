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
		/*
		 * g_no(2) = 유지 => 2
		 * o_no(1) = o_no(1) + 1 => 2
		 * d(0) = d + 1 => 1
		 * update o_no = o_no+1 where g_no = 2 and o_no >= 2
		 * insert (~~~~)
		 */
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
