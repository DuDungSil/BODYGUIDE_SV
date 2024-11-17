package org.hepi.hepi_sv.exercise.entity;

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
@Table(name = "muscle_group_detail")
public class MuscleGroupDetail {
    
    @Id
    @Column(name = "detail_id")
    private long detailId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "detail_muscle")
    private String detailMuscle;

}
