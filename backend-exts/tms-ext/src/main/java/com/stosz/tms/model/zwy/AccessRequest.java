package com.stosz.tms.model.zwy;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "AccessRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccessRequest implements Serializable{

    @XmlElement(name = "username",nillable=true)
    private String username;

    @XmlElement(name = "password",nillable=true)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
