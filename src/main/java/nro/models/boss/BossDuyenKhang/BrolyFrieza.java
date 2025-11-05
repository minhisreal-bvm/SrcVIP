package nro.models.boss.BossDuyenKhang;

import nro.consts.ConstItem;
import nro.consts.ConstRatio;
import nro.models.boss.Boss;
import nro.models.boss.BossData;
import nro.models.boss.BossFactory;
import nro.models.item.ItemOption;
import nro.models.map.ItemMap;
import nro.models.player.Player;
import nro.server.Manager;
import nro.services.RewardService;
import nro.services.Service;
import nro.utils.Util;

import nro.models.boss.BossManager;
import nro.services.SkillService;
import nro.utils.SkillUtil;

/**
 * @MinhDepZai
 * @copyright 💖 GirlkuN 💖
 */
public class BrolyFrieza extends Boss {

    public BrolyFrieza() {
        super(BossFactory.BROLYFRIEZA, BossData.BROLYFRIEZA);
    }

    @Override
    protected boolean useSpecialSkill() {
        return false;
    }

    @Override
    public long injured(Player plAtt, long damage, boolean piercing, boolean isMobAttack) {
        if (damage >= 10_000_000_000L) {
            damage = damage;
        }
        return super.injured(plAtt, damage, piercing, isMobAttack);
    }

    @Override
    public void attack() {
        try {
            Player pl = getPlayerAttack();
            if (pl != null) {
                if (!useSpecialSkill()) {
                    this.playerSkill.skillSelect = this.getSkillAttack();
                    if (Util.getDistance(this, pl) <= this.getRangeCanAttackWithSkillSelect()) {
                        if (Util.isTrue(15, ConstRatio.PER100) && SkillUtil.isUseSkillChuong(this)) {
                            goToXY(pl.location.x + (Util.getOne(-1, 1) * Util.nextInt(20, 80)),
                                    Util.nextInt(10) % 2 == 0 ? pl.location.y : pl.location.y - Util.nextInt(0, 50), false);
                        }
                        SkillService.gI().useSkill(this, pl, null, null);
                        checkPlayerDie(pl);
                    } else {
                        goToPlayer(pl, false);
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void rewards(Player pl) {
        int a = 0;
        // do than 1/20
        int[] tempIds1 = new int[]{555, 556, 563, 557, 558, 565, 559, 567, 560};
        // Nhan, gang than 1/30
        int[] tempIds2 = new int[]{562, 564, 566, 561};
        int tempId = -1;

        if (Util.isTrue(20, 100)) {
            tempId = tempIds1[Util.nextInt(0, tempIds1.length - 1)];
        } else if (Util.isTrue(20, 100)) {
            tempId = tempIds2[Util.nextInt(0, tempIds2.length - 1)];
        }
        if (Manager.EVENT_SEVER == 4 && tempId == -1) {
            tempId = ConstItem.LIST_ITEM_NLSK_TET_2023[Util.nextInt(0, ConstItem.LIST_ITEM_NLSK_TET_2023.length - 1)];
        }
        if (tempId != -1) {
            ItemMap itemMap = new ItemMap(this.zone, tempId, 1,
                    pl.location.x, this.zone.map.yPhysicInTop(pl.location.x, pl.location.y - 24), pl.id);
            RewardService.gI().initBaseOptionClothes(itemMap.itemTemplate.id, itemMap.itemTemplate.type, itemMap.options);
            RewardService.gI().initStarOption(itemMap, new RewardService.RatioStar[]{
                new RewardService.RatioStar((byte) 1, 1, 2),
                new RewardService.RatioStar((byte) 2, 1, 3),
                new RewardService.RatioStar((byte) 3, 1, 4),
                new RewardService.RatioStar((byte) 4, 1, 5),
                new RewardService.RatioStar((byte) 5, 1, 6),
                new RewardService.RatioStar((byte) 6, 1, 7),
                new RewardService.RatioStar((byte) 7, 1, 8)
            });
            Service.getInstance().dropItemMap(this.zone, itemMap);
        }
        generalRewards(pl);
        //đồ cũ
        if (Util.isTrue(1, 200)) {
            
            ItemMap itemMap = new ItemMap(this.zone, 1547, 1,
                    this.location.x - 10, this.zone.map.yPhysicInTop(this.location.x, this.location.y - 24), pl.id);
            
            Service.getInstance().dropItemMap(this.zone, itemMap);
        } else if (Util.isTrue(25, 100)) {
            for (int i = 0; i < 10; i++) {
                ItemMap itemMap = new ItemMap(this.zone, 1533, 1,
                        this.location.x + a, this.zone.map.yPhysicInTop(this.location.x, this.location.y - 24), -1);
                Service.getInstance().dropItemMap(this.zone, itemMap);
                a += 10;
            }
        } else if (Util.isTrue(10, 100)) {
            for (int j = 0; j < 30; j++) {
                ItemMap itemMap1 = new ItemMap(this.zone, 1535, 20,
                        this.location.x + a, this.zone.map.yPhysicInTop(this.location.x, this.location.y - 24), -1);
                Service.getInstance().dropItemMap(this.zone, itemMap1);
                a += 10;
            }
        } else {
            for (int j = 0; j < 20; j++) {
                ItemMap itemMap1 = new ItemMap(this.zone, 1441, 2,
                        this.location.x + a, this.zone.map.yPhysicInTop(this.location.x, this.location.y - 24), -1);
                Service.getInstance().dropItemMap(this.zone, itemMap1);
                a += 10;
            }
        }
    }

    @Override
    public void idle() {

    }

    @Override
    public void checkPlayerDie(Player pl) {

    }

    @Override
    public void initTalk() {
        this.textTalkMidle = new String[]{"Lũ Kiến Hôi", "Cút mẹ mày đi.",
            "Từ lớp 1 đến lớp 5, tao còn chưa sợ.", "Nhìn mày bẩn mắt vãi lồn."};

    }

    @Override
    public void leaveMap() {
        BossFactory.createBoss(BossFactory.BROLYFRIEZA).setJustRest();
        super.leaveMap();
        BossManager.gI().removeBoss(this);
    }

}