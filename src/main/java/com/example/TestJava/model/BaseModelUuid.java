package com.example.TestJava.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseModelUuid {

    @Id
    private UUID uuid = UUID.randomUUID();
}
