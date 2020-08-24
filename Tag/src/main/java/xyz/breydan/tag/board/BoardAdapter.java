package xyz.breydan.tag.board;

import org.bukkit.entity.Player;
import org.selyu.ui.scoreboard.adapter.ScoreboardAdapter;
import org.selyu.ui.scoreboard.objective.ScoreboardObjective;
import org.selyu.ui.scoreboard.title.ScoreboardTitle;
import xyz.breydan.tag.Tag;
import xyz.breydan.tag.game.Game;
import xyz.breydan.tag.profile.Profile;

import java.util.List;
import java.util.UUID;

public class BoardAdapter implements ScoreboardAdapter {

    private final Profile profile;

    private Game game = Tag.getInstance().getGameManager().getGame();

    public BoardAdapter(Player player) {
        profile = Tag.getInstance().getProfileManager().load(player.getUniqueId()).join();
    }

    @Override
    public ScoreboardTitle getTitle() {
        return ScoreboardTitle.of(true, 1,
                "&6&lTHE BOYS",
                "&lT&6&lHE BOYS",
                "&lTH&6&lE BOYS",
                "&lTHE&6&l BOYS",
                "&lTHE B&6&lOYS",
                "&lTHE BO&6&lYS",
                "&lTHE BOY&6&lS",
                "&lTHE BOYS",
                "&6&lTHE BOYS",
                "&lTHE BOYS",
                "&6&lTHE BOYS",
                "&lTHE BOYS");
    }

    @Override
    public List<ScoreboardObjective> getObjectives() {
        return ScoreboardObjective.builder()
                .addObjective("")
                .addObjective("&6Game&7: &e" + game.getFormattedDuration())
                .addObjective("&6Team&7: &e" + getTeamName(profile.getUuid()))
                .addObjective("&6Status&7: &e" + profile.getProfileState().name())
                .addObjective("")
                .addObjective("&6Border:")
                .addObjective("  &6Current&7: &e" + game.getBorder())
                .addObjective("  &6Next&7: &e" + game.getNextBorder() + " &7(" + game.getFormattedBorderTime() + ")")
                .addObjective("")
                .build();
    }

    public String getTeamName(UUID uuid) {
        boolean hunters = game.getHunters().contains(uuid);
        boolean runners = game.getRunners().contains(uuid);
        if (hunters) return "Hunters";
        if (runners) return "Runners";
        return "N/A";
    }
}
