package org.bodyguide_sv.exercise.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MUSCLE_GROUP")
public class MuscleGroup {
    
    @Id
    @Column(name = "group_id", columnDefinition = "TINYINT")
    private int groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "strength")
    private String strength;

}
