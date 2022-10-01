package com.dwgj.mlxydedicatedline.commons.exception;

import javax.security.auth.login.AccountException;

public class CustomException extends AccountException {

    public CustomException(){
        super();
    }
    public CustomException(String msg){
        super(msg);
    }
}
