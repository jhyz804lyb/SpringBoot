package com.vuck.common.views;

import com.vuck.utils.StringUtils;

public class Button
{
    private String name;

    private String styleClass;

    private String text;

    private String id;

    public Button(String name, String text)
    {
        this.name = name;
        this.text = text;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getStyleClass()
    {
        return styleClass;
    }

    public void setStyleClass(String styleClass)
    {
        this.styleClass = styleClass;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Button button = (Button) o;
        if (StringUtils.isEmpty(button.getText()) || StringUtils.isEmpty(this.getText()) ||
                StringUtils.isEmpty(button.getName()) || StringUtils.isEmpty(this.getName())) return false;
        return (button.getText().equals(this.text) && button.name.equals(this.name));
    }

    @Override
    public int hashCode()
    {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (styleClass != null ? styleClass.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
