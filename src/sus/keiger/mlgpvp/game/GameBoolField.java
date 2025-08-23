package sus.keiger.mlgpvp.game;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GameBoolField
{
    String Description();
}