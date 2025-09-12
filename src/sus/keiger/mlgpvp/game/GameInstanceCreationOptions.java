package sus.keiger.mlgpvp.game;

import sus.keiger.mlgpvp.service.IServerServices;

/**
 * Options for a game instance's creation.
 * @param Services The server's services, which will be used.
 * @param Values The configuration the game should use.
 */
public record GameInstanceCreationOptions(IServerServices Services, GameInstanceValues Values) { }