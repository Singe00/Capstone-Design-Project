package com.example.CrtDgn.repository;

import javax.sql.DataSource;
import java.lang.reflect.Member;

public class JdbcMemberRepository{

    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }


}
