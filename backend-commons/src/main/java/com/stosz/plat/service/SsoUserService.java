package com.stosz.plat.service;


import com.stosz.plat.common.MBox;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * 从redis中获取用户信息
 */
public class SsoUserService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 从 cookie 中根据ticket从redis中获取当前登录用户
     * @param request
     * @return
     */
    public UserDto getCurrentUserDto(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies==null|| cookies.length==0)
            return null;
        Optional<Cookie> cookieOptional =  Stream.of(cookies).filter(e-> e.getName().equalsIgnoreCase(MBox.COOKIE_TICKET_KEY)).findFirst();
        return cookieOptional.isPresent() ? getCurrentUserDto(cookieOptional.get().getValue()) : null;
    }

    /**
     * 通过ticket 从redis 中获取用户
     * @param ticket
     * @return
     */
    public UserDto getCurrentUserDto(String ticket){

        String userDtoJson = stringRedisTemplate.opsForValue().get(ticket);
        return JsonUtil.readValue(userDtoJson, UserDto.class);
    }


    /**
     * 将用户设置到cookie中，并将 ticket 对应的用户设置到redis中
     * @param userDto
     * @param response
     * @return
     */
    public String  setUserToRedis(UserDto userDto,HttpServletResponse response) {
        String ticketValue = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(ticketValue, JsonUtil.toJson(userDto));
        setTicketToCookie(ticketValue , response);
        return ticketValue;
    }

    public void setTicketToCookie(String ticketValue ,HttpServletResponse response){
        Cookie cookie = new Cookie(MBox.COOKIE_TICKET_KEY , ticketValue);
        cookie.setMaxAge(15*24*60*60);
        cookie.setDomain(".stosz.com");
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void logout(HttpServletRequest request , HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies==null|| cookies.length==0)
            return ;
        Optional<Cookie> cookieOptional =  Stream.of(cookies).filter(e-> e.getName().equalsIgnoreCase(MBox.COOKIE_TICKET_KEY)).findFirst();
        String ticket = "";
        if (cookieOptional.isPresent()) {
            Cookie cookie = cookieOptional.get();
            ticket = cookie.getValue();
            stringRedisTemplate.delete(ticket);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

    }

}
