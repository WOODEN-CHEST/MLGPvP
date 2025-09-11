package sus.keiger.mlgpvp.event;

/**
 * An object which implements this interface is one which requires subscribing to events to function.
 * <br>This doesn't necessarily mean that it only subscribes to the events dispatched from the passed in event
 * dispatcher. It could subscribe to any event available to it, the disptacher is simply passed in as the default
 * event dispatcher.
 */
public interface IMLGPvPEventListener
{
    void SubscribeToEvents(IEventDispatcher dispatcher);
    void UnsubscribeFromEvents(IEventDispatcher dispatcher);
}