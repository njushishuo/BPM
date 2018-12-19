package com.github.florent37.materialviewpager.sample.rest;

import com.github.florent37.materialviewpager.sample.model.User;
import com.github.florent37.materialviewpager.sample.model.UserList;
import com.github.florent37.materialviewpager.sample.util.RestInfo;

@RestInfo(path = "User", object = User.class, array = UserList.class)
public class UserDAO extends BaseDAO<User, UserList> {
}
