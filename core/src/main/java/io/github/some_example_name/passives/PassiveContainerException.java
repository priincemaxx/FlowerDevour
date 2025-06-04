package io.github.some_example_name.passives;

public class PassiveContainerException extends RuntimeException
{
    public PassiveContainerException(String message)
    {
        super("PassiveContainerException: " + message);
    }
}
