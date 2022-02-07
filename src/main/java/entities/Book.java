package entities;

import enums.Criteria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Bekpulatov Shoxruh, Wed 1:37 AM. 12/22/2021
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book extends Auditable {
    private String Title;
    private String Author;
    private String ownerChatId;
    private String fileId;
    private Criteria criteria;
}
