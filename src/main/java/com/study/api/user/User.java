package com.study.api.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value={"password", "ssn"}) - return json 무시 여러개 설정
@JsonFilter("UserInfo")
@NoArgsConstructor
public class User {

    private Integer id;
    private String name;
    private Date joinData;

//    @JsonIgnore - 리턴 json에 무시
    private String password;
//    @JsonIgnore
    private String ssn;
}
