package nro.services.func;

/**
 *
 * @MinhDepZai
 * @copyright 💖 GirlkuN 💖
 *
 */
public class InventoryServiceNew {

    private static InventoryServiceNew i;

    public static InventoryServiceNew gI() {
        if (i == null) {
            i = new InventoryServiceNew();
        }
        return i;
    }

}
