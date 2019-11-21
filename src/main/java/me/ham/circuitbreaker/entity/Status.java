package me.ham.circuitbreaker.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Status {

    @Id @GeneratedValue
    private Long id;

    private String state;

}
