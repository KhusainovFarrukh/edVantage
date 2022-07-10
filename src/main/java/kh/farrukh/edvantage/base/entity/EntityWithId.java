package kh.farrukh.edvantage.base.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class EntityWithId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
