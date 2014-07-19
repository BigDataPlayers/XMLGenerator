package com.bdc.XMLGenerator.util;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;



public class JsonArrayIterator implements Iterator<Object>
{
    private final JSONArray array;
    private int nextIndex;
    private final int length;

    public JsonArrayIterator(JSONArray array)
    {
        this.array = array;
        nextIndex = 0;
        length = array.length();
    }

    @Override
    public boolean hasNext()
    {
        return nextIndex < length;
    }

    @Override
    public Object next()
    {
        nextIndex++;
        try
        {
            return array.get(nextIndex - 1);
        }
        catch (JSONException e)
        {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException();
    }


}
