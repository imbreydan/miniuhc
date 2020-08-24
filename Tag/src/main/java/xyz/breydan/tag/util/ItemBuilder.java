package xyz.breydan.tag.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

import static xyz.breydan.tag.util.TranslateUtils.color;

public final class ItemBuilder {

    private ItemStack item;
    private ItemMeta meta;

    private ItemBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public static ItemBuilder create(ItemStack item) {
        return new ItemBuilder(item);
    }

    public static ItemBuilder create(Material material, int amount) {
        return new ItemBuilder(new ItemStack(material, amount));
    }

    public static ItemBuilder create(Material material) {
        return create(material, 1);
    }

    public ItemBuilder withName(String name) {
        meta.setDisplayName(color(name));
        return this;
    }

    public ItemBuilder withLore(List<String> lore) {
        meta.setLore(color(lore));
        return this;
    }

    public ItemBuilder withLore(String... lore) {
        withLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder withLoreLine(String line) {
        if(meta.getLore() == null || meta.getLore().isEmpty()) {
            withLore(line);
            return this;
        }
        List<String> currentLore = meta.getLore();
        currentLore.add(line);
        meta.setLore(color(currentLore));
        return this;
    }

    public ItemBuilder setSkullOwner(String skullOwner) {
        if(meta instanceof SkullMeta) {
            ((SkullMeta) meta).setOwner(skullOwner);
            return this;
        }
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}

