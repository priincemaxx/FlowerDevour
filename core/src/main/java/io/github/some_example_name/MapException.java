package io.github.some_example_name;

public class MapException extends RuntimeException
{
    public MapException(String message)
    {
        super("MapException: " + message);
    }
}
