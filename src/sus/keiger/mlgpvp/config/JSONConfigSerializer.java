package sus.keiger.mlgpvp.config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sus.keiger.mlgpvp.MLGPvPPlugin;
import sus.keiger.mlgpvp.game.GameFieldType;
import sus.keiger.mlgpvp.game.GameInstanceValues;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* This is kinda bad since it won't preserve the config if a field's name changes. */


/**
 * Provides functionality to serialise game configs (stored in <code>GameInstanceValues</code> objects into JSON and
 * deserialise them from JSON.
 */
public class JSONConfigSerializer
{
    // Private fields.
    private final Map<String, Field> _fieldMap;
    private final String BOOL_FALSE = "false";
    private final String BOOL_TRUE = "true";


    // Constructors.
    public JSONConfigSerializer()
    {
        _fieldMap = new HashMap<>();
        for (Field TargetField : GameInstanceValues.GetModifiableFields())
        {
            _fieldMap.put(TargetField.getName(), TargetField);
        }
    }


    // Methods.
    public String Serialize(GameInstanceValues values)
    {

        JSONObject Root = new JSONObject();
        for (Field TargetField : GameInstanceValues.GetModifiableFields())
        {
            Root.put(TargetField.getName(), values.GetProperties(TargetField).Value());
        }
        return Root.toJSONString();
    }

    public GameInstanceValues Deserialize(String json) throws ConfigException
    {
        GameInstanceValues Values = new GameInstanceValues();

        try
        {
            Object Root = new JSONParser().parse(json);
            if (!(Root instanceof JSONObject CompoundRoot))
            {
                throw new ConfigException("Expected root of config to be a compound.");
            }

            for (Object Key : CompoundRoot.keySet())
            {
                String Value = CompoundRoot.get(Key).toString();
                Field TargetField = _fieldMap.get(Key.toString());
                if (TargetField == null)
                {
                    continue;
                }

                SetValue(Values, TargetField, Value);
            }
        }
        catch (ParseException e)
        {
            throw new ConfigException("Invalid config JSON: %s".formatted(e.getMessage()));
        }
        return Values;
    }


    // Private methods.
    private void SetValue(GameInstanceValues values, Field field, String value)
    {
        GameFieldType FieldType = GameInstanceValues.GetFieldType(field);

        switch (FieldType)
        {
            case BoolField -> SetBoolField(values, field, value);
            case IntField -> SetIntegerField(values, field, value);
            case DoubleField -> SetDoubleField(values, field, value);
            case StringField -> SetStringField(values, field, value);
        }
    }

    private void SetBoolField(GameInstanceValues values, Field field, String value)
    {
        String FormattedValue = value.strip().toLowerCase(MLGPvPPlugin.GetLocale());

        if (FormattedValue.equals(BOOL_FALSE))
        {
            values.SetField(field, false);
        }
        else if (FormattedValue.equals(BOOL_TRUE))
        {
            values.SetField(field, true);
        }
        else
        {
            throw new ConfigException("Invalid config boolean value: \"%s\"".formatted(value));
        }
    }

    private void SetIntegerField(GameInstanceValues values, Field field, String value)
    {
        try
        {
            values.SetField(field, Integer.parseInt(value));
        }
        catch (NumberFormatException e)
        {
            throw new ConfigException("Invalid config integer value: \"%s\"".formatted(value));
        }
    }

    private void SetDoubleField(GameInstanceValues values, Field field, String value)
    {
        try
        {
            double ParsedValue = Double.parseDouble(value);
            if (Double.isInfinite(ParsedValue) || Double.isNaN(ParsedValue))
            {
                throw new ConfigException("Double value in config cannot be infinite or NaN: %s"
                        .formatted(ParsedValue));
            }
            values.SetField(field, ParsedValue);
        }
        catch (NumberFormatException e)
        {
            throw new ConfigException("Invalid config double value: \"%s\"".formatted(value));
        }
    }

    private void SetStringField(GameInstanceValues values, Field field, String value)
    {
        values.SetField(field, value);
    }
}