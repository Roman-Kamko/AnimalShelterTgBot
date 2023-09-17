package com.team2.animalshelter.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "adaptations")
@ToString(exclude = "adaptations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Owner extends BaseEntity {

    String phoneNumber;

    @OneToMany(mappedBy = "owner")
    List<Adaptation> adaptations;

}
