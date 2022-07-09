package com.study.api.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value={"password", "ssn"}) - return json 무시 여러개 설정
@JsonFilter("UserInfoV2")
public class UserV2 extends User{
    private String grade;
}
