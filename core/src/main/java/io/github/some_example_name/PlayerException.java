package io.github.some_example_name;

public class PlayerException extends RuntimeException
{
    public PlayerException(String message)
    {
        super("PlayerException: " + message);
    }
}
