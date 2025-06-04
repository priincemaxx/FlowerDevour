package io.github.some_example_name.tools;

public class ToolContainerException extends RuntimeException
{
    public ToolContainerException(String message)
    {
        super("ToolContainerException: " + message);
    }
}
