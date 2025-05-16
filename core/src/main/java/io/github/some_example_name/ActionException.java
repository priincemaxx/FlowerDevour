package io.github.some_example_name;

public class ActionException extends RuntimeException
{
    public ActionException(String message)
    {
        super("ActionException: " + message);
    }
}
