package com.projectkorra.projectkorra.region;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.*;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class Towny extends RegionProtectionBase {

    private final List<String> allowedTownyAbilities;

    protected Towny() {
        super("Towny");
        allowedTownyAbilities = ConfigManager.getConfig().getStringList("Properties.RegionProtection.Towny.AllowedAbilities");
    }

    @Override
    public boolean isRegionProtectedReal(Player player, Location location, CoreAbility ability, boolean igniteAbility, boolean explosiveAbility) {
        TownBlock townBlock = TownyAPI.getInstance().getTownBlock(location);
        boolean canBuild = PlayerCacheUtil.getCachePermission(player, location, Material.DIRT, TownyPermission.ActionType.BUILD);
        if (townBlock == null) {
            return !canBuild;
        }

        if (townBlock.getType().equals(TownBlockType.ARENA)) {
            return false;
        }
        if (allowedTownyAbilities.contains(ability.getName())) {
            return false;
        }
        return !canBuild;
    }
}
