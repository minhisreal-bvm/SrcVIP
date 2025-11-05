package nro.dialog;

import lombok.Getter;
import lombok.Setter;

/**
 * @MinhDepZai
 */
@Setter
@Getter
public abstract class MenuRunable implements Runnable {
    private int indexSelected;
}
