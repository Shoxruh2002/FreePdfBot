package entities;

import lombok.*;

import java.util.Date;

import static utils.BaseUtils.generateUniqueId;

/**
 * @author Bekpulatov Shoxruh, Sun 1:27 PM. 12/19/2021
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auditable implements BaseEntity{
    private Long id = generateUniqueId();
    private String createdAt = new Date().toString();
}
