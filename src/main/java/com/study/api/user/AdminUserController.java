package com.study.api.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service){
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers(){
        List<User> users = service.findAll();

        // 관리자만 유저 데이터에 접근가능하게 filtering 처리
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;

    }

    @GetMapping("/v1/users/{id}") // uri로 버젼관리
//    @GetMapping(value= "/users/{id}/", params = "version=1") // 파라메터로 버젼관리
//    @GetMapping(value= "/users/{id}/", headers="V=1") // 헤더로 버젼관리
//    @GetMapping(value= "/users/{id}/", produces="application/vnd.company.v1") // mime 타입
    public MappingJacksonValue retrieveUserV1(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // 관리자만 유저 데이터에 접근가능하게 filtering 처리
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;

    }

    @GetMapping("/v2/users/{id}")
//    @GetMapping(value= "/users/{id}/", params = "version=2")
//    @GetMapping(value= "/users/{id}/", headers="V=2") // 헤더로 버젼관리
//    @GetMapping(value= "/users/{id}/", produces="application/vnd.company.v2")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User -> User2

        UserV2 userV2 = new UserV2();

        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        // 관리자만 유저 데이터에 접근가능하게 filtering 처리
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);

        return mapping;

    }
    }
