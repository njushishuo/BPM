package com.example.lpp.examinationsystem.rest;

import com.example.lpp.examinationsystem.model.User;
import com.example.lpp.examinationsystem.model.UserList;
import com.example.lpp.examinationsystem.util.RestInfo;

@RestInfo(path = "User", object = User.class, array = UserList.class)
public class UserDAO extends BaseDAO<User, UserList> {
}
