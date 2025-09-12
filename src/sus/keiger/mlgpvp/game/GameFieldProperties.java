package sus.keiger.mlgpvp.game;

/**
 * Holds string representations of a {@link GameInstanceValues} modifiable field.
 * <br>This is meant to be used not to modify a field but to display its contents to the user.
 * @param Value The current value of the field.
 * @param MinValue The minimum value of that field, may be <code>null</code> if the field doesn't have a minimum value.
 * @param MaxValue The maximum value of that field, may be <code>null</code> if the field doesn't have a maximum value.
 * @param Description The user-friendly description of what the field does.
 */
public record GameFieldProperties(String Value, String MinValue, String MaxValue, String Description) { }