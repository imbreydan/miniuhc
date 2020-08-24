package xyz.breydan.tag.command.adapter;

import me.idriz.oss.commands.CommandAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.breydan.tag.Tag;
import xyz.breydan.tag.profile.Profile;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileCommandAdapter implements CommandAdapter<Profile> {

    @Override
    public Profile convert(CommandSender commandSender, String s, String[] strings, String[] strings1, Parameter parameter) {
        return Tag.getInstance().getProfileManager().load(Bukkit.getPlayer(s).getUniqueId()).join();
    }

    @Override
    public Profile getDefaultValue() {
        return null;
    }

    @Override
    public void onError(CommandSender commandSender, String s, String[] strings, String[] strings1, Parameter parameter) {
        commandSender.sendMessage(ChatColor.RED + "There is no profile existing under that name.");
    }

    @Override
    public List<String> tabComplete(CommandSender commandSender, String[] strings, String s, Parameter parameter) {
        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .collect(Collectors.toList());
    }
}
