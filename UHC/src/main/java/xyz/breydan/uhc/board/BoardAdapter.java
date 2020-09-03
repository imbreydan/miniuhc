package xyz.breydan.uhc.board;

import org.bukkit.entity.Player;
import org.selyu.ui.scoreboard.adapter.ScoreboardAdapter;
import org.selyu.ui.scoreboard.objective.ScoreboardObjective;
import org.selyu.ui.scoreboard.title.ScoreboardTitle;
import xyz.breydan.uhc.UHC;
import xyz.breydan.uhc.game.Game;
import java.util.List;
import java.util.UUID;

public class BoardAdapter implements ScoreboardAdapter {

    private Game game = UHC.getInstance().getGame();

    private final Player player;

    public BoardAdapter(Player player) {
        this.player = player;
    }

    @Override
    public ScoreboardTitle getTitle() {
        return ScoreboardTitle.of(true, 20, "&6&lTHE BOYS", "&lTHE BOYS");
    }

    @Override
    public List<ScoreboardObjective> getObjectives() {
        return ScoreboardObjective.builder()
                .addObjective("")
                .addObjective("&6Game&7: &e" + game.getFormattedDuration())
                .addObjective("&6Team&7: &e" + getTeamName(player.getUniqueId()))
                .addObjective("")
                .addObjective("&6Border:")
                .addObjective("  &6Current&7: &e" + game.getBorder())
                .addObjective("  &6Next&7: &e" + game.getNextBorder() + " &7(" + game.getFormattedBorderTime() + ")")
                .addObjective("")
                .build();
    }

    public String getTeamName(UUID uuid) {
        boolean alpha = game.getAlphas().contains(uuid);
        boolean beta = game.getBetas().contains(uuid);
        if (alpha) return "Alpha";
        if (beta) return "Beta";
        return "N/A";
    }
}
