package sus.keiger.mlgpvp.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import sus.keiger.mlgpvp.game.entity.GameEntity;
import sus.keiger.mlgpvp.player.IAudienceMember;
import sus.keiger.mlgpvp.player.IServerPlayer;
import sus.keiger.plugincommon.player.actionbar.ActionbarMessage;

import java.util.List;

public class MLGPvPGameInstance implements IGameInstanceExtended
{
    // Private fields.



    // Private methods.




    // Inherited methods.
    @Override
    public void AddEntity(GameEntity entity)
    {

    }

    @Override
    public void RemoveEntity(GameEntity entity)
    {

    }

    @Override
    public List<GameEntity> GetEntities()
    {
        return List.of();
    }

    @Override
    public int GetEntityCount()
    {
        return 0;
    }

    @Override
    public void SetState(GameInstanceState state)
    {

    }

    @Override
    public void AddPlayer(IServerPlayer player)
    {

    }

    @Override
    public void RemovePlayer(IServerPlayer player)
    {

    }

    @Override
    public int GetJoinedPlayerCount()
    {
        return 0;
    }

    @Override
    public List<IServerPlayer> GetJoinedPlayers()
    {
        return List.of();
    }

    @Override
    public int GetOnlinePlayerCount()
    {
        return 0;
    }

    @Override
    public List<IServerPlayer> GetOnlinePlayers()
    {
        return List.of();
    }

    @Override
    public void AddSpectator(IServerPlayer player)
    {

    }

    @Override
    public void RemoveSpectator(IServerPlayer player)
    {

    }

    @Override
    public int GetJoinedSpectatorCount()
    {
        return 0;
    }

    @Override
    public List<IServerPlayer> GetJoinedSpectators()
    {
        return List.of();
    }

    @Override
    public GameInstanceValues GetConfigValues()
    {
        return null;
    }

    @Override
    public void Start()
    {

    }

    @Override
    public void End()
    {

    }

    @Override
    public GameInstanceState GetState()
    {
        return null;
    }

    @Override
    public List<? extends IAudienceMember> GetAudienceMembers()
    {
        return List.of();
    }

    @Override
    public void ShowTitle(Title title)
    {

    }

    @Override
    public void ClearTitle()
    {

    }

    @Override
    public void SendMessage(Component message)
    {

    }

    @Override
    public void ShowActionbar(ActionbarMessage message)
    {

    }

    @Override
    public void ClearActionbar()
    {

    }

    @Override
    public void RemoveActionbar(long id)
    {

    }

    @Override
    public <T> void SpawnParticle(Particle particle,
                                  Location location,
                                  double deltaX,
                                  double deltaY,
                                  double deltaZ,
                                  int count,
                                  double extra,
                                  T data)
    {

    }

    @Override
    public void PlaySound(Sound sound, Location location, SoundCategory category, float volume, float pitch)
    {

    }

    @Override
    public void Tick()
    {

    }
}