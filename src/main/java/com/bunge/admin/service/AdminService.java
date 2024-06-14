package com.bunge.admin.service;


public interface AdminService {

    void insertVisitor(String username);

    int getVisitorCount();

    int getjoinCount();

    int getstudyCount();

    int getreviewCount();
}
