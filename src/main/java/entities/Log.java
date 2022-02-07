package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Bekpulatov Shoxruh, Sun 1:29 PM. 12/19/2021
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Log extends Auditable {
    private String data;
    private Long id;
    private String createdAt;
}
