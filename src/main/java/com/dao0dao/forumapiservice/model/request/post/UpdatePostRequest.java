package com.dao0dao.forumapiservice.model.request.post;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpdatePostRequest extends PostRequest {

    @NotNull(message = "Version can not be empty")
    private Long version;
}
