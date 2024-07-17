package com.example.TestJava.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseModelUuid{

    private String user_name;
    private String user_password;
    private String email;
    private String token;
    private LocalDateTime creation_date;
    private LocalDateTime modified_date;
    private LocalDateTime last_login;
    private boolean active;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phones;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.modified_date = now;
        this.creation_date = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.modified_date = LocalDateTime.now();
    }

}
