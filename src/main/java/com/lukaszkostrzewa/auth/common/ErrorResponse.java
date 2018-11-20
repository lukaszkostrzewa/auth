package com.lukaszkostrzewa.auth.common;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author Lukasz Kostrzewa (SG0221165)
 * @since Nov 19, 2018
 */
@Data
@Builder
class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String message;
    @Builder.Default
    private List<String> details = Collections.emptyList();
}
