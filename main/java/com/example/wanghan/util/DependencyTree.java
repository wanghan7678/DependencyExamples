package com.example.wanghan.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;

public class DependencyTree implements Iterable<DependencyTree>
{
    private static final Logger logger = LogManager.getLogger(DependencyTree.class);

    /**
     * parent of the tree, null if the root
     */
    private DependencyTree parent;

    /**
     * name or index of the node, in this case: the path
     */
    private String key;

    /**
     * the contains of the node
     */
    private Object value;

    public DependencyTree()
    {
        createEmptyNode();
    }

    public DependencyTree(DependencyTree parent, String key, Object value)
    {
        this.parent = parent;
        this.key = key;
        this.value = value;
    }


    /**
     * put in to the tree, as format: a.1,b.2
     * @param path
     * @param value
     * @return
     */
    //TODO: support formats like: a.1,b.2,c.3
    public DependencyTree put(String path, Object value)
    {
        int i = path.indexOf(',');
        String parentKey = path.substring(0, i);
        String childKey = path.substring(i + 1);
        DependencyTree node = getNode(parentKey);
        if (node.isMap())
        {
            Map map = (Map) node.value;
            value = map.put(childKey, value);
            return new DependencyTree(node, childKey, value);
        }
        return node;
    }

    protected DependencyTree getNode(String parentKey) {
        final Map<Object, Object> map = (Map<Object, Object>) value;
        final Object object = map.get(parentKey);
        // Node not found, create new node if in case of 'put'
        if (object == null)
        {
            // Create a new node
            final LinkedHashMap<String, Object> child = new LinkedHashMap<>();
            map.put(parentKey, child);
            return new DependencyTree(this, parentKey, child);
        }
        // Node found
        return new DependencyTree(this, parentKey, object);
    }

    protected void createEmptyNode()
    {
        key = "root";
        value = new LinkedHashMap<String, Object>();
    }

    /**
     * get the one level children for a given parent key.
     * @param parentKey
     * @param node
     * @param children
     */
    public void getChildren(String parentKey, DependencyTree node, List<DependencyTree> children) {
        DependencyTree matchedNode = null;
        if (node.getParent() != null && node.getParent().getKey().equals(parentKey))
        {
            matchedNode = node;
        }
        else if (!node.isMap())
        {
            return;
        }
        else
        {
            for(DependencyTree child : node)
            {
                getChildren(parentKey, child, children);
            }
        }
        if (matchedNode != null)
        {
            children.add(matchedNode);
        }
    }




    /**
     * Returns {@code true} if the value of this node is a Map.
     *
     * @return {@code true} if the value is a Map
     */
    public boolean isMap()
    {
        return value != null && value instanceof Map;
    }




    @Override
    public Iterator<DependencyTree> iterator()
    {
        if (value != null && value instanceof Map)
        {
            return mapIterator();
        }
        return Collections.singleton(this).iterator();
    }

    protected Iterator<DependencyTree> mapIterator()
    {
        final DependencyTree self = this;
        return new Iterator<>() {

            @SuppressWarnings("rawtypes")
            private final Iterator children = ((Map) value).entrySet().iterator();

            @Override
            public boolean hasNext() {
                return children.hasNext();
            }

            @Override
            @SuppressWarnings("rawtypes")
            public DependencyTree next() {
                Map.Entry entry = (Map.Entry) children.next();
                return new DependencyTree(self, entry.getKey().toString(), entry.getValue());
            }

            @Override
            public void remove() {
                children.remove();
            }
        };
    }


    public boolean isRoot()
    {
        return parent == null;
    }

    public Object getValue()
    {
        return this.value;
    }

    public String getKey()
    {
        return this.key;
    }

    public DependencyTree getParent()
    {
        return this.parent;
    }

}
