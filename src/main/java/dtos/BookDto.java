package dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Bekpulatov Shoxruh, Sun 2:33 PM. 12/26/2021
 */
@Getter
@Setter
@Builder
public class BookDto {
    String Id;
    String title;
    int downloadCount;
}
