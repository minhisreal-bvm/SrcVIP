package nro.models.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @MinhDepZai
 */
@Setter
@Getter
@AllArgsConstructor
public class AchivementTemplate {

    private int id;

    private String name;

    private String detail;

    private int money;

    private int maxCount;
}
