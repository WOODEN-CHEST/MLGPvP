package sus.keiger.mlgpvp.player;

import sus.keiger.mlgpvp.event.IMLGPvPEventListener;
import sus.keiger.plugincommon.ITickable;

/* Ensures that all server players have a valid state (either in a game, not in a game or a spectator). */
public interface IPlayerStateController extends IMLGPvPEventListener, ITickable { }