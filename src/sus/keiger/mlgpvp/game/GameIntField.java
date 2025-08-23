package sus.keiger.mlgpvp.game;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GameIntField
{
    String Description();
    int MinValue();
    int MaxValue();
}