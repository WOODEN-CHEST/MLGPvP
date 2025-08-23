package sus.keiger.mlgpvp.event;

public interface IMLGPvPEventListener
{
    void SubscribeToEvents(IEventDispatcher dispatcher);
    void UnsubscribeFromEvents(IEventDispatcher dispatcher);
}