package com.unibridge.app.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Execute;
import com.unibridge.app.Result;

public class MentorVerifyController implements Execute{

	private Result outResult = new Result();

    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	System.out.println("===MentorVerifyController===");

        String method = request.getMethod().toUpperCase();

        switch (method) {
            case "GET":
                doGet(request, response);
                break;
            case "POST":
                doPost(request, response);
                break;
        }

        return outResult;
    }

	private void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		outResult.setPath("/app/user/mentor/myPage/userManage/userModifyCheck.jsp");
	    outResult.setRedirect(false);
		
	}

	private void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

}
