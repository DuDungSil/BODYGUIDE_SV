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
@Table(name = "MUSCLE_GROUP_DETAIL")
public class MuscleGroupDetail {
    
    @Id
    @Column(name = "detail_id", columnDefinition = "SMALLINT")
    private int detailId;

    @Column(name = "group_id", columnDefinition = "TINYINT")
    private int groupId;

    @Column(name = "detail_muscle")
    private String detailMuscle;

}
