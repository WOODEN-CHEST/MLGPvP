package sus.keiger.mlgpvp.game.entity.player.component;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.util.Vector;
import sus.keiger.mlgpvp.game.entity.component.GameEntityComponent;
import sus.keiger.mlgpvp.game.entity.player.GamePlayerEntity;

import java.util.Optional;


public class PlayerYCoordBooster extends GameEntityComponent<GamePlayerEntity>
{
    // Private static fields.

    /* The max motion is limited to 4.095875 blocks per second because of how the set motion packet
    * is encoded. To achieve higher motion, prolonged boosting is required.
    * This class doesn't aim to mimic the motion as if it was actually higher than the limit, rather it serves
    * as a way to boost the player higher upwards thank ~60 blocks.
    * See https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Entity_Velocity */
    private static final double MAX_PACKET_MOTION = Short.MAX_VALUE / 8000d;
    private static final double Y_POS_CHECK_MARGIN_OF_ERROR = 0.00001d;



    // Private fields.
    private double _remainingYBoost = 0d;
    private double _previousYPos;


    // Constructors.
    public PlayerYCoordBooster(GamePlayerEntity entity)
    {
        super(entity);
    }


    // Methods.
    public void SetRemainingYBoost(double amount)
    {
        if (Double.isNaN(amount) || Double.isInfinite(amount))
        {
            throw new IllegalArgumentException("Y Boost must not be NaN or infinite: %f".formatted(amount));
        }
        _remainingYBoost = Math.max(amount, 0d);
    }


    // Private methods.
    private void TickYBoost()
    {
        Location CurrentLocation = GetEntity().GetLocation();

        TryPreventStaticBoosting(CurrentLocation);
        if (_remainingYBoost > MAX_PACKET_MOTION)
        {
            Vector Motion = GetEntity().GetMotion();
            Motion.setY(MAX_PACKET_MOTION);
            GetEntity().SetMotion(Motion);
        }
        DecreaseBoost();
        _previousYPos = CurrentLocation.getY();
    }

    private void TryPreventStaticBoosting(Location currentLocation)
    {
        if (Math.abs(currentLocation.getY() - _previousYPos) <= Y_POS_CHECK_MARGIN_OF_ERROR)
        {
            _remainingYBoost = 0d;
        }
    }

    private void DecreaseBoost()
    {
        Optional<AttributeInstance> GravityAttribute = GetEntity().GetAttributeInstance(Attribute.GRAVITY);
        GravityAttribute.ifPresent(attributeInstance ->
                _remainingYBoost = Math.max(0d, _remainingYBoost - attributeInstance.getValue()));
    }


    // Inherited methods.
    @Override
    public void Tick()
    {
        super.Tick();
        TickYBoost();
    }
}