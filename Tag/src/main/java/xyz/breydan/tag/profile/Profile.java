package xyz.breydan.tag.profile;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import xyz.breydan.tag.game.Game;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Profile {

    private final UUID uuid;
    private int wins, losses, gamesPlayed;
    @BsonIgnore
    private ProfileState profileState = ProfileState.LOBBY;
    @BsonIgnore
    private Game game;

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public ProfileState getProfileState() {
        return profileState;
    }

    public void setProfileState(ProfileState profileState) {
        this.profileState = profileState;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "uuid=" + uuid +
                ", wins=" + wins +
                ", losses=" + losses +
                ", gamesPlayed=" + gamesPlayed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return getWins() == profile.getWins() &&
                getLosses() == profile.getLosses() &&
                getGamesPlayed() == profile.getGamesPlayed() &&
                getUuid().equals(profile.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getWins(), getLosses(), getGamesPlayed());
    }
}
