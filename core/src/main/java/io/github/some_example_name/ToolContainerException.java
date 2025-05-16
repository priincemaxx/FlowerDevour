package io.github.some_example_name;

public class ToolContainerException extends RuntimeException
{
    public ToolContainerException(String message)
    {
        super("ToolContainerException: " + message);
    }
}
