package org.bodyguide_sv.weight.controller.request;

import java.time.LocalDateTime;

public record WeightRecordRequest(
    double weight,
    LocalDateTime recordAt
) {
    
}
