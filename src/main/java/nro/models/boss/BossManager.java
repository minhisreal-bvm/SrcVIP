package nro.models.boss;

import nro.utils.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nro.models.boss.boss_ban_do_kho_bau.BossBanDoKhoBau;
import nro.models.boss.boss_doanh_trai.BossDoanhTrai;
import nro.models.boss.cdrd.CBoss;
import nro.models.boss.dhvt.BossDHVT;
import nro.models.boss.event.SantaClaus;
import nro.models.boss.mabu_war.BossMabuWar;
import nro.models.player.Player;
import nro.server.io.Message;
import nro.utils.Util;

public class BossManager {

    public static final List<Boss> BOSSES_IN_GAME;
    private static BossManager intance;

    // Các map boss sẽ bị ẩn và không thể teleport tới
    private static final List<Integer> HIDDEN_MAPS = Arrays.asList(213, 214, 215);

    static {
        BOSSES_IN_GAME = new ArrayList<>();
    }

    private BossManager() {}

    public static BossManager gI() {
        if (intance == null) {
            intance = new BossManager();
        }
        return intance;
    }

    public void updateAllBoss() {
        for (int i = BOSSES_IN_GAME.size() - 1; i >= 0; i--) {
            try {
                Boss boss = BOSSES_IN_GAME.get(i);
                if (boss != null) boss.update();
            } catch (Exception e) {
                Log.error(BossManager.class, e);
            }
        }
    }

    public Boss getBossById(byte bossId) {
        for (Boss boss : BOSSES_IN_GAME) {
            if (boss.id == bossId) {
                if (boss.zone != null && HIDDEN_MAPS.contains(boss.zone.map.mapId)) {
                    return null; // Boss ở map ẩn -> không cho teleport
                }
                return boss;
            }
        }
        return null;
    }

    public Boss getBossByIdRandom(int bossId) {
        for (Boss boss : BOSSES_IN_GAME) {
            if (boss.id == bossId) {
                if (boss.zone != null && HIDDEN_MAPS.contains(boss.zone.map.mapId)) {
                    return null; // Boss ở map ẩn -> không cho teleport
                }
                return boss;
            }
        }
        return null;
    }

    public long getBossidlist(List<Boss> BOSSES_IN_GAME) {
        for (int i = BOSSES_IN_GAME.size() - 1; i >= 0; i--) {
            return BOSSES_IN_GAME.get(i).id;
        }
        return -1;
    }

    public void addBoss(Boss boss) {
        boolean have = false;
        for (Boss b : BOSSES_IN_GAME) {
            if (boss.equals(b)) {
                have = true;
                break;
            }
        }
        if (!have) {
            BOSSES_IN_GAME.add(boss);
        }
    }

    public List<Boss> getBosses() {
        return BossManager.BOSSES_IN_GAME;
    }

    public void removeBoss(Boss boss) {
        BOSSES_IN_GAME.remove(boss);
        boss.dispose();
    }

    public void showListBoss(Player player) {
        Message msg = new Message(-96);
        try {
            msg.writer().writeByte(0);
            msg.writer().writeUTF("List BOSS");

            int count = (int) BOSSES_IN_GAME.stream()
                    .filter(boss -> !(boss instanceof CBoss)
                            && !(boss instanceof BossMabuWar)
                            && !(boss instanceof BossDHVT)
                            && !(boss instanceof SantaClaus)
                            && !(boss instanceof BossDoanhTrai)
                            && !(boss instanceof BossBanDoKhoBau)
                            && (boss.zone == null || !HIDDEN_MAPS.contains(boss.zone.map.mapId)))
                    .count();

            msg.writer().writeByte(count > 120 ? 120 : count);

            for (Boss boss : BOSSES_IN_GAME) {
                if ((boss instanceof CBoss)
                        || (boss instanceof BossMabuWar)
                        || (boss instanceof BossDHVT)
                        || (boss instanceof SantaClaus)
                        || (boss instanceof BossDoanhTrai)
                        || (boss instanceof BossBanDoKhoBau)) {
                    continue;
                }

                if (boss.zone != null && HIDDEN_MAPS.contains(boss.zone.map.mapId)) {
                    continue;
                }

                msg.writer().writeInt((int) boss.id);
                msg.writer().writeInt((int) boss.id);
                msg.writer().writeShort(boss.data.outfit[0]);
                if (player.isVersionAbove(220)) {
                    msg.writer().writeShort(-1);
                }
                msg.writer().writeShort(boss.data.outfit[1]);
                msg.writer().writeShort(boss.data.outfit[2]);
                msg.writer().writeUTF(boss.data.name);
                msg.writer().writeUTF(boss.zone != null ? "Sống" : "Chết rồi");
                if (boss.zone != null) {
                    msg.writer().writeUTF("Map xuất hiện: " + boss.zone.map.mapName + " khu " + boss.zone.zoneId
                            + "\nMáu: " + Util.powerToStringnew(boss.nPoint.hp));
                } else {
                    msg.writer().writeUTF("Chưa xuất hiện: Chờ đi");
                }
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showListBossMember(Player player) {
        Message msg = new Message(-96);
        try {
            msg.writer().writeByte(0);
            msg.writer().writeUTF("List BOSS");

            int count = (int) BOSSES_IN_GAME.stream()
                    .filter(boss -> !(boss instanceof CBoss)
                            && !(boss instanceof BossMabuWar)
                            && !(boss instanceof BossDHVT)
                            && !(boss instanceof SantaClaus)
                            && !(boss instanceof BossDoanhTrai)
                            && !(boss instanceof BossBanDoKhoBau)
                            && (boss.zone == null || !HIDDEN_MAPS.contains(boss.zone.map.mapId)))
                    .count();

            msg.writer().writeByte(count > 120 ? 120 : count);

            for (Boss boss : BOSSES_IN_GAME) {
                if ((boss instanceof CBoss)
                        || (boss instanceof BossMabuWar)
                        || (boss instanceof BossDHVT)
                        || (boss instanceof SantaClaus)
                        || (boss instanceof BossDoanhTrai)
                        || (boss instanceof BossBanDoKhoBau)) {
                    continue;
                }

                if (boss.zone != null && HIDDEN_MAPS.contains(boss.zone.map.mapId)) {
                    continue;
                }

                msg.writer().writeInt((int) boss.id);
                msg.writer().writeInt((int) boss.id);
                msg.writer().writeShort(boss.data.outfit[0]);
                if (player.isVersionAbove(220)) {
                    msg.writer().writeShort(-1);
                }
                msg.writer().writeShort(boss.data.outfit[1]);
                msg.writer().writeShort(boss.data.outfit[2]);
                msg.writer().writeUTF(boss.data.name);
                msg.writer().writeUTF(boss.zone != null ? "Sống" : "Chết mẹ rồi");
                if (boss.zone != null) {
                    msg.writer().writeUTF("Máu: " + Util.powerToStringnew(boss.nPoint.hp)
                            + "\nDịch chuyển tới và giảng hoà nào!");
                } else {
                    msg.writer().writeUTF("Chưa xuất hiện: Chờ đi");
                }
            }
            player.sendMessage(msg);
            msg.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}